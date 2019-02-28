package nz.co.it4biz.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "contact_id", nullable = false)
    private Integer contactId;

    @NotNull
    @Column(name = "contact_code", nullable = false)
    private String contactCode;

    @NotNull
    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @NotNull
    @Column(name = "contact_mail", nullable = false)
    private String contactMail;

    @Column(name = "contact_inactive")
    private Boolean contactInactive;

    @ManyToOne
    @JsonIgnoreProperties("contacts")
    private Customer customerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContactId() {
        return contactId;
    }

    public Contact contactId(Integer contactId) {
        this.contactId = contactId;
        return this;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getContactCode() {
        return contactCode;
    }

    public Contact contactCode(String contactCode) {
        this.contactCode = contactCode;
        return this;
    }

    public void setContactCode(String contactCode) {
        this.contactCode = contactCode;
    }

    public String getContactName() {
        return contactName;
    }

    public Contact contactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMail() {
        return contactMail;
    }

    public Contact contactMail(String contactMail) {
        this.contactMail = contactMail;
        return this;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public Boolean isContactInactive() {
        return contactInactive;
    }

    public Contact contactInactive(Boolean contactInactive) {
        this.contactInactive = contactInactive;
        return this;
    }

    public void setContactInactive(Boolean contactInactive) {
        this.contactInactive = contactInactive;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public Contact customerId(Customer customer) {
        this.customerId = customer;
        return this;
    }

    public void setCustomerId(Customer customer) {
        this.customerId = customer;
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
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", contactId=" + getContactId() +
            ", contactCode='" + getContactCode() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", contactMail='" + getContactMail() + "'" +
            ", contactInactive='" + isContactInactive() + "'" +
            "}";
    }
}
