/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ajudabichopoa.model;

import java.io.Serializable;

/**
 *
 * @author Raquel
 */
public class ContatoEntidade implements Serializable {

    private String cont_Nome;
    private String cont_Msg;
    private String cont_Email;
    private String cont_Telefone;

    public ContatoEntidade() {
    }

    public String getCont_Nome() {
        return cont_Nome;
    }

    public void setCont_Nome(String cont_Nome) {
        this.cont_Nome = cont_Nome;
    }

    public String getCont_Msg() {
        return cont_Msg;
    }

    public void setCont_Msg(String cont_Msg) {
        this.cont_Msg = cont_Msg;
    }

    public String getCont_Email() {
        return cont_Email;
    }

    public void setCont_Email(String cont_Email) {
        this.cont_Email = cont_Email;
    }

    public String getCont_Telefone() {
        return cont_Telefone;
    }

    public void setCont_Telefone(String cont_Telefone) {
        this.cont_Telefone = cont_Telefone;
    }
}
