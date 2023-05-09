package edu.upc.dsa.restproject.models;
import java.util.ArrayList;
import java.util.List;

public class Game {
    String username;
    Integer puntos;
    Integer nivelActual;

    public Game() {
    }

    public Game(String username, Integer puntos, Integer nivelActual) {
        this.setUsername(username);
        this.setPuntos(25);
        this.setNivelActual(1);
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getPuntos() {
        return puntos;
    }
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
    public Integer getNivelActual() {
        return nivelActual;
    }
    public void setNivelActual(Integer nivelActual) {
        this.nivelActual = nivelActual;
    }
}