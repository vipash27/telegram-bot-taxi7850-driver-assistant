package com.gmail.vipash27.dao.impl;

import com.gmail.vipash27.dao.UserDao;
import com.gmail.vipash27.model.User;
import com.gmail.vipash27.service.ConnectionService;
import com.gmail.vipash27.service.QueryService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public void testSave(User user) {
        try (Connection connection = ConnectionService.getInstance().getConnection()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(QueryService.getInstance().testQuery());
                }
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(User user) {
        String query = QueryService.getInstance().insert();
        try (Connection connection = ConnectionService.getInstance().getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, user.getUserId());
                    statement.setString(2, user.getUserName());
                    statement.setString(3, user.getFirstName());
                    statement.setString(4, user.getLastName());
                    statement.execute();
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String query = QueryService.getInstance().selectAllFromUser();
        List<User> list = new ArrayList<>();
        try (Connection connection = ConnectionService.getInstance().getConnection()) {
            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        User user = new User(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4));
                        list.add(user);
                    }
                    resultSet.close();
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
