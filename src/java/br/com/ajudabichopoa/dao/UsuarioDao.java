package br.com.ajudabichopoa.dao;

import br.com.ajudabichopoa.model.UsuarioEntidade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Raquel
 */
@Stateless
public class UsuarioDao {

    //private Session sessaoHibernate;
    //private Transaction transHibernate;
    @PersistenceContext(unitName = "AjudaBichoPOAPU2")
    EntityManager objEntityM;

    public UsuarioDao() {
    }

    public void cadastrarUsuario(UsuarioEntidade usu) {

        try {
         System.out.println("entrou no dao, cadastro de user");
            //sessaoHibernate = HibernateUtil.getSessionFactory().openSession(); //pega uma nova sessão e abre;            
            //transHibernate = sessaoHibernate.beginTransaction();               //inicia uma transação dentro da sessão;
            UsuarioEntidade usuario = new UsuarioEntidade();
            usuario.setUsu_Nome(usu.getUsu_Nome());
            usuario.setUsu_Email(usu.getUsu_Email());
            usuario.setUsu_Senha(usu.getUsu_Senha());            

            if (usuario.getId() == null) {
                objEntityM.persist(usuario);
            } else {
                objEntityM.merge(usuario);
            }

            //sessaoHibernate.save(usuario); //salva os valores setados p o usuario;
            //transHibernate.commit();       //commita as alterações, grava no banco;        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //sessaoHibernate.close();    //independente do resultado fecha a sessão;
        }
    }

    public void RemoverUsuario(UsuarioEntidade usu) {

        try {

            UsuarioEntidade uEntidade = objEntityM.merge(usu);
            objEntityM.remove(uEntidade);

            //sessaoHibernate = HibernateUtil.getSessionFactory().openSession(); //pega uma nova sessão e abre;
            //transHibernate = sessaoHibernate.beginTransaction();               //inicia uma transação dentro da sessão;
            //sessaoHibernate.delete(usu);   //deleta o usuario q ja estava gravado mesmo (usu);
            //transHibernate.commit();       //commita as alterações, grava/deleta no banco;        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //sessaoHibernate.close();    //independente do resultado fecha a sessão;
        }

    }
    //pode ser implementado ainda um Update.....

    //Entra aqui depois do Bean Autoriza, pega e retorna a lista de usuarios do banco:
    public List<UsuarioEntidade> getListaUsuarios() {

        EntityManager entityManager = Persistence.createEntityManagerFactory("AjudaBichoPOAPU2").createEntityManager();
        Query query = entityManager.createQuery("select u from UsuarioEntidade u", UsuarioEntidade.class);
        List<UsuarioEntidade> resultList = query.getResultList();

        for (UsuarioEntidade Ue : resultList) {
            System.out.println("Retorno lista DAO: " + Ue.getUsu_Nome());
        }

        return resultList;
    }
}
