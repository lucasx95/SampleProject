package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Sample entity.
 */
public class SampleDTO implements Serializable {

    private Long id;

    private String sampleName;

    private Integer sampleSize;

    private Long sampleOneId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public Integer getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(Integer sampleSize) {
        this.sampleSize = sampleSize;
    }

    public Long getSampleOneId() {
        return sampleOneId;
    }

    public void setSampleOneId(Long sampleOneToOneId) {
        this.sampleOneId = sampleOneToOneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SampleDTO sampleDTO = (SampleDTO) o;
        if(sampleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sampleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SampleDTO{" +
            "id=" + getId() +
            ", sampleName='" + getSampleName() + "'" +
            ", sampleSize=" + getSampleSize() +
            "}";
    }
}
