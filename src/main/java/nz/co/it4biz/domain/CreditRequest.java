package nz.co.it4biz.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CreditRequest.
 */
@Entity
@Table(name = "credit_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CreditRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "credit_request_id", nullable = false)
    private Integer creditRequestId;

    @NotNull
    @Column(name = "credit_request_date", nullable = false)
    private LocalDate creditRequestDate;

    @NotNull
    @Column(name = "credit_request_code", nullable = false)
    private String creditRequestCode;

    @NotNull
    @Column(name = "credit_request_reference", nullable = false)
    private String creditRequestReference;

    @Column(name = "credit_request_contact_mail")
    private String creditRequestContactMail;

    @Column(name = "credit_request_reject_reason")
    private String creditRequestRejectReason;

    @ManyToOne
    @JsonIgnoreProperties("creditRequests")
    private Customer customerId;

    @ManyToOne
    @JsonIgnoreProperties("creditRequests")
    private CreditReferenceType creditReferenceTypeId;

    @ManyToOne
    @JsonIgnoreProperties("creditRequests")
    private CreditRequestStatus creditRequestStatusId;

    @ManyToOne
    @JsonIgnoreProperties("creditRequests")
    private AppUser appUserId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditRequestId() {
        return creditRequestId;
    }

    public CreditRequest creditRequestId(Integer creditRequestId) {
        this.creditRequestId = creditRequestId;
        return this;
    }

    public void setCreditRequestId(Integer creditRequestId) {
        this.creditRequestId = creditRequestId;
    }

    public LocalDate getCreditRequestDate() {
        return creditRequestDate;
    }

    public CreditRequest creditRequestDate(LocalDate creditRequestDate) {
        this.creditRequestDate = creditRequestDate;
        return this;
    }

    public void setCreditRequestDate(LocalDate creditRequestDate) {
        this.creditRequestDate = creditRequestDate;
    }

    public String getCreditRequestCode() {
        return creditRequestCode;
    }

    public CreditRequest creditRequestCode(String creditRequestCode) {
        this.creditRequestCode = creditRequestCode;
        return this;
    }

    public void setCreditRequestCode(String creditRequestCode) {
        this.creditRequestCode = creditRequestCode;
    }

    public String getCreditRequestReference() {
        return creditRequestReference;
    }

    public CreditRequest creditRequestReference(String creditRequestReference) {
        this.creditRequestReference = creditRequestReference;
        return this;
    }

    public void setCreditRequestReference(String creditRequestReference) {
        this.creditRequestReference = creditRequestReference;
    }

    public String getCreditRequestContactMail() {
        return creditRequestContactMail;
    }

    public CreditRequest creditRequestContactMail(String creditRequestContactMail) {
        this.creditRequestContactMail = creditRequestContactMail;
        return this;
    }

    public void setCreditRequestContactMail(String creditRequestContactMail) {
        this.creditRequestContactMail = creditRequestContactMail;
    }

    public String getCreditRequestRejectReason() {
        return creditRequestRejectReason;
    }

    public CreditRequest creditRequestRejectReason(String creditRequestRejectReason) {
        this.creditRequestRejectReason = creditRequestRejectReason;
        return this;
    }

    public void setCreditRequestRejectReason(String creditRequestRejectReason) {
        this.creditRequestRejectReason = creditRequestRejectReason;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public CreditRequest customerId(Customer customer) {
        this.customerId = customer;
        return this;
    }

    public void setCustomerId(Customer customer) {
        this.customerId = customer;
    }

    public CreditReferenceType getCreditReferenceTypeId() {
        return creditReferenceTypeId;
    }

    public CreditRequest creditReferenceTypeId(CreditReferenceType creditReferenceType) {
        this.creditReferenceTypeId = creditReferenceType;
        return this;
    }

    public void setCreditReferenceTypeId(CreditReferenceType creditReferenceType) {
        this.creditReferenceTypeId = creditReferenceType;
    }

    public CreditRequestStatus getCreditRequestStatusId() {
        return creditRequestStatusId;
    }

    public CreditRequest creditRequestStatusId(CreditRequestStatus creditRequestStatus) {
        this.creditRequestStatusId = creditRequestStatus;
        return this;
    }

    public void setCreditRequestStatusId(CreditRequestStatus creditRequestStatus) {
        this.creditRequestStatusId = creditRequestStatus;
    }

    public AppUser getAppUserId() {
        return appUserId;
    }

    public CreditRequest appUserId(AppUser appUser) {
        this.appUserId = appUser;
        return this;
    }

    public void setAppUserId(AppUser appUser) {
        this.appUserId = appUser;
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
        CreditRequest creditRequest = (CreditRequest) o;
        if (creditRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditRequest{" +
            "id=" + getId() +
            ", creditRequestId=" + getCreditRequestId() +
            ", creditRequestDate='" + getCreditRequestDate() + "'" +
            ", creditRequestCode='" + getCreditRequestCode() + "'" +
            ", creditRequestReference='" + getCreditRequestReference() + "'" +
            ", creditRequestContactMail='" + getCreditRequestContactMail() + "'" +
            ", creditRequestRejectReason='" + getCreditRequestRejectReason() + "'" +
            "}";
    }
}
