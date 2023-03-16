package com.example.testjavafx.domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private static final long serialVersionUID = 7331115341259248461L;

    private ID entityID;

    protected ID getEntityID(){
        return entityID;
    }

    protected void setEntityID(ID _entityID){
        this.entityID = _entityID;
    }
}
