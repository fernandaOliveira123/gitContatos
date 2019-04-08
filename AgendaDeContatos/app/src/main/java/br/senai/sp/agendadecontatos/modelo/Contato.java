package br.senai.sp.agendadecontatos.modelo;

import java.io.Serializable;

public class Contato implements Serializable{
    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private String linkedin;
    private byte[] foto_contato;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public byte[] getFoto_contato() {
        return foto_contato;
    }

    public void setFoto_contato(byte[] foto_contato) {
        this.foto_contato = foto_contato;
    }

    @Override
    public String toString() {

        return this.getId() + " - " + this.getNome() + ": " + this.getTelefone();
    }
}
