package nz.co.it4biz.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @NotNull
    @Column(name = "role_description", nullable = false)
    private String roleDescription;

    @NotNull
    @Column(name = "role_approve", nullable = false)
    private Boolean roleApprove;

    @NotNull
    @Column(name = "role_insert", nullable = false)
    private Boolean roleInsert;

    @NotNull
    @Column(name = "role_edit", nullable = false)
    private Boolean roleEdit;

    @NotNull
    @Column(name = "role_return", nullable = false)
    private Boolean roleReturn;

    @NotNull
    @Column(name = "role_process", nullable = false)
    private Boolean roleProcess;

    @NotNull
    @Column(name = "role_see_all_rquests", nullable = false)
    private Boolean roleSeeAllRquests;

    @NotNull
    @Column(name = "role_courier_claim", nullable = false)
    private Boolean roleCourierClaim;

    @Column(name = "role_comment")
    private String roleComment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public Role roleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public Role roleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
        return this;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Boolean isRoleApprove() {
        return roleApprove;
    }

    public Role roleApprove(Boolean roleApprove) {
        this.roleApprove = roleApprove;
        return this;
    }

    public void setRoleApprove(Boolean roleApprove) {
        this.roleApprove = roleApprove;
    }

    public Boolean isRoleInsert() {
        return roleInsert;
    }

    public Role roleInsert(Boolean roleInsert) {
        this.roleInsert = roleInsert;
        return this;
    }

    public void setRoleInsert(Boolean roleInsert) {
        this.roleInsert = roleInsert;
    }

    public Boolean isRoleEdit() {
        return roleEdit;
    }

    public Role roleEdit(Boolean roleEdit) {
        this.roleEdit = roleEdit;
        return this;
    }

    public void setRoleEdit(Boolean roleEdit) {
        this.roleEdit = roleEdit;
    }

    public Boolean isRoleReturn() {
        return roleReturn;
    }

    public Role roleReturn(Boolean roleReturn) {
        this.roleReturn = roleReturn;
        return this;
    }

    public void setRoleReturn(Boolean roleReturn) {
        this.roleReturn = roleReturn;
    }

    public Boolean isRoleProcess() {
        return roleProcess;
    }

    public Role roleProcess(Boolean roleProcess) {
        this.roleProcess = roleProcess;
        return this;
    }

    public void setRoleProcess(Boolean roleProcess) {
        this.roleProcess = roleProcess;
    }

    public Boolean isRoleSeeAllRquests() {
        return roleSeeAllRquests;
    }

    public Role roleSeeAllRquests(Boolean roleSeeAllRquests) {
        this.roleSeeAllRquests = roleSeeAllRquests;
        return this;
    }

    public void setRoleSeeAllRquests(Boolean roleSeeAllRquests) {
        this.roleSeeAllRquests = roleSeeAllRquests;
    }

    public Boolean isRoleCourierClaim() {
        return roleCourierClaim;
    }

    public Role roleCourierClaim(Boolean roleCourierClaim) {
        this.roleCourierClaim = roleCourierClaim;
        return this;
    }

    public void setRoleCourierClaim(Boolean roleCourierClaim) {
        this.roleCourierClaim = roleCourierClaim;
    }

    public String getRoleComment() {
        return roleComment;
    }

    public Role roleComment(String roleComment) {
        this.roleComment = roleComment;
        return this;
    }

    public void setRoleComment(String roleComment) {
        this.roleComment = roleComment;
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
        Role role = (Role) o;
        if (role.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Role{" +
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
