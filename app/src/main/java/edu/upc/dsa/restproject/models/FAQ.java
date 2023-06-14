package com.example.juegodsarest3.models;

public class FAQ {

    String pregunta;

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public FAQ(){

    }

    public FAQ(String pregunta,String respuesta){
        setPregunta(pregunta);
        setRespuesta(respuesta);
    }
    String respuesta;

}
