package com.tapereader.engine;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.deploy.Module;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.tapereader.config.ModuleName;
import com.tapereader.config.StatementName;
import com.tapereader.framework.Engine;
import com.tapereader.framework.Event;
import com.tapereader.listener.TickEventListener;

public class EngineImpl implements Engine {
    
    private EPServiceProvider engine;
    
    @Inject
    public EngineImpl(@ModuleName String moduleName, @StatementName String statementName, TickEventListener listener) throws Exception {
        Configuration configuration = new Configuration();
        configuration.configure();
        engine = EPServiceProviderManager.getDefaultProvider();
        EPDeploymentAdmin deployAdmin = engine.getEPAdministrator().getDeploymentAdmin();
        Module module = deployAdmin.read(moduleName);
        deployAdmin.deploy(module, null);
        engine.getEPAdministrator().getStatement(statementName).setSubscriber(listener);
    }

    @Subscribe
    public void onEvent(Event event){
        System.out.println("HELLO, WORLD!");
        engine.getEPRuntime().sendEvent(event);
    }
}
