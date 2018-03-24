package com.tickercash.tapereader.tape;

import com.tickercash.tapereader.gui.Displayable;

public enum TapeType implements Displayable {
    
    H2("H2 DB"),
    CSV("CSV");
    
    private String displayName;
    
    TapeType(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}
