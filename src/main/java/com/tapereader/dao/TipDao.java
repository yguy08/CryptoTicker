package com.tapereader.dao;

import com.tapereader.model.Tip;

public interface TipDao extends DataAccess<Tip> {
    
    Tip findByName(String name);
    
    Tip findServer();
}
