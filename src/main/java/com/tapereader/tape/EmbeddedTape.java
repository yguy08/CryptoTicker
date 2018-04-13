package com.tapereader.tape;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.tapereader.framework.Event;
import com.tapereader.framework.Tape;

@Singleton
public class EmbeddedTape implements Tape {
    
    private EventBus eventBus;
    
    @Inject
    public EmbeddedTape() {
        eventBus = new EventBus();
    }

    @Override
    public void write(Event event) throws Exception {
        eventBus.post(event);
    }

    @Override
    public void read(Object object) throws Exception {
        eventBus.register(object);
    }
}
