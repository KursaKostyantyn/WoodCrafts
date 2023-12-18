package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.mapper.ColorMapper;
import io.teamchallenge.woodCrafts.models.Color;
import io.teamchallenge.woodCrafts.models.dto.ColorDto;
import io.teamchallenge.woodCrafts.repository.ColorRepository;
import io.teamchallenge.woodCrafts.services.api.ColorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public ResponseEntity<Void> createColor(ColorDto colorDto) {
        Color color = ColorMapper.convertColorDtoToColor(colorDto);
        colorRepository.save(color);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<ColorDto> findColorById(Long id) {
        Color color = colorRepository.findById(id).orElse(null);
        if (color == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(ColorMapper.convertColorToColorDto(color));
    }

    @Override
    public ResponseEntity<Void> deleteColorById(Long id) {
        Color color = colorRepository.findById(id).orElse(null);
        if (color == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        color.setDeleted(true);
        colorRepository.save(color);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateColorById(ColorDto colorDto, Long id) {
        Color color = colorRepository.findById(id).orElse(null);
        if (color == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        color.setName(colorDto.getName());
        color.setDeleted(colorDto.isDeleted());
        colorRepository.save(color);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<List<ColorDto>> getAllColors(boolean isDeleted) {
        List<Color> colors = colorRepository.findAllByDeleted(isDeleted);
        List<ColorDto> colorDtos = colors.stream().map(ColorMapper::convertColorToColorDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(colorDtos);
    }
}
