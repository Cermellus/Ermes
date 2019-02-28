package nz.co.it4biz.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CreditRequestStatus.
 */
@Entity
@Table(name = "credit_request_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CreditRequestStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "credit_request_status_id", nullable = false)
    private Integer creditRequestStatusId;

    @NotNull
    @Column(name = "credit_request_status_description", nullable = false)
    private String creditRequestStatusDescription;

    @NotNull
    @Column(name = "credit_request_status_export", nullable = false)
    private Boolean creditRequestStatusExport;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditRequestStatusId() {
        return creditRequestStatusId;
    }

    public CreditRequestStatus creditRequestStatusId(Integer creditRequestStatusId) {
        this.creditRequestStatusId = creditRequestStatusId;
        return this;
    }

    public void setCreditRequestStatusId(Integer creditRequestStatusId) {
        this.creditRequestStatusId = creditRequestStatusId;
    }

    public String getCreditRequestStatusDescription() {
        return creditRequestStatusDescription;
    }

    public CreditRequestStatus creditRequestStatusDescription(String creditRequestStatusDescription) {
        this.creditRequestStatusDescription = creditRequestStatusDescription;
        return this;
    }

    public void setCreditRequestStatusDescription(String creditRequestStatusDescription) {
        this.creditRequestStatusDescription = creditRequestStatusDescription;
    }

    public Boolean isCreditRequestStatusExport() {
        return creditRequestStatusExport;
    }

    public CreditRequestStatus creditRequestStatusExport(Boolean creditRequestStatusExport) {
        this.creditRequestStatusExport = creditRequestStatusExport;
        return this;
    }

    public void setCreditRequestStatusExport(Boolean creditRequestStatusExport) {
        this.creditRequestStatusExport = creditRequestStatusExport;
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
        CreditRequestStatus creditRequestStatus = (CreditRequestStatus) o;
        if (creditRequestStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditRequestStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditRequestStatus{" +
            "id=" + getId() +
            ", creditRequestStatusId=" + getCreditRequestStatusId() +
            ", creditRequestStatusDescription='" + getCreditRequestStatusDescription() + "'" +
            ", creditRequestStatusExport='" + isCreditRequestStatusExport() + "'" +
            "}";
    }
}
