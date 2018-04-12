package com.tapereader.module;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class PropertiesModule extends AbstractModule {

    @Override
    protected void configure() {
        try{
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/tapereader.properties"));
            Names.bindProperties(binder(), properties);
        }catch(IOException ex){
            
        }
    }

}
