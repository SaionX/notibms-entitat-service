package es.caib.notib.entitat.mapper;

import es.caib.notib.entitat.dto.EntitatDto;
import es.caib.notib.entitat.entity.EntitatEntity;
import org.mapstruct.Mapper;

@Mapper
//@Mapper(uses = DateMapper.class)
//@DecoratedWith(EntitatMapperDecorator.class)
public interface EntitatMapper {

    EntitatDto entitatToDto(EntitatEntity entitat);
    EntitatEntity dtoToEntitat(EntitatDto dto);

}
