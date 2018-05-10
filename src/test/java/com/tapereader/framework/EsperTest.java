package com.tapereader.framework;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.deploy.DeploymentException;
import com.espertech.esper.client.deploy.DeploymentOptions;
import com.espertech.esper.client.deploy.DeploymentResult;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.deploy.ParseException;
import com.tapereader.annotation.Subscriber;

public class EsperTest {
    
    private EPAdministrator epAdministrator;
    private EPServiceProvider engine;
    
    public EsperTest(){
        engine = EPServiceProviderManager.getDefaultProvider();
        epAdministrator = engine.getEPAdministrator();
    }
    
    private void processAnnotations(EPStatement statement) throws Exception {
        Annotation[] annotations = statement.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Subscriber) {
                Subscriber subscriber = (Subscriber) annotation;
                Object obj = getSubscriber(subscriber.className());
                String method = subscriber.methodName();
                statement.setSubscriber(obj, method);
            }
        }
    }

    private Object getSubscriber(String fqdn) throws Exception {
        Class<?> cl = Class.forName(fqdn);
        return cl.newInstance();
    }
    
    /**
     * @param source        a string containing EPL statements
     * @param intoFieldBean if not null, any @IntoMethod annotations on Esper statements will bind the columns from
     *                      the select statement into the fields of the intoFieldBean instance.
     */
    public void loadStatements(String source) {
        EPDeploymentAdmin deploymentAdmin = epAdministrator.getDeploymentAdmin();
        com.espertech.esper.client.deploy.Module module;
        String filename = source + ".epl";
        try {
            module = deploymentAdmin.read(filename);
            DeploymentResult deployResult;
            try {
                deployResult = deploymentAdmin.deploy(module, new DeploymentOptions());
                List<EPStatement> statements = deployResult.getStatements();

                for (EPStatement statement : statements) {

                    try {
                        processAnnotations(statement);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        System.out.println("Threw a Execption, full stack trace follows:"+e);
                    }
                }
                System.out.println("deployed module " + filename);
            } catch (DeploymentException | InterruptedException e) {
                System.out.println("unable to deploy module " + source + e);
            }
        } catch (FileNotFoundException ignored) {
            // it is not neccessary for every module to have an EPL file
        } catch (IOException e) {
            System.out.println("no module file found for " + filename + " on classpath. Please ensure " + source + ".epl is in the resources directory.");
        } catch (ParseException e) {
            System.out.println("Could not parse EPL " + filename + e);
        }
    }
    
    public void sendEvent(Object event) throws Exception {
        engine.getEPRuntime().sendEvent(event);
    }

    public static void main(String[] args) throws Exception {
        EsperTest esper = new EsperTest();
        esper.loadStatements("TapeReader");
        for(int i = 0; i < 10; i++){
            esper.sendEvent(new Tick("BTC/USD", "FAKE", 1000L, 1000.00, i));
        }
    }
    
    public void onTick(Tick tick){
        System.out.println(tick);
    }

}
