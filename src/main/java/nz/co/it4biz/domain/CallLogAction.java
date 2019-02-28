package nz.co.it4biz.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CallLogAction.
 */
@Entity
@Table(name = "call_log_action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CallLogAction implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "call_log_action_id", nullable = false)
    private Integer callLogActionId;

    @NotNull
    @Column(name = "call_log_action_description", nullable = false)
    private String callLogActionDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCallLogActionId() {
        return callLogActionId;
    }

    public CallLogAction callLogActionId(Integer callLogActionId) {
        this.callLogActionId = callLogActionId;
        return this;
    }

    public void setCallLogActionId(Integer callLogActionId) {
        this.callLogActionId = callLogActionId;
    }

    public String getCallLogActionDescription() {
        return callLogActionDescription;
    }

    public CallLogAction callLogActionDescription(String callLogActionDescription) {
        this.callLogActionDescription = callLogActionDescription;
        return this;
    }

    public void setCallLogActionDescription(String callLogActionDescription) {
        this.callLogActionDescription = callLogActionDescription;
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
        CallLogAction callLogAction = (CallLogAction) o;
        if (callLogAction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), callLogAction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CallLogAction{" +
            "id=" + getId() +
            ", callLogActionId=" + getCallLogActionId() +
            ", callLogActionDescription='" + getCallLogActionDescription() + "'" +
            "}";
    }
}
