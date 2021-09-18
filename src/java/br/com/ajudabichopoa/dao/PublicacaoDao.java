/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ajudabichopoa.dao;

import br.com.ajudabichopoa.model.AntesDepoisEntidade;
import br.com.ajudabichopoa.model.PublicacaoEntidade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Raquel
 */
@Stateless
public class PublicacaoDao {

    public PublicacaoDao() {
    }

    @PersistenceContext(unitName = "AjudaBichoPOAPU2")
    EntityManager objEntityM;

    public void cadastrarPublicacao(PublicacaoEntidade pub_ent) {

        try {
            if (pub_ent.getId() == null) {
                objEntityM.persist(pub_ent);
            } else {
                objEntityM.merge(pub_ent);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {

        }
    }
    
    public void cadastrarAntesDepois(AntesDepoisEntidade antesD_ent) {

        try {
            if (antesD_ent.getId() == null) {
                objEntityM.persist(antesD_ent);
            } else {
                objEntityM.merge(antesD_ent);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {

        }
    }

    public void cadastrarComment(PublicacaoEntidade pub_ent) {

        try {
            if (pub_ent.getId() == null) {
                objEntityM.persist(pub_ent);
            } else {
                objEntityM.merge(pub_ent);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {

        }
    }

    public void removerPublicacao(PublicacaoEntidade pub_ent) {

        try {
            System.out.println("Entrou no remover pub DAO");
            PublicacaoEntidade pEntidade = objEntityM.merge(pub_ent);
            objEntityM.remove(pEntidade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void removerPublicacaoAD(AntesDepoisEntidade pub_ent_AD) {

        try {
            System.out.println("Entrou no remover pubAD DAO");
            AntesDepoisEntidade AnDeEntidade = objEntityM.merge(pub_ent_AD);
            objEntityM.remove(AnDeEntidade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PublicacaoEntidade> getListaPublicacao() {

        EntityManager entityManager = Persistence.createEntityManagerFactory("AjudaBichoPOAPU2").createEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublicacaoEntidade> q = cb.createQuery(PublicacaoEntidade.class);
        Root<PublicacaoEntidade> c = q.from(PublicacaoEntidade.class);
        q.where(c.get("pub_Status").in("1", "2"));
        q.orderBy(cb.desc(c.get("pub_Data")));
        TypedQuery que = entityManager.createQuery(q);

        return que.getResultList();

//        RETORNA TUDO DA TABELA
//        EntityManager entityManager = Persistence.createEntityManagerFactory("AjudaBichoPOAPU2").createEntityManager();
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<PublicacaoEntidade> q = cb.createQuery(PublicacaoEntidade.class);
//        Root<PublicacaoEntidade> c = q.from(PublicacaoEntidade.class);
//        q.select(c);
//        q.orderBy(cb.desc(c.get("pub_Data")));
//        TypedQuery que = entityManager.createQuery(q);
//        
//        return que.getResultList();
    }

    //select com status 4 - animais disponiveis doação
    public List<PublicacaoEntidade> getListaDoacao() {

        EntityManager entityManager = Persistence.createEntityManagerFactory("AjudaBichoPOAPU2").createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublicacaoEntidade> q = cb.createQuery(PublicacaoEntidade.class);
        Root<PublicacaoEntidade> c = q.from(PublicacaoEntidade.class);
        q.where(c.get("pub_Status").in("4"));

        q.orderBy(cb.desc(c.get("pub_Data")));
        TypedQuery que = entityManager.createQuery(q);

        return que.getResultList();

    }

    public PublicacaoEntidade getPubPorId(Long id) {
        PublicacaoEntidade pub_Ent = objEntityM.find(PublicacaoEntidade.class, id);
        System.out.println("ID no get por ID DAO: " + pub_Ent.getId());

        if (pub_Ent.getId() == null) {
            System.out.println("entrou no NULL DAO");
            objEntityM.persist(pub_Ent);
        } else {
            System.out.println("entrou no else DAO");
            objEntityM.merge(pub_Ent);

        }
        return (pub_Ent);
    }

    public void editarPub(PublicacaoEntidade pub_ent) {

        if (pub_ent.getId() == null) {
            System.out.println("nao funfou");
        } else {
            System.out.println("ID: " + pub_ent.getId());
            System.out.println("teste: " + pub_ent.getPub_Comentario());
            objEntityM.merge(pub_ent);
        }

    }

    //retorna uma lista com as doações feitas pelo sistema. (pg. BichoFeliz)
    public List<PublicacaoEntidade> getListaDoados() {

        EntityManager entityManager = Persistence.createEntityManagerFactory("AjudaBichoPOAPU2").createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublicacaoEntidade> q = cb.createQuery(PublicacaoEntidade.class);
        Root<PublicacaoEntidade> c = q.from(PublicacaoEntidade.class);
        q.where(c.get("pub_Status").in("5"));

        q.orderBy(cb.desc(c.get("pub_Data")));
        TypedQuery que = entityManager.createQuery(q);

        return que.getResultList();
    }

    //retorna uma lista com as urgências realizadas pelo sistema. (pg. BichoFeliz)
    public List<PublicacaoEntidade> getListaUrgFechadas() {

        EntityManager entityManager = Persistence.createEntityManagerFactory("AjudaBichoPOAPU2").createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PublicacaoEntidade> q = cb.createQuery(PublicacaoEntidade.class);
        Root<PublicacaoEntidade> c = q.from(PublicacaoEntidade.class);
        q.where(c.get("pub_Status").in("3"));

        q.orderBy(cb.desc(c.get("pub_Data")));
        TypedQuery que = entityManager.createQuery(q);

        return que.getResultList();
    }
    
    public List<AntesDepoisEntidade> getListaAntesDepois() {

        EntityManager entityManager = Persistence.createEntityManagerFactory("AjudaBichoPOAPU2").createEntityManager();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AntesDepoisEntidade> q = cb.createQuery(AntesDepoisEntidade.class);
        Root<AntesDepoisEntidade> c = q.from(AntesDepoisEntidade.class);
        q.select(c);
        TypedQuery que = entityManager.createQuery(q);

        return que.getResultList();
    }
}
