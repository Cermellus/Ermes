package nz.co.it4biz.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ErmesUser.
 */
@Entity
@Table(name = "ermes_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ErmesUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ermes_user_id", nullable = false)
    private Integer ermesUserId;

    @NotNull
    @Column(name = "ermes_user_username", nullable = false)
    private String ermesUserUsername;

    @NotNull
    @Column(name = "ermes_user_password", nullable = false)
    private String ermesUserPassword;

    @NotNull
    @Column(name = "ermes_user_name", nullable = false)
    private String ermesUserName;

    @NotNull
    @Column(name = "ermes_user_mail", nullable = false)
    private String ermesUserMail;

    @ManyToOne
    @JsonIgnoreProperties("ermesUsers")
    private Role roleId;

    @ManyToOne
    @JsonIgnoreProperties("ermesUsers")
    private Company companyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getErmesUserId() {
        return ermesUserId;
    }

    public ErmesUser ermesUserId(Integer ermesUserId) {
        this.ermesUserId = ermesUserId;
        return this;
    }

    public void setErmesUserId(Integer ermesUserId) {
        this.ermesUserId = ermesUserId;
    }

    public String getErmesUserUsername() {
        return ermesUserUsername;
    }

    public ErmesUser ermesUserUsername(String ermesUserUsername) {
        this.ermesUserUsername = ermesUserUsername;
        return this;
    }

    public void setErmesUserUsername(String ermesUserUsername) {
        this.ermesUserUsername = ermesUserUsername;
    }

    public String getErmesUserPassword() {
        return ermesUserPassword;
    }

    public ErmesUser ermesUserPassword(String ermesUserPassword) {
        this.ermesUserPassword = ermesUserPassword;
        return this;
    }

    public void setErmesUserPassword(String ermesUserPassword) {
        this.ermesUserPassword = ermesUserPassword;
    }

    public String getErmesUserName() {
        return ermesUserName;
    }

    public ErmesUser ermesUserName(String ermesUserName) {
        this.ermesUserName = ermesUserName;
        return this;
    }

    public void setErmesUserName(String ermesUserName) {
        this.ermesUserName = ermesUserName;
    }

    public String getErmesUserMail() {
        return ermesUserMail;
    }

    public ErmesUser ermesUserMail(String ermesUserMail) {
        this.ermesUserMail = ermesUserMail;
        return this;
    }

    public void setErmesUserMail(String ermesUserMail) {
        this.ermesUserMail = ermesUserMail;
    }

    public Role getRoleId() {
        return roleId;
    }

    public ErmesUser roleId(Role role) {
        this.roleId = role;
        return this;
    }

    public void setRoleId(Role role) {
        this.roleId = role;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public ErmesUser companyId(Company company) {
        this.companyId = company;
        return this;
    }

    public void setCompanyId(Company company) {
        this.companyId = company;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErmesUser ermesUser = (ErmesUser) o;
        if (ermesUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ermesUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ErmesUser{" +
            "id=" + getId() +
            ", ermesUserId=" + getErmesUserId() +
            ", ermesUserUsername='" + getErmesUserUsername() + "'" +
            ", ermesUserPassword='" + getErmesUserPassword() + "'" +
            ", ermesUserName='" + getErmesUserName() + "'" +
            ", ermesUserMail='" + getErmesUserMail() + "'" +
            "}";
    }
}
