package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Optional;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Sample.
 */
@Entity
@Table(name = "sample")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sample")
public class Sample implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sample_name")
    private String sampleName;

    @Column(name = "sample_size")
    private Integer sampleSize;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sample_one",unique = true)
    private SampleOneToOne sampleOne;

    @OneToMany(mappedBy = "sample")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SampleMany> sampleManies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSampleName() {
        return sampleName;
    }

    public Sample sampleName(String sampleName) {
        this.sampleName = sampleName;
        return this;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public Integer getSampleSize() {
        Optional<Integer> queijo;
        queijo.isPre
        return sampleSize;
    }

    public Sample sampleSize(Integer sampleSize) {
        this.sampleSize = sampleSize;
        return this;
    }

    public void setSampleSize(Integer sampleSize) {
        this.sampleSize = sampleSize;
    }

    public SampleOneToOne getSampleOne() {
        return sampleOne;
    }

    public Sample sampleOne(SampleOneToOne sampleOneToOne) {
        this.sampleOne = sampleOneToOne;
        return this;
    }

    public void setSampleOne(SampleOneToOne sampleOneToOne) {
        this.sampleOne = sampleOneToOne;
    }

    public Set<SampleMany> getSampleManies() {
        return sampleManies;
    }

    public Sample sampleManies(Set<SampleMany> sampleManies) {
        this.sampleManies = sampleManies;
        return this;
    }

    public Sample addSampleMany(SampleMany sampleMany) {
        this.sampleManies.add(sampleMany);
        sampleMany.setSample(this);
        return this;
    }

    public Sample removeSampleMany(SampleMany sampleMany) {
        this.sampleManies.remove(sampleMany);
        sampleMany.setSample(null);
        return this;
    }

    public void setSampleManies(Set<SampleMany> sampleManies) {
        this.sampleManies = sampleManies;
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
        Sample sample = (Sample) o;
        if (sample.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sample.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sample{" +
            "id=" + getId() +
            ", sampleName='" + getSampleName() + "'" +
            ", sampleSize=" + getSampleSize() +
            "}";
    }
}
