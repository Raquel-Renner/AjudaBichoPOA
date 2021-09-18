package br.com.ajudabichopoa.beans;

import javax.mail.*;
import javax.mail.PasswordAuthentication;


public class Autenticar extends Authenticator {
        
    
	private String usuario;
	private String senha;
	
	public Autenticar(){}
       
	
	public Autenticar(String usuario, String senha){
		this.usuario = usuario;
		this.senha = senha;
	}
	
	public PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(usuario,senha);	
	}
}
