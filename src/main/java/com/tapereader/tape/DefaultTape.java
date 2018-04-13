package com.tapereader.tape;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.deploy.Module;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.tapereader.config.ModuleName;
import com.tapereader.config.StatementName;
import com.tapereader.framework.Tape;
import com.tapereader.listener.TickEventListener;
import com.tapereader.model.Tick;

@Singleton
public class DefaultTape implements Tape {
    
    private EventBus eventBus;
    
    private EPServiceProvider engine;
    
    @Inject
    public DefaultTape(@ModuleName String moduleName, @StatementName String statementName, TickEventListener listener) throws Exception {
        eventBus = new EventBus();
        Configuration configuration = new Configuration();
        configuration.configure();
        engine = EPServiceProviderManager.getDefaultProvider();
        EPDeploymentAdmin deployAdmin = engine.getEPAdministrator().getDeploymentAdmin();
        Module module = deployAdmin.read(moduleName);
        deployAdmin.deploy(module, null);
        engine.getEPAdministrator().getStatement(statementName).setSubscriber(listener);
    }

    @Override
    public void write(Tick event) throws Exception {
        eventBus.post(event);
    }

    @Override
    public void read(Object object) throws Exception {
        eventBus.register(object);
    }

    @Override
    public void onEvent(Tick event, long sequence, boolean endOfBatch) throws Exception {
        engine.getEPRuntime().sendEvent(event);
    }
}
