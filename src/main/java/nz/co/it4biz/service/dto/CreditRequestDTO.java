package nz.co.it4biz.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CreditRequest entity.
 */
public class CreditRequestDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer creditRequestId;

    @NotNull
    private LocalDate creditRequestDate;

    @NotNull
    private String creditRequestCode;

    @NotNull
    private String creditRequestReference;

    private String creditRequestContactMail;

    private String creditRequestRejectReason;


    private Long customerIdId;

    private Long creditReferenceTypeIdId;

    private Long creditRequestStatusIdId;

    private Long appUserIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditRequestId() {
        return creditRequestId;
    }

    public void setCreditRequestId(Integer creditRequestId) {
        this.creditRequestId = creditRequestId;
    }

    public LocalDate getCreditRequestDate() {
        return creditRequestDate;
    }

    public void setCreditRequestDate(LocalDate creditRequestDate) {
        this.creditRequestDate = creditRequestDate;
    }

    public String getCreditRequestCode() {
        return creditRequestCode;
    }

    public void setCreditRequestCode(String creditRequestCode) {
        this.creditRequestCode = creditRequestCode;
    }

    public String getCreditRequestReference() {
        return creditRequestReference;
    }

    public void setCreditRequestReference(String creditRequestReference) {
        this.creditRequestReference = creditRequestReference;
    }

    public String getCreditRequestContactMail() {
        return creditRequestContactMail;
    }

    public void setCreditRequestContactMail(String creditRequestContactMail) {
        this.creditRequestContactMail = creditRequestContactMail;
    }

    public String getCreditRequestRejectReason() {
        return creditRequestRejectReason;
    }

    public void setCreditRequestRejectReason(String creditRequestRejectReason) {
        this.creditRequestRejectReason = creditRequestRejectReason;
    }

    public Long getCustomerIdId() {
        return customerIdId;
    }

    public void setCustomerIdId(Long customerId) {
        this.customerIdId = customerId;
    }

    public Long getCreditReferenceTypeIdId() {
        return creditReferenceTypeIdId;
    }

    public void setCreditReferenceTypeIdId(Long creditReferenceTypeId) {
        this.creditReferenceTypeIdId = creditReferenceTypeId;
    }

    public Long getCreditRequestStatusIdId() {
        return creditRequestStatusIdId;
    }

    public void setCreditRequestStatusIdId(Long creditRequestStatusId) {
        this.creditRequestStatusIdId = creditRequestStatusId;
    }

    public Long getAppUserIdId() {
        return appUserIdId;
    }

    public void setAppUserIdId(Long appUserId) {
        this.appUserIdId = appUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditRequestDTO creditRequestDTO = (CreditRequestDTO) o;
        if (creditRequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditRequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditRequestDTO{" +
            "id=" + getId() +
            ", creditRequestId=" + getCreditRequestId() +
            ", creditRequestDate='" + getCreditRequestDate() + "'" +
            ", creditRequestCode='" + getCreditRequestCode() + "'" +
            ", creditRequestReference='" + getCreditRequestReference() + "'" +
            ", creditRequestContactMail='" + getCreditRequestContactMail() + "'" +
            ", creditRequestRejectReason='" + getCreditRequestRejectReason() + "'" +
            ", customerId=" + getCustomerIdId() +
            ", creditReferenceTypeId=" + getCreditReferenceTypeIdId() +
            ", creditRequestStatusId=" + getCreditRequestStatusIdId() +
            ", appUserId=" + getAppUserIdId() +
            "}";
    }
}
