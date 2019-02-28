package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CreditRequestStatus entity.
 */
public class CreditRequestStatusDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer creditRequestStatusId;

    @NotNull
    private String creditRequestStatusDescription;

    @NotNull
    private Boolean creditRequestStatusExport;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditRequestStatusId() {
        return creditRequestStatusId;
    }

    public void setCreditRequestStatusId(Integer creditRequestStatusId) {
        this.creditRequestStatusId = creditRequestStatusId;
    }

    public String getCreditRequestStatusDescription() {
        return creditRequestStatusDescription;
    }

    public void setCreditRequestStatusDescription(String creditRequestStatusDescription) {
        this.creditRequestStatusDescription = creditRequestStatusDescription;
    }

    public Boolean isCreditRequestStatusExport() {
        return creditRequestStatusExport;
    }

    public void setCreditRequestStatusExport(Boolean creditRequestStatusExport) {
        this.creditRequestStatusExport = creditRequestStatusExport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditRequestStatusDTO creditRequestStatusDTO = (CreditRequestStatusDTO) o;
        if (creditRequestStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditRequestStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditRequestStatusDTO{" +
            "id=" + getId() +
            ", creditRequestStatusId=" + getCreditRequestStatusId() +
            ", creditRequestStatusDescription='" + getCreditRequestStatusDescription() + "'" +
            ", creditRequestStatusExport='" + isCreditRequestStatusExport() + "'" +
            "}";
    }
}
