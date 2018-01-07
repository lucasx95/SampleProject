package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SampleMany entity.
 */
public class SampleManyDTO implements Serializable {

    private Long id;

    private String sampleMany;

    private Long sampleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSampleMany() {
        return sampleMany;
    }

    public void setSampleMany(String sampleMany) {
        this.sampleMany = sampleMany;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SampleManyDTO sampleManyDTO = (SampleManyDTO) o;
        if(sampleManyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sampleManyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SampleManyDTO{" +
            "id=" + getId() +
            ", sampleMany='" + getSampleMany() + "'" +
            "}";
    }
}
