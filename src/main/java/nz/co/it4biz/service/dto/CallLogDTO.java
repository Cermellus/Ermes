package nz.co.it4biz.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CallLog entity.
 */
public class CallLogDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer callLogId;

    @NotNull
    private String callLogComment;

    @NotNull
    private LocalDate callLogDate;

    private String callLogContactMail;


    private Long customerIdId;

    private Long prospectIdId;

    private Long appUserIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCallLogId() {
        return callLogId;
    }

    public void setCallLogId(Integer callLogId) {
        this.callLogId = callLogId;
    }

    public String getCallLogComment() {
        return callLogComment;
    }

    public void setCallLogComment(String callLogComment) {
        this.callLogComment = callLogComment;
    }

    public LocalDate getCallLogDate() {
        return callLogDate;
    }

    public void setCallLogDate(LocalDate callLogDate) {
        this.callLogDate = callLogDate;
    }

    public String getCallLogContactMail() {
        return callLogContactMail;
    }

    public void setCallLogContactMail(String callLogContactMail) {
        this.callLogContactMail = callLogContactMail;
    }

    public Long getCustomerIdId() {
        return customerIdId;
    }

    public void setCustomerIdId(Long customerId) {
        this.customerIdId = customerId;
    }

    public Long getProspectIdId() {
        return prospectIdId;
    }

    public void setProspectIdId(Long prospectId) {
        this.prospectIdId = prospectId;
    }

    public Long getAppUserIdId() {
        return appUserIdId;
    }

    public void setAppUserIdId(Long appUserId) {
        this.appUserIdId = appUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CallLogDTO callLogDTO = (CallLogDTO) o;
        if (callLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), callLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CallLogDTO{" +
            "id=" + getId() +
            ", callLogId=" + getCallLogId() +
            ", callLogComment='" + getCallLogComment() + "'" +
            ", callLogDate='" + getCallLogDate() + "'" +
            ", callLogContactMail='" + getCallLogContactMail() + "'" +
            ", customerId=" + getCustomerIdId() +
            ", prospectId=" + getProspectIdId() +
            ", appUserId=" + getAppUserIdId() +
            "}";
    }
}
