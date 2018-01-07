package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SampleOneToOne;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SampleOneToOne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleOneToOneRepository extends JpaRepository<SampleOneToOne, Long> {

}
