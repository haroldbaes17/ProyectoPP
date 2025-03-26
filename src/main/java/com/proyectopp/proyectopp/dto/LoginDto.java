package com.proyectopp.proyectopp.dto;

public class LoginDto {

    private String correoElectronico;
    private String password;
    private String confirmarPassword;


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

    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "correoElectronico='" + correoElectronico + '\'' +
                ", password='" + password + '\'' +
                ", confirmarPassword='" + confirmarPassword + '\'' +
                '}';
    }
}
