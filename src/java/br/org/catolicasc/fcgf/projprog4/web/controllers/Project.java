package br.org.catolicasc.fcgf.projprog4.web.controllers;

/**
 *
 * @author Fabio Tavares Dippold, FCGF
 * @version 15/08/2016
 * 
 */
public final class Project {
    Long id;
    String nome;
    String status;
    String gerente;
    String inicio;
    String termino;
    
    public Project(Long id, String nome, String status, String gerente, String inicio, String termino) {
        setId(id);
        setNome(nome);
        setStatus(status);
        setGerente(gerente);
        setInicio(inicio);
        setTermino(termino);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }
    
    
    
}
