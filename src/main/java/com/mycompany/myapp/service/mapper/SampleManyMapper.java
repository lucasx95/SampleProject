package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SampleManyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SampleMany and its DTO SampleManyDTO.
 */
@Mapper(componentModel = "spring", uses = {SampleMapper.class})
public interface SampleManyMapper extends EntityMapper<SampleManyDTO, SampleMany> {

    @Mapping(source = "sample.id", target = "sampleId")
    SampleManyDTO toDto(SampleMany sampleMany);

    @Mapping(source = "sampleId", target = "sample")
    SampleMany toEntity(SampleManyDTO sampleManyDTO);

    default SampleMany fromId(Long id) {
        if (id == null) {
            return null;
        }
        SampleMany sampleMany = new SampleMany();
        sampleMany.setId(id);
        return sampleMany;
    }
}
