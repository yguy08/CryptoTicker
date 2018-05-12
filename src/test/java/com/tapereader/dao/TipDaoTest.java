package com.tapereader.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.tapereader.framework.AppInitializer;
import com.tapereader.model.Tip;
import com.tapereader.module.FakeTestModule;

public class TipDaoTest {
    
    private AppInitializer init;
    private TipDao tipDao;
    
    /*@Before
    public void setUp(){
        Injector injector = Guice.createInjector(new FakeTestModule(), new JpaPersistModule("com.tapereader"));
        init = injector.getInstance(AppInitializer.class);
        tipDao = injector.getInstance(TipDao.class);
    }
    
    @After
    public void tearDown(){
        //init.endAll();
    }
    
    @Test
    public void tipDaoTest() {
        tipDao.save(new Tip("BHSL"));
        tipDao.save(new Tip("BLSH"));
        List<Tip> tips = tipDao.loadAll();
        assertEquals(2, tips.size());
    }*/
    
}
