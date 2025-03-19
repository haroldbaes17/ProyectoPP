package com.proyectopp.proyectopp.dto;

public class LoginDto {

    private String correoElectronico;
    private String password;


    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "correoElectronico='" + correoElectronico + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
