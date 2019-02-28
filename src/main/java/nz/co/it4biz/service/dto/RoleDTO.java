package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Role entity.
 */
public class RoleDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer roleId;

    @NotNull
    private String roleDescription;

    @NotNull
    private Boolean roleApprove;

    @NotNull
    private Boolean roleInsert;

    @NotNull
    private Boolean roleEdit;

    @NotNull
    private Boolean roleReturn;

    @NotNull
    private Boolean roleProcess;

    @NotNull
    private Boolean roleSeeAllRquests;

    @NotNull
    private Boolean roleCourierClaim;

    private String roleComment;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Boolean isRoleApprove() {
        return roleApprove;
    }

    public void setRoleApprove(Boolean roleApprove) {
        this.roleApprove = roleApprove;
    }

    public Boolean isRoleInsert() {
        return roleInsert;
    }

    public void setRoleInsert(Boolean roleInsert) {
        this.roleInsert = roleInsert;
    }

    public Boolean isRoleEdit() {
        return roleEdit;
    }

    public void setRoleEdit(Boolean roleEdit) {
        this.roleEdit = roleEdit;
    }

    public Boolean isRoleReturn() {
        return roleReturn;
    }

    public void setRoleReturn(Boolean roleReturn) {
        this.roleReturn = roleReturn;
    }

    public Boolean isRoleProcess() {
        return roleProcess;
    }

    public void setRoleProcess(Boolean roleProcess) {
        this.roleProcess = roleProcess;
    }

    public Boolean isRoleSeeAllRquests() {
        return roleSeeAllRquests;
    }

    public void setRoleSeeAllRquests(Boolean roleSeeAllRquests) {
        this.roleSeeAllRquests = roleSeeAllRquests;
    }

    public Boolean isRoleCourierClaim() {
        return roleCourierClaim;
    }

    public void setRoleCourierClaim(Boolean roleCourierClaim) {
        this.roleCourierClaim = roleCourierClaim;
    }

    public String getRoleComment() {
        return roleComment;
    }

    public void setRoleComment(String roleComment) {
        this.roleComment = roleComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoleDTO roleDTO = (RoleDTO) o;
        if (roleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
            "id=" + getId() +
            ", roleId=" + getRoleId() +
            ", roleDescription='" + getRoleDescription() + "'" +
            ", roleApprove='" + isRoleApprove() + "'" +
            ", roleInsert='" + isRoleInsert() + "'" +
            ", roleEdit='" + isRoleEdit() + "'" +
            ", roleReturn='" + isRoleReturn() + "'" +
            ", roleProcess='" + isRoleProcess() + "'" +
            ", roleSeeAllRquests='" + isRoleSeeAllRquests() + "'" +
            ", roleCourierClaim='" + isRoleCourierClaim() + "'" +
            ", roleComment='" + getRoleComment() + "'" +
            "}";
    }
}
