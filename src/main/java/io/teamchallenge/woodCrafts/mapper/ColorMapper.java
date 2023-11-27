package io.teamchallenge.woodCrafts.mapper;

import io.teamchallenge.woodCrafts.models.Color;
import io.teamchallenge.woodCrafts.models.dto.ColorDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ColorMapper {

    public static Color convertColorDtoToColor(ColorDto colorDto) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(colorDto, Color.class);
    }

    public static ColorDto convertColorToColorDto(Color color) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(color, ColorDto.class);
    }
}
