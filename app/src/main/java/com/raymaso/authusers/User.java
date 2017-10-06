package com.raymaso.authusers;

/**
 * Created by Raymi on 06/10/2017.
 */

public class User {
    String auth_id;
    String id;
    String name;
    String username;
    String email;
    String password;
    String date_reg;

    public User(){

    }

    public User( String id, String auth_id, String name, String username, String email, String password, String date_reg) {
        this.auth_id = auth_id;
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.date_reg = date_reg;
    }

    public String getId() {
        return id;
    }

    public void setId(String auth_id) {
        this.id = auth_id;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate_reg() {
        return date_reg;
    }

    public void setDate_reg(String date_reg) {
        this.date_reg = date_reg;
    }
}
