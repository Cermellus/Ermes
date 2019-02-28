package nz.co.it4biz.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Prospect.
 */
@Entity
@Table(name = "prospect")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prospect implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "prospect_id", nullable = false)
    private Integer prospectId;

    @NotNull
    @Column(name = "prospect_name", nullable = false)
    private String prospectName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProspectId() {
        return prospectId;
    }

    public Prospect prospectId(Integer prospectId) {
        this.prospectId = prospectId;
        return this;
    }

    public void setProspectId(Integer prospectId) {
        this.prospectId = prospectId;
    }

    public String getProspectName() {
        return prospectName;
    }

    public Prospect prospectName(String prospectName) {
        this.prospectName = prospectName;
        return this;
    }

    public void setProspectName(String prospectName) {
        this.prospectName = prospectName;
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
        Prospect prospect = (Prospect) o;
        if (prospect.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prospect.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prospect{" +
            "id=" + getId() +
            ", prospectId=" + getProspectId() +
            ", prospectName='" + getProspectName() + "'" +
            "}";
    }
}
