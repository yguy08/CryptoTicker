package com.tapereader.dao;

import java.util.Collection;
import java.util.List;

public interface DataAccess<E> {
    
    E get(long id);
    
    E load(long id);
    
    List<E> loadAll();
    
    void delete(E value);

    void deleteAll(Collection<E> values);
    
    boolean deleteById(long id);
    
    void flush();
    
    E persist(E value);
    
    void save(E entity);
    
    void saveAll(Collection<E> values);
    
}
