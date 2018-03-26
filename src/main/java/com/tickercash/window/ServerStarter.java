package com.tickercash.window;

import com.tickercash.tapereader.textui.ServerTUI;

public class ServerStarter {
 
    public static void main(String[] args) throws Exception {
        ServerTUI server = new ServerTUI();
        server.init();
    }
}