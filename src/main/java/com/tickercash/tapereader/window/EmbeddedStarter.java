package com.tickercash.tapereader.window;

import java.io.File;
import com.espertech.esper.client.deploy.DeploymentOptions;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.deploy.Module;
import com.tickercash.tapereader.TapeReader;
import com.tickercash.tapereader.config.Config;
import com.tickercash.tapereader.tip.TipEngineImpl;

public class EmbeddedStarter {

    public static void main(String[] args) throws Exception {
        Config config = Config.loadConfig(args[0]);
        TapeReader reader = new TapeReader();
        reader.setConfig(config);
        
        reader.setTipEngine(new TipEngineImpl());
        EPDeploymentAdmin deployAdmin = reader.getTipEngine().getEngine().getEPAdministrator().getDeploymentAdmin();
        Module module = deployAdmin.read(new File("src/main/resources/DoubleYou.epl"));
        deployAdmin.deploy(module, new DeploymentOptions());
        reader.getTipEngine().getEngine().getEPAdministrator().getStatement(config.getTip()).addListener(reader::update);
    }

}
