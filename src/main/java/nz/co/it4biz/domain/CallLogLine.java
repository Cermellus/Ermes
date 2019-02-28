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
 * A CallLogLine.
 */
@Entity
@Table(name = "call_log_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CallLogLine implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "call_log_line_id", nullable = false)
    private Integer callLogLineId;

    @Column(name = "call_log_comment")
    private String callLogComment;

    @NotNull
    @Column(name = "call_log_due_date", nullable = false)
    private LocalDate callLogDueDate;

    @ManyToOne
    @JsonIgnoreProperties("callLogLines")
    private Product productId;

    @ManyToOne
    @JsonIgnoreProperties("callLogLines")
    private CallLogAction callLogActionId;

    @ManyToOne
    @JsonIgnoreProperties("callLogLines")
    private CallLog callLogId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCallLogLineId() {
        return callLogLineId;
    }

    public CallLogLine callLogLineId(Integer callLogLineId) {
        this.callLogLineId = callLogLineId;
        return this;
    }

    public void setCallLogLineId(Integer callLogLineId) {
        this.callLogLineId = callLogLineId;
    }

    public String getCallLogComment() {
        return callLogComment;
    }

    public CallLogLine callLogComment(String callLogComment) {
        this.callLogComment = callLogComment;
        return this;
    }

    public void setCallLogComment(String callLogComment) {
        this.callLogComment = callLogComment;
    }

    public LocalDate getCallLogDueDate() {
        return callLogDueDate;
    }

    public CallLogLine callLogDueDate(LocalDate callLogDueDate) {
        this.callLogDueDate = callLogDueDate;
        return this;
    }

    public void setCallLogDueDate(LocalDate callLogDueDate) {
        this.callLogDueDate = callLogDueDate;
    }

    public Product getProductId() {
        return productId;
    }

    public CallLogLine productId(Product product) {
        this.productId = product;
        return this;
    }

    public void setProductId(Product product) {
        this.productId = product;
    }

    public CallLogAction getCallLogActionId() {
        return callLogActionId;
    }

    public CallLogLine callLogActionId(CallLogAction callLogAction) {
        this.callLogActionId = callLogAction;
        return this;
    }

    public void setCallLogActionId(CallLogAction callLogAction) {
        this.callLogActionId = callLogAction;
    }

    public CallLog getCallLogId() {
        return callLogId;
    }

    public CallLogLine callLogId(CallLog callLog) {
        this.callLogId = callLog;
        return this;
    }

    public void setCallLogId(CallLog callLog) {
        this.callLogId = callLog;
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
        CallLogLine callLogLine = (CallLogLine) o;
        if (callLogLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), callLogLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CallLogLine{" +
            "id=" + getId() +
            ", callLogLineId=" + getCallLogLineId() +
            ", callLogComment='" + getCallLogComment() + "'" +
            ", callLogDueDate='" + getCallLogDueDate() + "'" +
            "}";
    }
}
