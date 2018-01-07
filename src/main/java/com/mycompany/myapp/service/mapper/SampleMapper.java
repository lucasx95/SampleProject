package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SampleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sample and its DTO SampleDTO.
 */
@Mapper(componentModel = "spring", uses = {SampleOneToOneMapper.class})
public interface SampleMapper extends EntityMapper<SampleDTO, Sample> {

    @Mapping(source = "sampleOne.id", target = "sampleOneId")
    SampleDTO toDto(Sample sample);

    @Mapping(source = "sampleOneId", target = "sampleOne")
    @Mapping(target = "sampleManies", ignore = true)
    Sample toEntity(SampleDTO sampleDTO);

    default Sample fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sample sample = new Sample();
        sample.setId(id);
        return sample;
    }
}
