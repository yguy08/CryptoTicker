package com.tickercash.tapereader.core;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class WebSocket {

	public static void main(String[] args) throws URISyntaxException {
		WebSocketClient client = new WebSocketClient(new URI( "wss://ws-feed.gdax.com")) {
			@Override
			public void onOpen( ServerHandshake handshakedata ) {
                System.out.println("yooo");
			}

			@Override
			public void onMessage(String message) {
				
			}

			@Override
			public void onClose( int code, String reason, boolean remote ) {

			}

			@Override
			public void onError( Exception ex ) {

			}
		};
		
		client.connect();

	}

}
