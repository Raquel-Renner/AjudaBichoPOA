package br.com.ajudabichopoa.beans;

import br.com.ajudabichopoa.dao.UsuarioDao;
import br.com.ajudabichopoa.model.AntesDepoisEntidade;
import br.com.ajudabichopoa.model.PublicacaoEntidade;
import br.com.ajudabichopoa.model.UsuarioEntidade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Raquel
 */
@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

    public String idGoogle = "";
    public String userNameGoogle = "";
    public String idFace = "";
    public String userNameFace = "";
    public String nomeLoginGoogleFace = "";
    public boolean logadoRedeSocial = false;
    private UsuarioEntidade usuario = new UsuarioEntidade(); //p poder trabalhar inserir os set's e get's.

    @EJB
    private UsuarioDao usuarioDao;

    boolean flagAlertCadastro = false;
    boolean flagAlertLogin = false;
    boolean flagAlertCadastroExist = false;
    public FacesContext fContext;
    public HttpSession session;

    private List<UsuarioEntidade> listaUsers = new ArrayList<>(); //metodo de lista usuarios

    public UsuarioBean() {
    }

    //Disabled o botão "Login" (caso o login seja via rede socal)
//    public boolean desativaBtnLogin(){
//        System.out.println("entrou no desativa");
//        
//        if(!this.idGoogle.isEmpty() || !this.idFace.isEmpty()){
//            
//            System.out.println("ret..true");
//            return true;
//        }
//        else
//            return false;
//    }
    //metodos q vao se comunicar c/ a interface:
    public String cadastroDeUsuario() {

        if (verificaCamposCadastro()) { //verifica se os campos foram preenchidos
            if (!verifCadastroExistente()) { //verifica se já existe um usuário com esse nome no banco
                usuarioDao.cadastrarUsuario(this.usuario);//aqui passa o parametro para a DAO (o user q ele vai usar lá)

                limpaCampos();
                System.out.println("User Cadastrado");
                return "index";
            } else {
                return "index";
            }
        } else {
            return "index";
        }
    }

    public void limpaCampos() {

        this.usuario.setUsu_Nome(null);
        this.usuario.setUsu_Email(null);
        this.usuario.setUsu_Senha(null);
    }

    public boolean verificaCamposCadastro() { //verifica se os campos foram preenchidos

        if (this.usuario.getUsu_Nome().isEmpty() || this.usuario.getUsu_Senha().isEmpty()
                || this.usuario.getUsu_Email().isEmpty()) {
            System.out.println("Favor preencher todos os campos.");
            this.flagAlertCadastro = true;
            return false;
        } else {
            System.out.println("Campos preenchidos");
            this.flagAlertCadastro = false;
            return true;
        }
    }

    public String autorizaLogin(String nome, String senha) { //verifica se os campos são válidos

        nome = this.usuario.getUsu_Nome();
        senha = this.usuario.getUsu_Senha();
        this.fContext = FacesContext.getCurrentInstance();
        this.session = (HttpSession) fContext.getExternalContext().getSession(false);

        UsuarioDao obj_dao = new UsuarioDao();
        List<UsuarioEntidade> listadeEntidadeUsuario = new ArrayList<>();
        listadeEntidadeUsuario = obj_dao.getListaUsuarios();

        for (UsuarioEntidade retornoLista : listadeEntidadeUsuario) {

            if (nome.equalsIgnoreCase(retornoLista.getUsu_Nome()) && senha.equals(retornoLista.getUsu_Senha())) {

                this.session.setAttribute("usuario", retornoLista);
                this.session.setAttribute("id", retornoLista.getId());
                this.session.setAttribute("nome", retornoLista.getUsu_Nome());
                this.flagAlertLogin = false;
                return "index";
            }
        }
        this.flagAlertLogin = true;
        return "index";
    }

    public boolean verifCadastroExistente() {
        String nome = this.usuario.getUsu_Nome();
        UsuarioDao obj_dao = new UsuarioDao();
        List<UsuarioEntidade> listadeEntidadeUsuario = new ArrayList<>();
        listadeEntidadeUsuario = obj_dao.getListaUsuarios();

        for (UsuarioEntidade retornoLista : listadeEntidadeUsuario) {

            if (nome.equals(retornoLista.getUsu_Nome())) {
                this.flagAlertCadastroExist = true;
                return true;
            }
        }
        this.flagAlertCadastroExist = false;
        return false;
    }

    public boolean verificaDadosLogin() {
        limpaCampos();
        return this.flagAlertLogin;
    }

    public boolean verificaDadosCadastro() {
        limpaCampos();
        return this.flagAlertCadastro;
    }

    public boolean verificaDadosCadastroExist() {
        limpaCampos();
        return this.flagAlertCadastroExist;
    }

    public void abreSessaoRedeSocial() {
        this.fContext = FacesContext.getCurrentInstance();
        this.session = (HttpSession) fContext.getExternalContext().getSession(false);

        System.out.println("this.idFace no abre sessão: " + this.idFace);
        System.out.println("this.google no abre sessão: " + this.idGoogle);

        if (!this.idGoogle.isEmpty()) {
            System.out.println("entrou no if entao?????");
            this.idFace = "";
            this.session.setAttribute("id", this.idGoogle);
            this.session.setAttribute("nome", this.userNameGoogle);
        } else if (!this.idFace.isEmpty()) {
            System.out.println("entrou no else if?????");
            this.idGoogle = "";
            this.session.setAttribute("id", this.idFace);
            this.session.setAttribute("nome", this.userNameFace);
        }
        System.out.println("o id do user na seção é: " + session.getAttribute("id"));
        System.out.println("o nome do user na seção é: " + session.getAttribute("nome"));
    }

    public String getUserAtivo() {
        String id_Strg = "";

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        if (session.getAttribute("id") != null && session.getAttribute("id") != "") {
            id_Strg = session.getAttribute("id").toString();
        }
        System.out.println("valor do id_strg " + id_Strg);
        return id_Strg;
    }

    public boolean verificaSessaoAberta() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (((UsuarioEntidade) session.getAttribute("usuario") == null
                && ("".equals(this.idGoogle) || this.idGoogle == null)
                && ("".equals(this.idFace) || this.idFace == null))) {
            System.out.println("entrou no ver. sess. abert false");
            return false;
        } else {
            System.out.println("entrou no ver. sess. abert else-true");
            return true;
        }
    }

    public boolean verificaSessaoAbertaBotaoSair() {

        System.out.println("valores: " + this.idFace + this.idGoogle);
        if (verificaSessaoAberta() && ("".equals(this.idFace) && "".equals(this.idGoogle)
                || (this.idGoogle == null && this.idFace == null))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificaSessaoAbertaFB() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if ("".equals(this.idFace) || this.idFace == null) {
            System.out.println("entrou no ver. sess. abert false");
            return false;
        } else {
            System.out.println("entrou no ver. sess. abert else-true");
            return true;
        }
    }

    public boolean verificaSessaoAbertaGoogle() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if ("".equals(this.idGoogle) || this.idGoogle == null) {
            System.out.println("entrou no ver. sess. abert false");
            return false;
        } else {
            System.out.println("entrou no ver. sess. abert else-true");
            return true;
        }
    }

    public boolean verificaUserLogado() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        System.out.println("valor dentro logado rede " + session.getAttribute("id"));
        if ("".equals(session.getAttribute("id"))) {
            return false;
        } else {
            return true;
        }
    }

    public String retornaIdParaCadastro() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String numId = session.getAttribute("id").toString();
        return numId;
    }

    public String retornaNomeParaCadastro() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String nomeUser = session.getAttribute("nome").toString();
        System.out.println("nome noretorna nomepcadastro " + session.getAttribute("nome"));
        return nomeUser;
    }

    public boolean verificaPubDoUsuario(PublicacaoEntidade pub_obj) {
        if ((getUserAtivo() != null && !getUserAtivo().isEmpty())
                && (pub_obj.getId_userLogado().equals(getUserAtivo()))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificaPubADdoUsuario(AntesDepoisEntidade pub_AD_Ent) {
        if ((getUserAtivo() != null && !getUserAtivo().isEmpty())
                && (pub_AD_Ent.getId_userLogado().equals(getUserAtivo()))) {
            return true;
        } else {
            return false;
        }
    }

    public String fechaSessao() {

        System.out.println("entrou fechasessao");
        this.idGoogle = null;
        this.userNameGoogle = null;
        this.idFace = null;
        this.userNameFace = null;

        this.session.setAttribute("id", null);
        this.session.setAttribute("nome", null);

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("id", null);
        session.setAttribute("nome", null);

        if (this.session.getAttribute("usuario") != null) {
            System.out.println("id no fecha: " + this.session.getAttribute("id"));
            System.out.println("id no fecha, atrib. userrr: " + this.session.getAttribute("usuario"));
            this.session.setAttribute("usuario", null);
            this.session.setAttribute("id", null);
            return "index";
        }
        if (this.session.getAttribute("id") != null) {
            System.out.println("id no fecha: " + this.session.getAttribute("id"));
            this.session.setAttribute("id", null);
            return "index";
        }
        System.out.println("id no fecha: " + this.session.getAttribute("id"));
        System.out.println("name no fecha: " + this.session.getAttribute("name"));
        return "index";
    }

    public void limpaErroLogin() {
        this.flagAlertLogin = false;
    }

    public void limpaErroCad() {
        this.flagAlertCadastro = false;
    }

    public void limpaErroCadExist() {
        this.flagAlertCadastroExist = false;
    }

    public String RemocaoDeUsuario() {
        usuarioDao.RemoverUsuario(usuario);//aqui passa o parametro para a DAO (o user q ele vai usar lá)
        return "Sucesso Delete";
    }

//    metodo para listar dados do banco
    public List<UsuarioEntidade> getListaUsers() {
        this.listaUsers = usuarioDao.getListaUsuarios();
        return listaUsers;
    }

    public String retornoIDGoogleFace(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        this.idGoogle = request.getParameter("idGoogle");
        this.userNameGoogle = request.getParameter("userNameGoogle");
        this.idFace = request.getParameter("idFace");
        this.userNameFace = request.getParameter("userNameFace");

        System.out.println("id face, retorno..: " + this.idFace);
        System.out.println("id google, retorno..: " + this.idGoogle);

        if (this.idGoogle == null && this.idFace == null) {
            return "index";
        }
        abreSessaoRedeSocial();
        return "index";
    }

    //gET'S E sET'S
    public UsuarioEntidade getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntidade usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.usuario);
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
        final UsuarioBean other = (UsuarioBean) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }
}
