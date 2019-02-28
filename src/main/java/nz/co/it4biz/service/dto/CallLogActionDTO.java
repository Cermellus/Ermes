package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CallLogAction entity.
 */
public class CallLogActionDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer callLogActionId;

    @NotNull
    private String callLogActionDescription;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCallLogActionId() {
        return callLogActionId;
    }

    public void setCallLogActionId(Integer callLogActionId) {
        this.callLogActionId = callLogActionId;
    }

    public String getCallLogActionDescription() {
        return callLogActionDescription;
    }

    public void setCallLogActionDescription(String callLogActionDescription) {
        this.callLogActionDescription = callLogActionDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CallLogActionDTO callLogActionDTO = (CallLogActionDTO) o;
        if (callLogActionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), callLogActionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CallLogActionDTO{" +
            "id=" + getId() +
            ", callLogActionId=" + getCallLogActionId() +
            ", callLogActionDescription='" + getCallLogActionDescription() + "'" +
            "}";
    }
}
