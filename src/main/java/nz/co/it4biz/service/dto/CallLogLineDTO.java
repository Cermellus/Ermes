package nz.co.it4biz.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CallLogLine entity.
 */
public class CallLogLineDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer callLogLineId;

    private String callLogComment;

    @NotNull
    private LocalDate callLogDueDate;


    private Long productIdId;

    private Long callLogActionIdId;

    private Long callLogIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCallLogLineId() {
        return callLogLineId;
    }

    public void setCallLogLineId(Integer callLogLineId) {
        this.callLogLineId = callLogLineId;
    }

    public String getCallLogComment() {
        return callLogComment;
    }

    public void setCallLogComment(String callLogComment) {
        this.callLogComment = callLogComment;
    }

    public LocalDate getCallLogDueDate() {
        return callLogDueDate;
    }

    public void setCallLogDueDate(LocalDate callLogDueDate) {
        this.callLogDueDate = callLogDueDate;
    }

    public Long getProductIdId() {
        return productIdId;
    }

    public void setProductIdId(Long productId) {
        this.productIdId = productId;
    }

    public Long getCallLogActionIdId() {
        return callLogActionIdId;
    }

    public void setCallLogActionIdId(Long callLogActionId) {
        this.callLogActionIdId = callLogActionId;
    }

    public Long getCallLogIdId() {
        return callLogIdId;
    }

    public void setCallLogIdId(Long callLogId) {
        this.callLogIdId = callLogId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CallLogLineDTO callLogLineDTO = (CallLogLineDTO) o;
        if (callLogLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), callLogLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CallLogLineDTO{" +
            "id=" + getId() +
            ", callLogLineId=" + getCallLogLineId() +
            ", callLogComment='" + getCallLogComment() + "'" +
            ", callLogDueDate='" + getCallLogDueDate() + "'" +
            ", productId=" + getProductIdId() +
            ", callLogActionId=" + getCallLogActionIdId() +
            ", callLogId=" + getCallLogIdId() +
            "}";
    }
}
