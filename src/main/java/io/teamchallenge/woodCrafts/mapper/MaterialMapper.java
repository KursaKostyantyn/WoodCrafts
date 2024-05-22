package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.dto.MaterialDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    MaterialDto materialToMaterialDto(Material material);

    Material materialDtoToMaterial(MaterialDto materialDto);
}
