package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SampleMany;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SampleMany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleManyRepository extends JpaRepository<SampleMany, Long> {

}
