package com.tapereader.framework;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.deploy.DeploymentException;
import com.espertech.esper.client.deploy.DeploymentOptions;
import com.espertech.esper.client.deploy.DeploymentResult;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.deploy.Module;
import com.espertech.esper.client.deploy.ParseException;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.tapereader.annotation.ModuleName;
import com.tapereader.annotation.Subscriber;

public class DefaultEngine implements Engine {
    
    private EPServiceProvider engine;
    
    @Inject
    public DefaultEngine(@ModuleName String moduleName) throws Exception {
        Configuration configuration = new Configuration();
        configuration.configure();
        engine = EPServiceProviderManager.getDefaultProvider();
    }
    
    @Override
    public void loadStatements(Object obj, String source) {
        EPDeploymentAdmin deploymentAdmin = engine.getEPAdministrator().getDeploymentAdmin();
        Module module;
        String filename = null;
        if(!source.contains(".epl")){
            filename = source + ".epl";
        }
        try {
            module = deploymentAdmin.read(filename);
            DeploymentResult deployResult;
            try {
                deployResult = deploymentAdmin.deploy(module, new DeploymentOptions());
                List<EPStatement> statements = deployResult.getStatements();
                for (EPStatement statement : statements) {
                    try {
                        processAnnotations(obj, statement);
                    } catch (Exception e) {
                        System.out.println("Threw a Execption, full stack trace follows:"+e);
                    }
                }
                System.out.println("deployed module " + filename);
            } catch (DeploymentException e) {
                System.out.println("unable to deploy module " + source + e);
            }
        } catch (FileNotFoundException ignored) {
            
        } catch (IOException e) {
            System.out.println("no module file found for " + filename + " on classpath. Please ensure " + source + ".epl is in the resources directory.");
        } catch (ParseException e) {
            System.out.println("Could not parse EPL " + filename + e);
        }
    }
    
    private void processAnnotations(Object obj, EPStatement statement) throws Exception {
        Annotation[] annotations = statement.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Subscriber) {
                Subscriber subscriber = (Subscriber) annotation;
                String method = subscriber.methodName();
                statement.setSubscriber(obj, method);
            }
        }
    }
    
    @Override @Subscribe
    public void sendEvent(Object event) throws Exception {
        engine.getEPRuntime().sendEvent(event);
    }

    @Override
    public void addSubscriber(String stmtName, Object object, String methodName) throws Exception {
        engine.getEPAdministrator().getStatement(stmtName).setSubscriber(object, methodName);
    }
}
