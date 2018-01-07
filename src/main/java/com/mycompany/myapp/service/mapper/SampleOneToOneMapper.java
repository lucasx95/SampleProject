package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SampleOneToOneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SampleOneToOne and its DTO SampleOneToOneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SampleOneToOneMapper extends EntityMapper<SampleOneToOneDTO, SampleOneToOne> {



    default SampleOneToOne fromId(Long id) {
        if (id == null) {
            return null;
        }
        SampleOneToOne sampleOneToOne = new SampleOneToOne();
        sampleOneToOne.setId(id);
        return sampleOneToOne;
    }
}
