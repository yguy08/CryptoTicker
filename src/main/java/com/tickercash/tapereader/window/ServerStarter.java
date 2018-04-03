package com.tickercash.tapereader.window;

import com.tickercash.tapereader.QuoteServer;
import com.tickercash.tapereader.config.Config;

public class ServerStarter {
 
    public static void main(String[] args) throws Exception {
        Config config = Config.loadConfig(args[0]);
        QuoteServer server = new QuoteServer();
        server.setConfig(config);
        server.init();
        server.start();
    }
}