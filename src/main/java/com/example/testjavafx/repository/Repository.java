package com.example.testjavafx.repository;

import com.example.testjavafx.domain.Entity;
import com.example.testjavafx.exceptions.RepositoryException;

public interface Repository<ID, E extends Entity<ID>> {
    void save(E entityToSave) throws RepositoryException;
    E findOne(ID entityID) throws RepositoryException;
}
