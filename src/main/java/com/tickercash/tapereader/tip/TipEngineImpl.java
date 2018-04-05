package com.tickercash.tapereader.tip;

import java.io.File;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.deploy.DeploymentOptions;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.deploy.Module;
import com.tickercash.tapereader.marketdata.Tick;

public class TipEngineImpl implements TipEngine {
    
    private EPServiceProvider engine;
    
    private EPStatement statement;
    
    public TipEngineImpl() {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.getEPAdministrator().getConfiguration().addEventType(Tick.class);
    }

    public TipEngineImpl(Class<?> eventType, String stmt) {
        engine = EPServiceProviderManager.getDefaultProvider();
        engine.getEPAdministrator().getConfiguration().addEventType(eventType);
        statement = engine.getEPAdministrator().createEPL(stmt);
    }
    
    @Override
	public void addStatement(String stmt) {
		statement = engine.getEPAdministrator().createEPL(stmt);
	}
    
    public void addListener(UpdateListener listener){
        statement.addListener(listener);
    }

    @Override
    public void sendNewTick(Tick tick) {
        engine.getEPRuntime().sendEvent(tick);
    }

	@Override
	public EPServiceProvider getEngine() {
		return engine;
	}

	@Override
	public void deployModule(String eplFile) throws Exception {
		EPDeploymentAdmin deployAdmin = engine.getEPAdministrator().getDeploymentAdmin();
        Module module = deployAdmin.read(new File(eplFile));
        deployAdmin.deploy(module, new DeploymentOptions());
	}

	@Override
	public void addListener(String stmtName, UpdateListener listener) {
		engine.getEPAdministrator().getStatement(stmtName).addListener(listener);
	}

}
