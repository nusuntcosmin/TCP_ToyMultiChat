package com.example.testjavafx.repository;

import com.example.testjavafx.domain.User;
import com.example.testjavafx.exceptions.RepositoryException;

import java.util.Calendar;
import java.util.UUID;
import java.sql.*;

public class RepositoryUsers implements Repository<String, User> {

    private  String URL = "jdbc:postgresql://localhost:5432/academic";
    private  String password = "parolaMea123";
    private  String user = "postgres";


    public RepositoryUsers(String URL, String password, String user) {
        this.URL = URL;
        this.password = password;
        this.user = user;
    }

    private final String ADD_QUERRY = "INSERT INTO \"User\" VALUES( ? , ? , ? ) ";
    private final String FIND_ONE_QUERRY = "SELECT * FROM \"User\" WHERE email = ? ";

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL,user,password);
    }

    @Override
    public void save(User entityToSave) throws RepositoryException{
        try(Connection con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(ADD_QUERRY)){
            preparedStatement.setString(1,entityToSave.getUsername());
            preparedStatement.setString(2,entityToSave.getUserPassword());
            preparedStatement.setString(3,entityToSave.getUserEmail());
            preparedStatement.executeUpdate();

        }catch (SQLException sqlException){
            throw new RepositoryException("Error occured when trying to save the user");
        }
    }

    @Override
    public User findOne(String entityID) throws RepositoryException {
        try(Connection con = getConnection();
            PreparedStatement  preparedStatement = con.prepareStatement(FIND_ONE_QUERRY)) {
            preparedStatement.setString(1, entityID);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            final String username = resultSet.getString("username");
            final String password = resultSet.getString("password");
            final String email = resultSet.getString("email");

            return new User(email,username,password);

        }catch (SQLException sqlException){
            throw new RepositoryException("Error occured when trying to select an user");
        }
    }
}
