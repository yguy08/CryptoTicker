package com.speculation1000.cryptoticker.event.handler;

public enum EventEnum {
    LOG("log"),
    SAVE("save"),
    COUNT("count");
	
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
		case COUNT:
			return new Counter();
		default:
			return new Log();
		}
	}
}
