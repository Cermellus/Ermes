package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CreditReason entity.
 */
public class CreditReasonDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer creditReasonId;

    @NotNull
    private String creditReasonDescription;

    @NotNull
    private String creditReasonCode;

    @NotNull
    private Boolean creditReasonProductRequired;

    @NotNull
    private Boolean creditReasonCourierClaim;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditReasonId() {
        return creditReasonId;
    }

    public void setCreditReasonId(Integer creditReasonId) {
        this.creditReasonId = creditReasonId;
    }

    public String getCreditReasonDescription() {
        return creditReasonDescription;
    }

    public void setCreditReasonDescription(String creditReasonDescription) {
        this.creditReasonDescription = creditReasonDescription;
    }

    public String getCreditReasonCode() {
        return creditReasonCode;
    }

    public void setCreditReasonCode(String creditReasonCode) {
        this.creditReasonCode = creditReasonCode;
    }

    public Boolean isCreditReasonProductRequired() {
        return creditReasonProductRequired;
    }

    public void setCreditReasonProductRequired(Boolean creditReasonProductRequired) {
        this.creditReasonProductRequired = creditReasonProductRequired;
    }

    public Boolean isCreditReasonCourierClaim() {
        return creditReasonCourierClaim;
    }

    public void setCreditReasonCourierClaim(Boolean creditReasonCourierClaim) {
        this.creditReasonCourierClaim = creditReasonCourierClaim;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditReasonDTO creditReasonDTO = (CreditReasonDTO) o;
        if (creditReasonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditReasonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditReasonDTO{" +
            "id=" + getId() +
            ", creditReasonId=" + getCreditReasonId() +
            ", creditReasonDescription='" + getCreditReasonDescription() + "'" +
            ", creditReasonCode='" + getCreditReasonCode() + "'" +
            ", creditReasonProductRequired='" + isCreditReasonProductRequired() + "'" +
            ", creditReasonCourierClaim='" + isCreditReasonCourierClaim() + "'" +
            "}";
    }
}
