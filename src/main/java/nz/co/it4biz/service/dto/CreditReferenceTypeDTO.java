package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CreditReferenceType entity.
 */
public class CreditReferenceTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer creditReferenceTypeId;

    @NotNull
    private String creditReferenceTypeDescription;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditReferenceTypeId() {
        return creditReferenceTypeId;
    }

    public void setCreditReferenceTypeId(Integer creditReferenceTypeId) {
        this.creditReferenceTypeId = creditReferenceTypeId;
    }

    public String getCreditReferenceTypeDescription() {
        return creditReferenceTypeDescription;
    }

    public void setCreditReferenceTypeDescription(String creditReferenceTypeDescription) {
        this.creditReferenceTypeDescription = creditReferenceTypeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditReferenceTypeDTO creditReferenceTypeDTO = (CreditReferenceTypeDTO) o;
        if (creditReferenceTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditReferenceTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditReferenceTypeDTO{" +
            "id=" + getId() +
            ", creditReferenceTypeId=" + getCreditReferenceTypeId() +
            ", creditReferenceTypeDescription='" + getCreditReferenceTypeDescription() + "'" +
            "}";
    }
}
