package cst8218.n041092398.lab4;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.security.enterprise.identitystore.PasswordHash;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Entity
@Table(name = "appuser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppUser.findAll", query = "SELECT u FROM AppUser u"),
    @NamedQuery(name = "AppUser.findById", query = "SELECT u FROM AppUser u WHERE u.id = :id"),
    @NamedQuery(name = "AppUser.findByUserid", query = "SELECT u FROM AppUser u WHERE u.userid = :userid"),
    @NamedQuery(name = "AppUser.findByGroupname", query = "SELECT u FROM AppUser u WHERE u.groupname = :groupname")
})
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    
    @NotNull
    @Size(max = 255)
    @Column(name = "userid")
    private String userid;
    
    @Column(name = "password")
    private String password;
    
    @NotNull
    @Size(max = 255)
    @Column(name = "groupname")
    private String groupname;

    public AppUser() {
    }

    public AppUser(Long id) {
        this.id = id;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return ""; // Always return empty string
    }

    public void setPassword(String password) {
        if (password != null && !password.isEmpty()) {
            Instance<Pbkdf2PasswordHash> instance = CDI.current().select(Pbkdf2PasswordHash.class);
            PasswordHash hasher = instance.get();
            Map<String, String> parameters = new HashMap<>();
            hasher.initialize(parameters);
            this.password = hasher.generate(password.toCharArray());
        }
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    // hashCode, equals, and toString methods

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "cst8218.n041092398.lab4.AppUser[ id=" + id + " ]";
    }
}