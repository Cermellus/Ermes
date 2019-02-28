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
 * A CallLog.
 */
@Entity
@Table(name = "call_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CallLog implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "call_log_id", nullable = false)
    private Integer callLogId;

    @NotNull
    @Column(name = "call_log_comment", nullable = false)
    private String callLogComment;

    @NotNull
    @Column(name = "call_log_date", nullable = false)
    private LocalDate callLogDate;

    @Column(name = "call_log_contact_mail")
    private String callLogContactMail;

    @ManyToOne
    @JsonIgnoreProperties("callLogs")
    private Customer customerId;

    @ManyToOne
    @JsonIgnoreProperties("callLogs")
    private Prospect prospectId;

    @ManyToOne
    @JsonIgnoreProperties("callLogs")
    private AppUser appUserId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCallLogId() {
        return callLogId;
    }

    public CallLog callLogId(Integer callLogId) {
        this.callLogId = callLogId;
        return this;
    }

    public void setCallLogId(Integer callLogId) {
        this.callLogId = callLogId;
    }

    public String getCallLogComment() {
        return callLogComment;
    }

    public CallLog callLogComment(String callLogComment) {
        this.callLogComment = callLogComment;
        return this;
    }

    public void setCallLogComment(String callLogComment) {
        this.callLogComment = callLogComment;
    }

    public LocalDate getCallLogDate() {
        return callLogDate;
    }

    public CallLog callLogDate(LocalDate callLogDate) {
        this.callLogDate = callLogDate;
        return this;
    }

    public void setCallLogDate(LocalDate callLogDate) {
        this.callLogDate = callLogDate;
    }

    public String getCallLogContactMail() {
        return callLogContactMail;
    }

    public CallLog callLogContactMail(String callLogContactMail) {
        this.callLogContactMail = callLogContactMail;
        return this;
    }

    public void setCallLogContactMail(String callLogContactMail) {
        this.callLogContactMail = callLogContactMail;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public CallLog customerId(Customer customer) {
        this.customerId = customer;
        return this;
    }

    public void setCustomerId(Customer customer) {
        this.customerId = customer;
    }

    public Prospect getProspectId() {
        return prospectId;
    }

    public CallLog prospectId(Prospect prospect) {
        this.prospectId = prospect;
        return this;
    }

    public void setProspectId(Prospect prospect) {
        this.prospectId = prospect;
    }

    public AppUser getAppUserId() {
        return appUserId;
    }

    public CallLog appUserId(AppUser appUser) {
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
        CallLog callLog = (CallLog) o;
        if (callLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), callLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CallLog{" +
            "id=" + getId() +
            ", callLogId=" + getCallLogId() +
            ", callLogComment='" + getCallLogComment() + "'" +
            ", callLogDate='" + getCallLogDate() + "'" +
            ", callLogContactMail='" + getCallLogContactMail() + "'" +
            "}";
    }
}
