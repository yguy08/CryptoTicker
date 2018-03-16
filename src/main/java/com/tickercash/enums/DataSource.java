package com.tickercash.enums;

public enum DataSource implements Displayable {
    
    H2("H2 DB"),
    CSV("CSV");
    
    private String displayName;
    
    DataSource(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}
