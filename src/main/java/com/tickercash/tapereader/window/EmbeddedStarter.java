package com.tickercash.tapereader.window;

import com.tickercash.tapereader.TapeReader;
import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.config.Config;
import com.tickercash.tapereader.tip.TipEngineImpl;

public class EmbeddedStarter {

    public static void main(String[] args) throws Exception {
        Config config = Config.loadConfig(args[0]);
        TapeReader reader = new TapeReader();
        reader.setConfig(config);
        reader.setQuoteBoy(QuoteBoy.createQuoteBoy(config.getQuoteBoyType()));
        reader.getQuoteBoy().addHandler(reader::onEvent);

        reader.setTipEngine(new TipEngineImpl());
        
        char c = (char) 0023;
        String stmt = "@Name(SelectAll) select * from Tick"+Character.toString(c)+"#length(5)";
        reader.getTipEngine().addStatement(stmt);
        reader.getTipEngine().addListener(reader::update);
        
        reader.getQuoteBoy().start();
        
    }

}
