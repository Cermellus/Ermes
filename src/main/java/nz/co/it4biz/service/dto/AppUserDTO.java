package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AppUser entity.
 */
public class AppUserDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer appUserId;

    @NotNull
    private String appUserUsername;

    @NotNull
    private String appUserPassword;

    @NotNull
    private String appUserName;

    @NotNull
    private String appUserMail;


    private Long roleIdId;

    private Long companyIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserUsername() {
        return appUserUsername;
    }

    public void setAppUserUsername(String appUserUsername) {
        this.appUserUsername = appUserUsername;
    }

    public String getAppUserPassword() {
        return appUserPassword;
    }

    public void setAppUserPassword(String appUserPassword) {
        this.appUserPassword = appUserPassword;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public String getAppUserMail() {
        return appUserMail;
    }

    public void setAppUserMail(String appUserMail) {
        this.appUserMail = appUserMail;
    }

    public Long getRoleIdId() {
        return roleIdId;
    }

    public void setRoleIdId(Long roleId) {
        this.roleIdId = roleId;
    }

    public Long getCompanyIdId() {
        return companyIdId;
    }

    public void setCompanyIdId(Long companyId) {
        this.companyIdId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppUserDTO appUserDTO = (AppUserDTO) o;
        if (appUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppUserDTO{" +
            "id=" + getId() +
            ", appUserId=" + getAppUserId() +
            ", appUserUsername='" + getAppUserUsername() + "'" +
            ", appUserPassword='" + getAppUserPassword() + "'" +
            ", appUserName='" + getAppUserName() + "'" +
            ", appUserMail='" + getAppUserMail() + "'" +
            ", roleId=" + getRoleIdId() +
            ", companyId=" + getCompanyIdId() +
            "}";
    }
}
