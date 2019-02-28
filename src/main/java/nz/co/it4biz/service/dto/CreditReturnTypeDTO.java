package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CreditReturnType entity.
 */
public class CreditReturnTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer creditReturnTypeId;

    @NotNull
    private String creditReturnTypeDescription;

    @NotNull
    private Boolean creditReturnTypeArrangeReturn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditReturnTypeId() {
        return creditReturnTypeId;
    }

    public void setCreditReturnTypeId(Integer creditReturnTypeId) {
        this.creditReturnTypeId = creditReturnTypeId;
    }

    public String getCreditReturnTypeDescription() {
        return creditReturnTypeDescription;
    }

    public void setCreditReturnTypeDescription(String creditReturnTypeDescription) {
        this.creditReturnTypeDescription = creditReturnTypeDescription;
    }

    public Boolean isCreditReturnTypeArrangeReturn() {
        return creditReturnTypeArrangeReturn;
    }

    public void setCreditReturnTypeArrangeReturn(Boolean creditReturnTypeArrangeReturn) {
        this.creditReturnTypeArrangeReturn = creditReturnTypeArrangeReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditReturnTypeDTO creditReturnTypeDTO = (CreditReturnTypeDTO) o;
        if (creditReturnTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditReturnTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditReturnTypeDTO{" +
            "id=" + getId() +
            ", creditReturnTypeId=" + getCreditReturnTypeId() +
            ", creditReturnTypeDescription='" + getCreditReturnTypeDescription() + "'" +
            ", creditReturnTypeArrangeReturn='" + isCreditReturnTypeArrangeReturn() + "'" +
            "}";
    }
}
