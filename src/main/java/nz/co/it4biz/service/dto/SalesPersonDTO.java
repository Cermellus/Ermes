package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SalesPerson entity.
 */
public class SalesPersonDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer salesPersonId;

    @NotNull
    private String salesPersonCode;

    @NotNull
    private String salesPersonName;

    @NotNull
    private String salesPersonMail;

    @NotNull
    private Boolean salesPersonInactive;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(Integer salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    public String getSalesPersonCode() {
        return salesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        this.salesPersonCode = salesPersonCode;
    }

    public String getSalesPersonName() {
        return salesPersonName;
    }

    public void setSalesPersonName(String salesPersonName) {
        this.salesPersonName = salesPersonName;
    }

    public String getSalesPersonMail() {
        return salesPersonMail;
    }

    public void setSalesPersonMail(String salesPersonMail) {
        this.salesPersonMail = salesPersonMail;
    }

    public Boolean isSalesPersonInactive() {
        return salesPersonInactive;
    }

    public void setSalesPersonInactive(Boolean salesPersonInactive) {
        this.salesPersonInactive = salesPersonInactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalesPersonDTO salesPersonDTO = (SalesPersonDTO) o;
        if (salesPersonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salesPersonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalesPersonDTO{" +
            "id=" + getId() +
            ", salesPersonId=" + getSalesPersonId() +
            ", salesPersonCode='" + getSalesPersonCode() + "'" +
            ", salesPersonName='" + getSalesPersonName() + "'" +
            ", salesPersonMail='" + getSalesPersonMail() + "'" +
            ", salesPersonInactive='" + isSalesPersonInactive() + "'" +
            "}";
    }
}
