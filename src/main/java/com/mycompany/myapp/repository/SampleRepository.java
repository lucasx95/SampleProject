package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sample;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sample entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {

}
