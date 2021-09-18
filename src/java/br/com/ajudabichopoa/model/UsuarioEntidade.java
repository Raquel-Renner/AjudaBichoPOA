package br.com.ajudabichopoa.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Raquel
 */
@Entity
@Table(name = "usuario")
public class UsuarioEntidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column
    private String usu_Nome;
    private String usu_Senha;
    private String usu_Email;

    public UsuarioEntidade() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUsu_Nome() {
        return usu_Nome;
    }

    public void setUsu_Nome(String usu_Nome) {
        this.usu_Nome = usu_Nome;
    }

    public String getUsu_Senha() {
        return usu_Senha;
    }

    public void setUsu_Senha(String usu_Senha) {
        this.usu_Senha = usu_Senha;
    }

    public String getUsu_Email() {
        return usu_Email;
    }

    public void setUsu_Email(String usu_Email) {
        this.usu_Email = usu_Email;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final UsuarioEntidade other = (UsuarioEntidade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ajudabichopoa.model.NewEntity[ id=" + id + " ]";
    }
}
