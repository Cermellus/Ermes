package nz.co.it4biz.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @NotNull
    @Column(name = "customer_code", nullable = false)
    private String customerCode;

    @NotNull
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @NotNull
    @Column(name = "customer_inactive", nullable = false)
    private Boolean customerInactive;

    @OneToOne
    @JoinColumn(unique = true)
    private Prospect prospectId;

    @ManyToOne
    @JsonIgnoreProperties("customers")
    private SalesPerson salesPersonId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Customer customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public Customer customerCode(String customerCode) {
        this.customerCode = customerCode;
        return this;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Customer customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean isCustomerInactive() {
        return customerInactive;
    }

    public Customer customerInactive(Boolean customerInactive) {
        this.customerInactive = customerInactive;
        return this;
    }

    public void setCustomerInactive(Boolean customerInactive) {
        this.customerInactive = customerInactive;
    }

    public Prospect getProspectId() {
        return prospectId;
    }

    public Customer prospectId(Prospect prospect) {
        this.prospectId = prospect;
        return this;
    }

    public void setProspectId(Prospect prospect) {
        this.prospectId = prospect;
    }

    public SalesPerson getSalesPersonId() {
        return salesPersonId;
    }

    public Customer salesPersonId(SalesPerson salesPerson) {
        this.salesPersonId = salesPerson;
        return this;
    }

    public void setSalesPersonId(SalesPerson salesPerson) {
        this.salesPersonId = salesPerson;
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
        Customer customer = (Customer) o;
        if (customer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerId=" + getCustomerId() +
            ", customerCode='" + getCustomerCode() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", customerInactive='" + isCustomerInactive() + "'" +
            "}";
    }
}
