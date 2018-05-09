package com.tapereader.framework;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

public class AppInitializer {
    
    @Inject(optional=true) 
    private AppInitializer(PersistService service){
        service.start();
    }
    
    
    
}
