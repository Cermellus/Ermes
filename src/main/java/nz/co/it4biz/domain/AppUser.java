package nz.co.it4biz.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AppUser.
 */
@Entity
@Table(name = "app_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "app_user_id", nullable = false)
    private Integer appUserId;

    @NotNull
    @Column(name = "app_user_username", nullable = false)
    private String appUserUsername;

    @NotNull
    @Column(name = "app_user_password", nullable = false)
    private String appUserPassword;

    @NotNull
    @Column(name = "app_user_name", nullable = false)
    private String appUserName;

    @NotNull
    @Column(name = "app_user_mail", nullable = false)
    private String appUserMail;

    @ManyToOne
    @JsonIgnoreProperties("appUsers")
    private Role roleId;

    @ManyToOne
    @JsonIgnoreProperties("appUsers")
    private Company companyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public AppUser appUserId(Integer appUserId) {
        this.appUserId = appUserId;
        return this;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserUsername() {
        return appUserUsername;
    }

    public AppUser appUserUsername(String appUserUsername) {
        this.appUserUsername = appUserUsername;
        return this;
    }

    public void setAppUserUsername(String appUserUsername) {
        this.appUserUsername = appUserUsername;
    }

    public String getAppUserPassword() {
        return appUserPassword;
    }

    public AppUser appUserPassword(String appUserPassword) {
        this.appUserPassword = appUserPassword;
        return this;
    }

    public void setAppUserPassword(String appUserPassword) {
        this.appUserPassword = appUserPassword;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public AppUser appUserName(String appUserName) {
        this.appUserName = appUserName;
        return this;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public String getAppUserMail() {
        return appUserMail;
    }

    public AppUser appUserMail(String appUserMail) {
        this.appUserMail = appUserMail;
        return this;
    }

    public void setAppUserMail(String appUserMail) {
        this.appUserMail = appUserMail;
    }

    public Role getRoleId() {
        return roleId;
    }

    public AppUser roleId(Role role) {
        this.roleId = role;
        return this;
    }

    public void setRoleId(Role role) {
        this.roleId = role;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public AppUser companyId(Company company) {
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
        AppUser appUser = (AppUser) o;
        if (appUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", appUserId=" + getAppUserId() +
            ", appUserUsername='" + getAppUserUsername() + "'" +
            ", appUserPassword='" + getAppUserPassword() + "'" +
            ", appUserName='" + getAppUserName() + "'" +
            ", appUserMail='" + getAppUserMail() + "'" +
            "}";
    }
}
