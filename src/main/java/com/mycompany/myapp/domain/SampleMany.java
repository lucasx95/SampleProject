package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SampleMany.
 */
@Entity
@Table(name = "sample_many")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "samplemany")
public class SampleMany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sample_many")
    private String sampleMany;

    @ManyToOne
    private Sample sample;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSampleMany() {
        return sampleMany;
    }

    public SampleMany sampleMany(String sampleMany) {
        this.sampleMany = sampleMany;
        return this;
    }

    public void setSampleMany(String sampleMany) {
        this.sampleMany = sampleMany;
    }

    public Sample getSample() {
        return sample;
    }

    public SampleMany sample(Sample sample) {
        this.sample = sample;
        return this;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
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
        SampleMany sampleMany = (SampleMany) o;
        if (sampleMany.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sampleMany.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SampleMany{" +
            "id=" + getId() +
            ", sampleMany='" + getSampleMany() + "'" +
            "}";
    }
}
