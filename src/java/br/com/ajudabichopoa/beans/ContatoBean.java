/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ajudabichopoa.beans;

import br.com.ajudabichopoa.model.ContatoEntidade;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Raquel
 */
@ManagedBean
@SessionScoped
public class ContatoBean implements Serializable {

    /**
     * Creates a new instance of ContatoBean
     */
    public ContatoBean() {
    }

    private ContatoEntidade contatoEnt = new ContatoEntidade();
    private static Properties props = null;
    private static Session session = null;
    boolean flagAlertCampos = false;
    boolean flagAlertCamposSucess = false;

    public String mandaMail() throws IOException {

//        if relacionado a validação campos
        if (!this.verificaCampoParaCadastro()) {

            System.out.println("entrou no manda mail");

            props = System.getProperties();
            props.put("mail.smtp.host", "ajudabichopoa.com");
            props.put("mail.smtp.port", "25");
            props.put("mail.smtp.auth", "true");
            session = Session.getInstance(props,
                    new Autenticar("contato@ajudabichopoa.com", "Caco4747*")
            );

            try {

                MimeMessage message = new MimeMessage(session);
                message.setRecipient(
                        Message.RecipientType.TO,
                        new InternetAddress("contato@ajudabichopoa.com")
                );

                message.setFrom(new InternetAddress("ajudab@ajudabichopoa.com"));
                message.setSubject("Email solicitação AjudaBichoPOA");//Assunto

                String texto = "Solicitação para inclusão de serviços no mapa:"
                        + "\n"
                        + "Nome Solicitante:"
                        + "\n"
                        + contatoEnt.getCont_Nome()
                        + "\n"
                        + "E-mail Solicitante:"
                        + "\n"
                        + contatoEnt.getCont_Email()
                        + "\n"
                        + "Telefone Solicitante:"
                        + "\n"
                        + contatoEnt.getCont_Telefone()
                        + "\n"
                        + "Mensagem Solicitante:"
                        + "\n"
                        + contatoEnt.getCont_Msg();

                message.setContent(texto, "text/html; charset=utf-8");

                /**
                 * Método para enviar a mensagem criada
                 */
                Transport.send(message);
                System.out.println("Feito!!!");

            } catch (MessagingException e) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("http://www.ajudabichopoa.com/faces/servicos.xhtml#contact");
                throw new RuntimeException(e);
            }
            limpaCamposCont();

            FacesContext.getCurrentInstance().getExternalContext().redirect("http://www.ajudabichopoa.com/faces/servicos.xhtml#contact");
        }
        return "http://www.ajudabichopoa.com/faces/servicos.xhtml#contact";
    }

    public void limpaCamposCont() {
        this.contatoEnt.setCont_Email(null);
        this.contatoEnt.setCont_Msg(null);
        this.contatoEnt.setCont_Nome(null);
        this.contatoEnt.setCont_Telefone(null);
    }

//    Relacionados a Alert campos - inicio 
    public boolean verificaCampoParaCadastro() {
        try {
            if (this.contatoEnt.getCont_Email().isEmpty()
                    || this.contatoEnt.getCont_Msg().isEmpty()
                    || this.contatoEnt.getCont_Nome().isEmpty()
                    || this.contatoEnt.getCont_Telefone().isEmpty()) {
                this.limpaCamposCont();
                this.flagAlertCampos = true;
                this.flagAlertCamposSucess = false;
                return true;
            } else {
                this.flagAlertCampos = false;
                this.flagAlertCamposSucess = true;
                return false;
            }

        } catch (Exception e) {
            printStackTrace();
        }
        return false;
    }

    public boolean verificaCamposAlert() {
        this.limpaCamposCont();
        return this.flagAlertCampos;
    }

    public boolean verificaCamposAlertSucess() {
        this.limpaCamposCont();
        return this.flagAlertCamposSucess;
    }

    public void limpaErroAlert() {
        this.flagAlertCampos = false;
        this.flagAlertCamposSucess = false;
    }
//    Relacionados a Alert campos - fim

//    Get and Set
    public ContatoEntidade getContatoEnt() {
        return contatoEnt;
    }

    public void setContatoEnt(ContatoEntidade contatoEnt) {
        this.contatoEnt = contatoEnt;
    }
}
