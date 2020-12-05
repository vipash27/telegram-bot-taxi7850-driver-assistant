package com.gmail.vipash27.service;

public class QueryService {

    private static QueryService instance;

    private QueryService() {
    }

    public static QueryService getInstance() {
        if (instance == null) {
            instance = new QueryService();
        }
        return instance;
    }

    public String testQuery() {
        String query = "INSERT INTO USER(user_id, username, firstname, lastname, date) VALUES('1234567', 'testUser', 'fn', 'ln', NOW())";
        return query;
    }

    public String insert() {
        String query = "INSERT INTO USER(user_id, username, firstname, lastname, date) VALUES(?, ?, ?, ?, NOW())";
        return query;
    }

    public String selectAllFromUser() {
        String query = "SELECT * FROM user";
        return query;
    }
}
