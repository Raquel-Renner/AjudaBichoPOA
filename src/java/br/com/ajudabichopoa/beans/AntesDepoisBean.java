/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ajudabichopoa.beans;

import br.com.ajudabichopoa.dao.PublicacaoDao;
import br.com.ajudabichopoa.model.AntesDepoisEntidade;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Raquel
 */
@ManagedBean
@SessionScoped
public class AntesDepoisBean implements Serializable {

    private AntesDepoisEntidade antesD_Ent = new AntesDepoisEntidade();
    private PublicacaoBean pubBean = new PublicacaoBean();
    private UsuarioBean userBean = new UsuarioBean();
    private final String pastaimgAntes = "/home/ajudab/appservers/glassfish4/glassfish/domains/domain1/applications/AjudaBichoPOA/resources/uploads/bf-antes/";
    private final String pastaimgDepois = "/home/ajudab/appservers/glassfish4/glassfish/domains/domain1/applications/AjudaBichoPOA/resources/uploads/bf-depois/";
    public String filenameImagemAntes = null;
    public String filenameImagemDepois = null;
    public boolean isImgAntes = false;
    boolean flagAlertCadAntesDepois = false;
    private String filename = null;
    private String destination = null;

    @EJB
    private PublicacaoDao pub_Dao;
    private List<AntesDepoisEntidade> listaAntesDepois = new ArrayList<>(); //metodo de lista Antes e Depois

    public AntesDepoisBean() {
    }

    public List<AntesDepoisEntidade> getListaAntesDepois() {
        this.listaAntesDepois = pub_Dao.getListaAntesDepois();
        return listaAntesDepois;
    }

    public void uploadImagemAntes(FileUploadEvent event) {
        this.isImgAntes = true;
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadImagemDepois(FileUploadEvent event) {
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFile(String fileName, InputStream in) {

        try {
            this.filename = fileName;
            this.filename = pubBean.renomeiaImagem(this.filename);

            if (this.isImgAntes) {
                this.destination = this.pastaimgAntes;
                this.filenameImagemAntes = this.filename;
            } else {
                this.destination = this.pastaimgDepois;
                this.filenameImagemDepois = this.filename;
            }

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + this.filename));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            this.isImgAntes = false;
            System.out.println("New file created!");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String cadastroAntesDepois() {

        if (!verificaCampoParaCadastro()) {
            this.antesD_Ent.setAntesDep_FotoAntes(this.filenameImagemAntes);
            this.antesD_Ent.setAntesDep_FotoDepois(this.filenameImagemDepois);
            if (userBean.verificaUserLogado() == true) {
                this.antesD_Ent.setId_userLogado(userBean.retornaIdParaCadastro());
            }
            pub_Dao.cadastrarAntesDepois(this.antesD_Ent);

            this.limpaCamposAntesDepois();
            return "bichoFeliz";
        } else {
            return "bichoFeliz";
        }
    }

    public void limpaCamposAntesDepois() {
        this.antesD_Ent.setAntesDep_FotoAntes(null);
        this.antesD_Ent.setAntesDep_FotoDepois(null);
        this.antesD_Ent.setAntesDep_NomeAnimal(null);
        this.antesD_Ent.setAntesDep_Resumo(null);
        this.antesD_Ent.setId(null);
    }

    public boolean verificaCampoParaCadastro() {
        try {
            if (this.filenameImagemAntes == null
                    || this.filenameImagemDepois == null
                    || this.antesD_Ent.getAntesDep_NomeAnimal() == null
                    || this.antesD_Ent.getAntesDep_Resumo() == null) {
                this.limpaCamposAntesDepois();
                this.flagAlertCadAntesDepois = true;
                return true;
            } else {
                this.flagAlertCadAntesDepois = false;
                return false;
            }

        } catch (Exception e) {

            printStackTrace();
        }
        return false;
    }

    public boolean verificaCamposCadastroAlert() {
        limpaCamposAntesDepois();
        return this.flagAlertCadAntesDepois;
    }

    public void limpaErroAlert() {
        this.flagAlertCadAntesDepois = false;
    }

    public String remocaoDePublicacaoAD(AntesDepoisEntidade antesDepoisEnt) {
        pub_Dao.removerPublicacaoAD(antesDepoisEnt);
        return "/bichoFeliz.xhtml?faces-redirect=true";
    }

    //    getters and Setters
    public AntesDepoisEntidade getAntesD_Ent() {
        return antesD_Ent;
    }

    public void setAntesD_Ent(AntesDepoisEntidade antesD_Ent) {
        this.antesD_Ent = antesD_Ent;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.antesD_Ent);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AntesDepoisBean other = (AntesDepoisBean) obj;
        if (!Objects.equals(this.antesD_Ent, other.antesD_Ent)) {
            return false;
        }
        return true;
    }
}
