package com.example.testjavafx.service;

import com.example.testjavafx.domain.User;
import com.example.testjavafx.exceptions.RepositoryException;
import com.example.testjavafx.exceptions.ServiceException;
import com.example.testjavafx.repository.Repository;
import com.example.testjavafx.repository.RepositoryUsers;

public class Service {

    private Repository<String, User> userRepository;

    public Service() {
        userRepository = new RepositoryUsers("jdbc:postgresql://localhost:5432/academic","parolaMea123","postgres");
    }

    public void addUser(String email,String username, String password) throws ServiceException, RepositoryException {

        if(!userExists(email))
            userRepository.save(new User(email,username,password));
        else
            throw new ServiceException("Email already in use");

    }

    public boolean userExists(String email){
        try{
            userRepository.findOne(email);
            return true;
        }catch (RepositoryException serviceException){
            return false;
        }
    }
    public User findOne(String email) throws ServiceException{
        try{
            return userRepository.findOne(email);
        }catch (RepositoryException repositoryException){
            throw new ServiceException("User not found");
        }
    }
}
