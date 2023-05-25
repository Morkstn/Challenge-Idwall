package com.idwall.entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoas")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sobrenome;

    private int idade;

    private String nacionalidade;
    
    @Column(name = "passaporte")
    private String passaporte;

    private List<String> crimes;

    // Construtores, getters e setters


	public Pessoa() {
    }

    public Pessoa(String nome, String sobrenome, int idade, String nacionalidade, String passaporte, List<String> crimes) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
        this.nacionalidade = nacionalidade;
        this.passaporte = passaporte;
        this.crimes = crimes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(String passaporte) {
        this.passaporte = passaporte;
    }

    public List<String> getCrimes() {
        return crimes;
    }

    public void setCrimes(List<String> crimes) {
        this.crimes = crimes;
    }
}

