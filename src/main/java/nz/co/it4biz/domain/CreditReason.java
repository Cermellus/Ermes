package nz.co.it4biz.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CreditReason.
 */
@Entity
@Table(name = "credit_reason")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CreditReason implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "credit_reason_id", nullable = false)
    private Integer creditReasonId;

    @NotNull
    @Column(name = "credit_reason_description", nullable = false)
    private String creditReasonDescription;

    @NotNull
    @Column(name = "credit_reason_code", nullable = false)
    private String creditReasonCode;

    @NotNull
    @Column(name = "credit_reason_product_required", nullable = false)
    private Boolean creditReasonProductRequired;

    @NotNull
    @Column(name = "credit_reason_courier_claim", nullable = false)
    private Boolean creditReasonCourierClaim;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditReasonId() {
        return creditReasonId;
    }

    public CreditReason creditReasonId(Integer creditReasonId) {
        this.creditReasonId = creditReasonId;
        return this;
    }

    public void setCreditReasonId(Integer creditReasonId) {
        this.creditReasonId = creditReasonId;
    }

    public String getCreditReasonDescription() {
        return creditReasonDescription;
    }

    public CreditReason creditReasonDescription(String creditReasonDescription) {
        this.creditReasonDescription = creditReasonDescription;
        return this;
    }

    public void setCreditReasonDescription(String creditReasonDescription) {
        this.creditReasonDescription = creditReasonDescription;
    }

    public String getCreditReasonCode() {
        return creditReasonCode;
    }

    public CreditReason creditReasonCode(String creditReasonCode) {
        this.creditReasonCode = creditReasonCode;
        return this;
    }

    public void setCreditReasonCode(String creditReasonCode) {
        this.creditReasonCode = creditReasonCode;
    }

    public Boolean isCreditReasonProductRequired() {
        return creditReasonProductRequired;
    }

    public CreditReason creditReasonProductRequired(Boolean creditReasonProductRequired) {
        this.creditReasonProductRequired = creditReasonProductRequired;
        return this;
    }

    public void setCreditReasonProductRequired(Boolean creditReasonProductRequired) {
        this.creditReasonProductRequired = creditReasonProductRequired;
    }

    public Boolean isCreditReasonCourierClaim() {
        return creditReasonCourierClaim;
    }

    public CreditReason creditReasonCourierClaim(Boolean creditReasonCourierClaim) {
        this.creditReasonCourierClaim = creditReasonCourierClaim;
        return this;
    }

    public void setCreditReasonCourierClaim(Boolean creditReasonCourierClaim) {
        this.creditReasonCourierClaim = creditReasonCourierClaim;
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
        CreditReason creditReason = (CreditReason) o;
        if (creditReason.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditReason.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditReason{" +
            "id=" + getId() +
            ", creditReasonId=" + getCreditReasonId() +
            ", creditReasonDescription='" + getCreditReasonDescription() + "'" +
            ", creditReasonCode='" + getCreditReasonCode() + "'" +
            ", creditReasonProductRequired='" + isCreditReasonProductRequired() + "'" +
            ", creditReasonCourierClaim='" + isCreditReasonCourierClaim() + "'" +
            "}";
    }
}
