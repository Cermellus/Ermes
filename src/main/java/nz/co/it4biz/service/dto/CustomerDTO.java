package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Customer entity.
 */
public class CustomerDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer customerId;

    @NotNull
    private String customerCode;

    @NotNull
    private String customerName;

    @NotNull
    private Boolean customerInactive;


    private Long prospectIdId;

    private Long salesPersonIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean isCustomerInactive() {
        return customerInactive;
    }

    public void setCustomerInactive(Boolean customerInactive) {
        this.customerInactive = customerInactive;
    }

    public Long getProspectIdId() {
        return prospectIdId;
    }

    public void setProspectIdId(Long prospectId) {
        this.prospectIdId = prospectId;
    }

    public Long getSalesPersonIdId() {
        return salesPersonIdId;
    }

    public void setSalesPersonIdId(Long salesPersonId) {
        this.salesPersonIdId = salesPersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (customerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + getId() +
            ", customerId=" + getCustomerId() +
            ", customerCode='" + getCustomerCode() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", customerInactive='" + isCustomerInactive() + "'" +
            ", prospectId=" + getProspectIdId() +
            ", salesPersonId=" + getSalesPersonIdId() +
            "}";
    }
}
