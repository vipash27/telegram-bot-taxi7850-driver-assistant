package com.gmail.vipash27.dao;

import com.gmail.vipash27.model.User;

import java.util.List;

public interface UserDao {

    void testSave(User user);

    void insert(User user);

    List<User> getAllUsers();
}
