package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.dto.MaterialDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialMapper {

    public static Material convertMaterialDtoToMaterial(MaterialDto materialDto) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(materialDto, Material.class);
    }

    public static MaterialDto convertMaterialToMaterialDto(Material material) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(material, MaterialDto.class);
    }
}
