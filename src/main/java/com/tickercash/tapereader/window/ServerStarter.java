package com.tickercash.tapereader.window;

import com.tickercash.tapereader.TapeReader;
import com.tickercash.tapereader.clerk.CsvQuoteBoy;
import com.tickercash.tapereader.clerk.QuoteBoy;
import com.tickercash.tapereader.config.Config;
import com.tickercash.tapereader.event.handler.TickLogger;
import com.tickercash.tapereader.wire.Transmitter;

public class ServerStarter {
 
    public static void main(String[] args) throws Exception {
        Config config = Config.loadConfig(args[0]);
        TapeReader reader = new TapeReader();
        reader.setConfig(config);
        reader.setQuoteBoy(QuoteBoy.createQuoteBoy(config.getQuoteBoyType()));
        reader.getQuoteBoy().addHandler(new Transmitter(config.getQuoteBoyType().toString()));
        reader.getQuoteBoy().addHandler(new TickLogger());
        ((CsvQuoteBoy) reader.getQuoteBoy()).init(config.getReadCsvPath());
        reader.getQuoteBoy().start();
    }
}