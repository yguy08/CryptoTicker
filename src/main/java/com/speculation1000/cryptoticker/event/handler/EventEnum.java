package com.speculation1000.cryptoticker.event.handler;

public enum EventEnum {
    LOG("log"),
    SAVE("save");
	
	String eventName;
	
	EventEnum(String e){
		eventName = e;
	}
	
	public static EventHandler getHandler(EventEnum e) {
		switch(e) {
		case LOG:
			return new Log();
		case SAVE:
			return new Save2File();
		default:
			return new Log();
		}
	}
}
