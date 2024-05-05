package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.Color;
import io.teamchallenge.woodCrafts.models.dto.ColorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorDto colorToColorDto(Color color);

    Color colorDtoToColor(ColorDto colorDto);
}
