package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SampleOneToOne.
 */
@Entity
@Table(name = "sample_one_to_one")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sampleonetoone")
public class SampleOneToOne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sample_one")
    private String sampleOne;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSampleOne() {
        return sampleOne;
    }

    public SampleOneToOne sampleOne(String sampleOne) {
        this.sampleOne = sampleOne;
        return this;
    }

    public void setSampleOne(String sampleOne) {
        this.sampleOne = sampleOne;
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
        SampleOneToOne sampleOneToOne = (SampleOneToOne) o;
        if (sampleOneToOne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sampleOneToOne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SampleOneToOne{" +
            "id=" + getId() +
            ", sampleOne='" + getSampleOne() + "'" +
            "}";
    }
}
