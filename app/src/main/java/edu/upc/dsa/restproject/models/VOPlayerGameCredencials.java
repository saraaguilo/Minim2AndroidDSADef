package edu.upc.dsa.restproject.models;


public class VOPlayerGameCredencials {
    String username;

    public VOPlayerGameCredencials() {
        //this.id = RandomUtils.getId();
    }

    public VOPlayerGameCredencials(String username) {
        this.setUsername(username);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
