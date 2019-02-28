package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Contact entity.
 */
public class ContactDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer contactId;

    @NotNull
    private String contactCode;

    @NotNull
    private String contactName;

    @NotNull
    private String contactMail;

    private Boolean contactInactive;


    private Long customerIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getContactCode() {
        return contactCode;
    }

    public void setContactCode(String contactCode) {
        this.contactCode = contactCode;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public Boolean isContactInactive() {
        return contactInactive;
    }

    public void setContactInactive(Boolean contactInactive) {
        this.contactInactive = contactInactive;
    }

    public Long getCustomerIdId() {
        return customerIdId;
    }

    public void setCustomerIdId(Long customerId) {
        this.customerIdId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContactDTO contactDTO = (ContactDTO) o;
        if (contactDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
            "id=" + getId() +
            ", contactId=" + getContactId() +
            ", contactCode='" + getContactCode() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", contactMail='" + getContactMail() + "'" +
            ", contactInactive='" + isContactInactive() + "'" +
            ", customerId=" + getCustomerIdId() +
            "}";
    }
}
