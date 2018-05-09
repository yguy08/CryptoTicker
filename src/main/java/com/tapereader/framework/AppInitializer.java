package com.tapereader.framework;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

public class AppInitializer {
    
    @Inject AppInitializer(PersistService service){
        service.start();
    }
    
    public void startAll() {
        //service.start();
    }
    
    public void endAll() {
        //service.stop();
    }
    
    
    
}
