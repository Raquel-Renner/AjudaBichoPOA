/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ajudabichopoa.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Raquel
 */
@Entity
@Table(name = "publicacao")
public class PublicacaoEntidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String pub_Titulo;
    private String pub_Nome_Animal;
    private String pub_Nome_Prot;
    private String pub_Descricao;
    private String pub_Comentario;
    private String pub_Contato;
    private String pub_CaminhoImagem;
    private String nomeProtetorAndamento;
    private String nomeProtetorFechamento;
    private String id_userLogado;
    private Integer pub_Status;

    @Temporal(TemporalType.DATE)
    private Date pub_Data;

    public static final int statusAb = 1;
    public static final int statusAn = 2;
    public static final int statusFe = 3;
    public static final int statusDisp = 4;
    public static final int statusDoado = 5;

    public PublicacaoEntidade() {
    }

    public String getId_userLogado() {
        return id_userLogado;
    }

    public void setId_userLogado(String id_userLogado) {
        this.id_userLogado = id_userLogado;
    }

    public String getNomeProtetorAndamento() {
        return nomeProtetorAndamento;
    }

    public void setNomeProtetorAndamento(String nomeProtetorAndamento) {
        this.nomeProtetorAndamento = nomeProtetorAndamento;
    }

    public String getNomeProtetorFechamento() {
        return nomeProtetorFechamento;
    }

    public void setNomeProtetorFechamento(String nomeProtetorFechamento) {
        this.nomeProtetorFechamento = nomeProtetorFechamento;
    }

    public Integer getPub_Status() {
        return pub_Status;
    }

    public void setPub_Status(Integer pub_Status) {
        this.pub_Status = pub_Status;
    }

    public String getPub_Titulo() {
        return pub_Titulo;
    }

    public void setPub_Titulo(String pub_Titulo) {
        this.pub_Titulo = pub_Titulo;
    }

    public String getPub_Nome_Animal() {
        return pub_Nome_Animal;
    }

    public void setPub_Nome_Animal(String pub_Nome_Animal) {
        this.pub_Nome_Animal = pub_Nome_Animal;
    }

    public String getPub_Nome_Prot() {
        return pub_Nome_Prot;
    }

    public void setPub_Nome_Prot(String pub_Nome_Prot) {
        this.pub_Nome_Prot = pub_Nome_Prot;
    }

    public String getPub_Descricao() {
        return pub_Descricao;
    }

    public void setPub_Descricao(String pub_Descricao) {
        this.pub_Descricao = pub_Descricao;
    }

    public String getPub_Comentario() {
        return pub_Comentario;
    }

    public void setPub_Comentario(String pub_Comentario) {
        this.pub_Comentario = pub_Comentario;
    }

    public String getPub_Contato() {
        return pub_Contato;
    }

    public void setPub_Contato(String pub_Contato) {
        this.pub_Contato = pub_Contato;
    }

    public String getPub_CaminhoImagem() {
        return pub_CaminhoImagem;
    }

    public void setPub_CaminhoImagem(String pub_CaminhoImagem) {
        this.pub_CaminhoImagem = pub_CaminhoImagem;
    }

    public Date getPub_Data() {
        return pub_Data;
    }

    public void setPub_Data(Date pub_Data) {
        this.pub_Data = pub_Data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String statusOutput() {
        if (pub_Status == 1) {
            return ("Aberto");
        } else if (pub_Status == 2) {
            return ("Andamento");
        } else if (pub_Status == 3) {
            return ("Fechado");
        } else if (pub_Status == 4) {
            return ("Disponível");
        } else {
            return ("Doado");
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PublicacaoEntidade)) {
            return false;
        }
        PublicacaoEntidade other = (PublicacaoEntidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ajudabichopoa.model.NewEntity[ id=" + id + " ]";
    }

}
