package nz.co.it4biz.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CreditReferenceType.
 */
@Entity
@Table(name = "credit_reference_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CreditReferenceType implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "credit_reference_type_id", nullable = false)
    private Integer creditReferenceTypeId;

    @NotNull
    @Column(name = "credit_reference_type_description", nullable = false)
    private String creditReferenceTypeDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditReferenceTypeId() {
        return creditReferenceTypeId;
    }

    public CreditReferenceType creditReferenceTypeId(Integer creditReferenceTypeId) {
        this.creditReferenceTypeId = creditReferenceTypeId;
        return this;
    }

    public void setCreditReferenceTypeId(Integer creditReferenceTypeId) {
        this.creditReferenceTypeId = creditReferenceTypeId;
    }

    public String getCreditReferenceTypeDescription() {
        return creditReferenceTypeDescription;
    }

    public CreditReferenceType creditReferenceTypeDescription(String creditReferenceTypeDescription) {
        this.creditReferenceTypeDescription = creditReferenceTypeDescription;
        return this;
    }

    public void setCreditReferenceTypeDescription(String creditReferenceTypeDescription) {
        this.creditReferenceTypeDescription = creditReferenceTypeDescription;
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
        CreditReferenceType creditReferenceType = (CreditReferenceType) o;
        if (creditReferenceType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditReferenceType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditReferenceType{" +
            "id=" + getId() +
            ", creditReferenceTypeId=" + getCreditReferenceTypeId() +
            ", creditReferenceTypeDescription='" + getCreditReferenceTypeDescription() + "'" +
            "}";
    }
}
