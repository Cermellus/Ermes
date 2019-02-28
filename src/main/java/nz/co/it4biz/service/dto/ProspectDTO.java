package nz.co.it4biz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Prospect entity.
 */
public class ProspectDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer prospectId;

    @NotNull
    private String prospectName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProspectId() {
        return prospectId;
    }

    public void setProspectId(Integer prospectId) {
        this.prospectId = prospectId;
    }

    public String getProspectName() {
        return prospectName;
    }

    public void setProspectName(String prospectName) {
        this.prospectName = prospectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProspectDTO prospectDTO = (ProspectDTO) o;
        if (prospectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prospectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProspectDTO{" +
            "id=" + getId() +
            ", prospectId=" + getProspectId() +
            ", prospectName='" + getProspectName() + "'" +
            "}";
    }
}
