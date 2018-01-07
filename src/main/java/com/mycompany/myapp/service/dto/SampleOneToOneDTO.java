package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SampleOneToOne entity.
 */
public class SampleOneToOneDTO implements Serializable {

    private Long id;

    private String sampleOne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSampleOne() {
        return sampleOne;
    }

    public void setSampleOne(String sampleOne) {
        this.sampleOne = sampleOne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SampleOneToOneDTO sampleOneToOneDTO = (SampleOneToOneDTO) o;
        if(sampleOneToOneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sampleOneToOneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SampleOneToOneDTO{" +
            "id=" + getId() +
            ", sampleOne='" + getSampleOne() + "'" +
            "}";
    }
}
