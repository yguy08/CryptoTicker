package com.tapereader.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "TIPS" )
public class Tip {
    
    private long id;
    
    private String name;
    
    public Tip() {
        
    }
    
    public Tip(String name) {
        this.name = name;
    }
    
    @Id
    @GeneratedValue( generator="increment" )
    @GenericGenerator( name="increment", strategy = "increment" )
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
