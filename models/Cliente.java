package models;

import exceptions.EmailInvalidoException;

public class Cliente {
    private int id;
    private String nome;
    private String email;
    
    public Cliente(int id, String nome, String email) throws EmailInvalidoException {
        validarEmail(email);

        this.id = id;
        this.nome = nome;
        this.email = email;
    }
    
    private void validarEmail(String email) throws EmailInvalidoException {
        if (email == null || email.trim().isEmpty()) {
            throw new EmailInvalidoException("E-mail não pode ser vazio.");
        }
    }
    
    public int obterId() {
        return id;
    }
    
    public String obterNome() {
        return nome;
    }
    
    public String obterEmail() {
        return email;
    }
}

