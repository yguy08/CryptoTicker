package com.tapereader.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.tapereader.model.Tip;

public class TipDaoImpl implements TipDao {
    
    @Inject EntityManager em;
    
    @Override
    @Transactional
    public Tip get(long id) {
        return em.find(Tip.class, id);
    }

    @Override
    @Transactional
    public List<Tip> loadAll() {
        List result = em.createQuery( "from Tip" ).getResultList();
        return result;
    }

    @Override
    public void delete(Tip value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll(Collection<Tip> values) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean deleteById(long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

    @Override
    public Tip persist(Tip value) {
        em.persist(value);
        return value;
    }

    @Override
    public void save(Tip entity) {
        em.persist(entity);
    }

    @Override
    public void saveAll(Collection<Tip> values) {
        // TODO Auto-generated method stub

    }

    @Override
    public Tip findByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tip findServer() {
        // TODO Auto-generated method stub
        return null;
    }

}
