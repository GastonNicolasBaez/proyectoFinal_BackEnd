package com.portfoliobaez.gnb.Entity;

import lombok.Getter;

@Getter
public enum RolE {
    ADMIN("ADMIN");
    String text;
    
    RolE(String text){
        this.text = text;
    }
  
    
}
