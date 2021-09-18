/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ajudabichopoa.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author raquel.pinheiro
 */
@Entity
@Table(name = "antesdepois")
public class AntesDepoisEntidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String antesDep_NomeAnimal;
    private String antesDep_Resumo;
    private String antesDep_FotoAntes;
    private String antesDep_FotoDepois;
    private String id_userLogado;

    public AntesDepoisEntidade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAntesDep_NomeAnimal() {
        return antesDep_NomeAnimal;
    }

    public void setAntesDep_NomeAnimal(String antesDep_NomeAnimal) {
        this.antesDep_NomeAnimal = antesDep_NomeAnimal;
    }

    public String getAntesDep_Resumo() {
        return antesDep_Resumo;
    }

    public void setAntesDep_Resumo(String antesDep_Resumo) {
        this.antesDep_Resumo = antesDep_Resumo;
    }

    public String getAntesDep_FotoAntes() {
        return antesDep_FotoAntes;
    }

    public void setAntesDep_FotoAntes(String antesDep_FotoAntes) {
        this.antesDep_FotoAntes = antesDep_FotoAntes;
    }

    public String getAntesDep_FotoDepois() {
        return antesDep_FotoDepois;
    }

    public void setAntesDep_FotoDepois(String antesDep_FotoDepois) {
        this.antesDep_FotoDepois = antesDep_FotoDepois;
    }

    public String getId_userLogado() {
        return id_userLogado;
    }

    public void setId_userLogado(String id_userLogado) {
        this.id_userLogado = id_userLogado;
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
        if (!(object instanceof AntesDepoisEntidade)) {
            return false;
        }
        AntesDepoisEntidade other = (AntesDepoisEntidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ajudabichopoa.model.AntesEDepoisEntidade[ id=" + id + " ]";
    }

}
