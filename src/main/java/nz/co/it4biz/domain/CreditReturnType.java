package nz.co.it4biz.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CreditReturnType.
 */
@Entity
@Table(name = "credit_return_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CreditReturnType implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "credit_return_type_id", nullable = false)
    private Integer creditReturnTypeId;

    @NotNull
    @Column(name = "credit_return_type_description", nullable = false)
    private String creditReturnTypeDescription;

    @NotNull
    @Column(name = "credit_return_type_arrange_return", nullable = false)
    private Boolean creditReturnTypeArrangeReturn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditReturnTypeId() {
        return creditReturnTypeId;
    }

    public CreditReturnType creditReturnTypeId(Integer creditReturnTypeId) {
        this.creditReturnTypeId = creditReturnTypeId;
        return this;
    }

    public void setCreditReturnTypeId(Integer creditReturnTypeId) {
        this.creditReturnTypeId = creditReturnTypeId;
    }

    public String getCreditReturnTypeDescription() {
        return creditReturnTypeDescription;
    }

    public CreditReturnType creditReturnTypeDescription(String creditReturnTypeDescription) {
        this.creditReturnTypeDescription = creditReturnTypeDescription;
        return this;
    }

    public void setCreditReturnTypeDescription(String creditReturnTypeDescription) {
        this.creditReturnTypeDescription = creditReturnTypeDescription;
    }

    public Boolean isCreditReturnTypeArrangeReturn() {
        return creditReturnTypeArrangeReturn;
    }

    public CreditReturnType creditReturnTypeArrangeReturn(Boolean creditReturnTypeArrangeReturn) {
        this.creditReturnTypeArrangeReturn = creditReturnTypeArrangeReturn;
        return this;
    }

    public void setCreditReturnTypeArrangeReturn(Boolean creditReturnTypeArrangeReturn) {
        this.creditReturnTypeArrangeReturn = creditReturnTypeArrangeReturn;
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
        CreditReturnType creditReturnType = (CreditReturnType) o;
        if (creditReturnType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditReturnType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditReturnType{" +
            "id=" + getId() +
            ", creditReturnTypeId=" + getCreditReturnTypeId() +
            ", creditReturnTypeDescription='" + getCreditReturnTypeDescription() + "'" +
            ", creditReturnTypeArrangeReturn='" + isCreditReturnTypeArrangeReturn() + "'" +
            "}";
    }
}
