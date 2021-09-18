/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ajudabichopoa.beans;

import br.com.ajudabichopoa.dao.PublicacaoDao;
import br.com.ajudabichopoa.model.PublicacaoEntidade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.*;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Raquel
 */
@ManagedBean
@SessionScoped
public class PublicacaoBean implements Serializable {

    private PublicacaoEntidade pub_Ent = new PublicacaoEntidade(); //p poder trabalhar inserir os set's e get's.
    //LOCAL
    //private final String destination = "C:\\Users\\Raquel\\Documents\\NetBeansProjects\\AjudaBichoPOA\\web\\resources\\uploads\\urgencia-doacao\\";
    private final String destination = "/home/ajudab/appservers/glassfish4/glassfish/domains/domain1/applications/AjudaBichoPOA/resources/uploads/urgencia-doacao/";
    public String filename = null;
    boolean flagAlert = false;
    private UsuarioBean userBean = new UsuarioBean();

    @EJB
    private PublicacaoDao pub_Dao;
    private List<PublicacaoEntidade> listaPublicacao = new ArrayList<>(); //metodo de lista pub
    private List<PublicacaoEntidade> listaDoacao = new ArrayList<>(); //metodo de lista Doação
    private List<PublicacaoEntidade> listaDoados = new ArrayList<>();//metodo de lista animais doados (pg. bicho Feliz)
    private List<PublicacaoEntidade> listaUrgFechadas = new ArrayList<>();//metodo de lista urgencias fechadas (pg. bicho Feliz)

    public PublicacaoBean() {

    }

    public void upload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Ok: ", event.getFile().getFileName() + ", upload efetuado com sucesso!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String renomeiaImagem(String fileName) {
        String filenameImagem = fileName;
        String now = LocalDateTime.now().toString();
        String extensao = "";
        String primParteNome = "";

        int posicaoPonto = filenameImagem.lastIndexOf('.');
        if (posicaoPonto > -1) {
            extensao = filenameImagem.substring(posicaoPonto + 1);
        }

        if (!extensao.isEmpty() && filenameImagem.contains(extensao)) {
            primParteNome = filenameImagem.replace(extensao, "");
        }

        filenameImagem = primParteNome.concat(now).concat(".").concat(extensao);
        filenameImagem = filenameImagem.replace(":", "-");
        return filenameImagem;
    }

    public void copyFile(String fileName, InputStream in) {

        try {

            this.filename = fileName;
            System.out.println("filename antes q renomeia:" + this.filename);
            this.filename = renomeiaImagem(this.filename);
            System.out.println("filename depois q renomeia:" + this.filename);

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
            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String cadastroDePublicacaoUrgencia() {
        if (verificaCampoVazioUrgencias()) {
//        Insere uma foto default caso o usuário nao faça Upload
            if (this.filename == null) {
                this.filename = "LogoDefault.jpg";
            }

            this.pub_Ent.setPub_Data(getPegaDataAtual());
            this.pub_Ent.setPub_CaminhoImagem(this.filename);
            if (userBean.verificaUserLogado() == true) {
                this.pub_Ent.setId_userLogado(userBean.retornaIdParaCadastro());
            }
            pub_Dao.cadastrarPublicacao(this.pub_Ent);      //aqui passa o parametro para a DAO (o user q ele vai usar lá)
            this.limpaCampos();
            this.filename = null;

            System.out.println("Urgência cadastrada");
            return "urgencia";
        } else {
            return "urgencia";
        }
    }

    public String cadastroDePublicacaoDoacao() {
        if (verificaCampoVazioDoacao()) {

            this.pub_Ent.setPub_Status(4);
            this.pub_Ent.setPub_Data(getPegaDataAtual());
            this.pub_Ent.setPub_CaminhoImagem(this.filename);
            if (userBean.verificaUserLogado() == true) {
                this.pub_Ent.setId_userLogado(userBean.retornaIdParaCadastro());
            }
            pub_Dao.cadastrarPublicacao(this.pub_Ent);      //aqui passa o parametro para a DAO (o user q ele vai usar lá)

            this.filename = null;
            this.limpaCampos();
            return "doacao";
        } else {
            return "doacao";
        }
    }

    public boolean verificaCampoVazioDoacao() {
        if (this.pub_Ent.getPub_Titulo().isEmpty()
                || this.pub_Ent.getPub_Descricao().isEmpty()
                || this.pub_Ent.getPub_Nome_Prot().isEmpty()
                || this.pub_Ent.getPub_Nome_Animal().isEmpty()
                || this.pub_Ent.getPub_Contato().isEmpty()
                || this.filename == null) {
            System.out.println("Favor preencher todos os campos.");
            this.flagAlert = true;
            return false;
        } else {
            System.out.println("campos preenchidos.");
            this.flagAlert = false;
            return true;
        }
    }

    public boolean verificaCampoVazioUrgencias() {
        if (this.pub_Ent.getPub_Titulo().isEmpty()
                || this.pub_Ent.getPub_Descricao().isEmpty()
                || this.pub_Ent.getPub_Nome_Prot().isEmpty()
                || this.pub_Ent.getPub_Nome_Animal().isEmpty()
                || this.pub_Ent.getPub_Contato().isEmpty()) {
            System.out.println("Favor preencher todos os campos urg.");
            this.flagAlert = true;
            return false;
        } else {
            System.out.println("campos preenchidos urg.");
            this.flagAlert = false;
            return true;
        }
    }

    public boolean verificaCamposCadastroAlert() {
        limpaCampos();
        return this.flagAlert;
    }

    public void limpaErroAlert() {
        this.flagAlert = false;
    }

    public String cadastroDeCommentUrgencia() {

        this.pub_Ent.setPub_Data(getPegaDataAtual());
        System.out.println("valor do status and: " + this.pub_Ent.getPub_Status());
        if (this.pub_Ent.getPub_Status() == 2) {
            this.pub_Ent.setNomeProtetorAndamento(userBean.retornaNomeParaCadastro());
        } else if (this.pub_Ent.getPub_Status() == 3) {
            this.pub_Ent.setNomeProtetorFechamento(userBean.retornaNomeParaCadastro());
        }
        pub_Dao.cadastrarComment(this.pub_Ent);      //aqui passa o parametro para a DAO (o user q ele vai usar lá)

        this.limpaCampos();
        System.out.println("Comentário cadastrado");
        return "/urgencia.xhtml?faces-redirect=true";
    }

    public String cadastroDeCommentDoacao() {

        this.pub_Ent.setPub_Data(getPegaDataAtual());
        pub_Dao.cadastrarComment(this.pub_Ent);      //aqui passa o parametro para a DAO (o user q ele vai usar lá)

        this.limpaCampos();
        System.out.println("Comentário cadastrado");
        return "/doacao.xhtml?faces-redirect=true";
    }

    public String remocaoDePublicacaoDoacao(PublicacaoEntidade pEnt) {
        pub_Dao.removerPublicacao(pEnt);
        return "/doacao.xhtml?faces-redirect=true";
    }

    public String remocaoDePublicacaoUrgencia(PublicacaoEntidade pEnt) {
        pub_Dao.removerPublicacao(pEnt);
        return "/urgencia.xhtml?faces-redirect=true";
    }

    public boolean renderProtParticipantes(PublicacaoEntidade pEnt) {
        if ((pEnt.getNomeProtetorAndamento() != null && pEnt.getNomeProtetorFechamento() != null)
                && pEnt.getNomeProtetorAndamento().equals(pEnt.getNomeProtetorFechamento())) {
            return false;
        } else {
            return true;
        }
    }

    public String chamaModalUrgencia(PublicacaoEntidade pEnt) {
        this.pub_Ent = pEnt;
        return "urgenciaModalComentario";
    }

    public String chamaModalDoacao(PublicacaoEntidade pEnt) {
        this.pub_Ent = pEnt;
        return "doacaoModalComentario";
    }

    public String statusDoado() {
        this.pub_Ent.setPub_Status(5);
        System.out.println("status 5: " + this.pub_Ent.getPub_Status());
        cadastroDeCommentDoacao();
        return "doacao";
    }

    public void statusDisponivel() {
        this.pub_Ent.setPub_Status(4);
        System.out.println("status 4 setado: " + this.pub_Ent.getPub_Status());
    }

    public String sairBotaoModalComentario() {
        limpaCampos();
        return "urgencia";
    }

    public String sairBotaoModalComentarioDoacao() {
        limpaCampos();
        return "/doacao.xhtml?faces-redirect=true";
    }

    public String statusOutputBean(PublicacaoEntidade pubEnt) {
        return pubEnt.statusOutput();
    }

    public String retornaCor(PublicacaoEntidade pubEnt) {
        System.out.println("Entrou aki no retona cor");

        if (pubEnt.getPub_Status() == 1) {
            return "#DD4B39"; //Vermelho
        } else if (pubEnt.getPub_Status() == 2) {
            return "#EEC900";//amarelo
        } else if (pubEnt.getPub_Status() == 3) {
            return "#43CD80"; //Verde
        } else if (pubEnt.getPub_Status() == 4) {
            return "#1E90FF"; //Azul
        } else {
            return "#43CD80"; //Verde
        }
    }

    public Boolean carregarTabela(PublicacaoEntidade pubEnt) {
        if (pubEnt.getPub_Status() == 4) {
            return true;
        } else {
            return false;
        }
    }

    public String getDateFormat(PublicacaoEntidade pubEnt) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = pubEnt.getPub_Data();
        return dateFormat.format(date);
    }

    public Date getPegaDataAtual() {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        return calendar.getTime();
    }

    public void limpaCampos() {

        this.pub_Ent.setPub_CaminhoImagem(null);
        this.pub_Ent.setPub_Contato(null);
        this.pub_Ent.setPub_Nome_Prot(null);
        this.pub_Ent.setPub_Descricao(null);
        this.pub_Ent.setPub_Comentario(null);
        this.pub_Ent.setPub_Titulo(null);
        this.pub_Ent.setPub_Nome_Animal(null);
        this.pub_Ent.setPub_Status(null);
        this.pub_Ent.setPub_Data(null);
        this.pub_Ent.setId(null);
    }

    //Get's and Set's
    public PublicacaoEntidade getPub_Ent() {
        return pub_Ent;
    }

    public void setPub_Ent(PublicacaoEntidade pub_Ent) {
        this.pub_Ent = pub_Ent;
    }

    //    metodo para listar dados do banco
    public List<PublicacaoEntidade> getListaPublicacao() {
        this.listaPublicacao = pub_Dao.getListaPublicacao();
        return listaPublicacao;
    }

    public List<PublicacaoEntidade> getListaDoacao() {
        this.listaDoacao = pub_Dao.getListaDoacao();
        return listaDoacao;
    }

    public List<PublicacaoEntidade> getListaDoados() {
        this.listaDoados = pub_Dao.getListaDoados();
        return listaDoados;
    }

    public List<PublicacaoEntidade> getListaUrgFechadas() {
        this.listaUrgFechadas = pub_Dao.getListaUrgFechadas();
        return listaUrgFechadas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.pub_Ent);
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
        final PublicacaoBean other = (PublicacaoBean) obj;
        if (!Objects.equals(this.pub_Ent, other.pub_Ent)) {
            return false;
        }
        return true;
    }
}
