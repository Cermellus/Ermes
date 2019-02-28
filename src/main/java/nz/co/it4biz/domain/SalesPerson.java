package nz.co.it4biz.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SalesPerson.
 */
@Entity
@Table(name = "sales_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalesPerson implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sales_person_id", nullable = false)
    private Integer salesPersonId;

    @NotNull
    @Column(name = "sales_person_code", nullable = false)
    private String salesPersonCode;

    @NotNull
    @Column(name = "sales_person_name", nullable = false)
    private String salesPersonName;

    @NotNull
    @Column(name = "sales_person_mail", nullable = false)
    private String salesPersonMail;

    @NotNull
    @Column(name = "sales_person_inactive", nullable = false)
    private Boolean salesPersonInactive;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSalesPersonId() {
        return salesPersonId;
    }

    public SalesPerson salesPersonId(Integer salesPersonId) {
        this.salesPersonId = salesPersonId;
        return this;
    }

    public void setSalesPersonId(Integer salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    public String getSalesPersonCode() {
        return salesPersonCode;
    }

    public SalesPerson salesPersonCode(String salesPersonCode) {
        this.salesPersonCode = salesPersonCode;
        return this;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        this.salesPersonCode = salesPersonCode;
    }

    public String getSalesPersonName() {
        return salesPersonName;
    }

    public SalesPerson salesPersonName(String salesPersonName) {
        this.salesPersonName = salesPersonName;
        return this;
    }

    public void setSalesPersonName(String salesPersonName) {
        this.salesPersonName = salesPersonName;
    }

    public String getSalesPersonMail() {
        return salesPersonMail;
    }

    public SalesPerson salesPersonMail(String salesPersonMail) {
        this.salesPersonMail = salesPersonMail;
        return this;
    }

    public void setSalesPersonMail(String salesPersonMail) {
        this.salesPersonMail = salesPersonMail;
    }

    public Boolean isSalesPersonInactive() {
        return salesPersonInactive;
    }

    public SalesPerson salesPersonInactive(Boolean salesPersonInactive) {
        this.salesPersonInactive = salesPersonInactive;
        return this;
    }

    public void setSalesPersonInactive(Boolean salesPersonInactive) {
        this.salesPersonInactive = salesPersonInactive;
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
        SalesPerson salesPerson = (SalesPerson) o;
        if (salesPerson.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salesPerson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalesPerson{" +
            "id=" + getId() +
            ", salesPersonId=" + getSalesPersonId() +
            ", salesPersonCode='" + getSalesPersonCode() + "'" +
            ", salesPersonName='" + getSalesPersonName() + "'" +
            ", salesPersonMail='" + getSalesPersonMail() + "'" +
            ", salesPersonInactive='" + isSalesPersonInactive() + "'" +
            "}";
    }
}
