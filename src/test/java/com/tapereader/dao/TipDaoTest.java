package com.tapereader.dao;

import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.tapereader.framework.AppInitializer;
import com.tapereader.model.Tip;
import com.tapereader.module.FakeTestModule;

public class TipDaoTest {
    
    private AppInitializer init;
    private TipDao tipDao;
    
    @Before
    public void setUp(){
        Injector injector = Guice.createInjector(new FakeTestModule(), new JpaPersistModule("com.tapereader"));
        init = injector.getInstance(AppInitializer.class);
        tipDao = injector.getInstance(TipDao.class);
        tipDao.save(new Tip("BHSL"));
        tipDao.save(new Tip("BLSH"));
    }
    
    @After
    public void tearDown(){
        
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new FakeTestModule(), new JpaPersistModule("com.tapereader"));
        AppInitializer init = injector.getInstance(AppInitializer.class);
        TipDao tipDao = injector.getInstance(TipDao.class);
        tipDao.save(new Tip("BHSL"));
        tipDao.save(new Tip("BLSH"));
        List<Tip> tips = tipDao.loadAll();
        for(Tip tip : tips){
            System.out.println(tip.getName());
        }
    }

}
