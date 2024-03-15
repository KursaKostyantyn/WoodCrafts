package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.exception.DuplicateException;
import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public ResponseEntity<Void> createColor(ColorDto colorDto) {
        Optional<Color> existingColor = colorRepository.findByName(colorDto.getName());
        if (existingColor.isPresent()) {
            throw new DuplicateException(String.format("Category with name='%s' already exists", colorDto.getName()));
        }
        Color color = ColorMapper.convertColorDtoToColor(colorDto);
        colorRepository.save(color);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<ColorDto> findColorById(Long colorId) {
        Color color = colorRepository.findById(colorId).orElse(null);
        if (color == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(ColorMapper.convertColorToColorDto(color));
    }

    @Override
    public ResponseEntity<Void> deleteColorById(Long colorId) {
        Color color = colorRepository.findById(colorId).orElseThrow(() -> new EntityNotFoundException(String.format("Color with id='%s' not found", colorId)));
        color.setDeleted(true);
        colorRepository.save(color);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateColorById(ColorDto colorDto, Long id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Color with id='%s' not found", id)));
        Optional<Color> existingColor = colorRepository.findByName(colorDto.getName());
        if (existingColor.isPresent() && existingColor.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Category with name='%s' already exists", colorDto.getName()));
        }
        color.setName(colorDto.getName());
        color.setDeleted(colorDto.isDeleted());
        colorRepository.save(color);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<List<ColorDto>> getAllColors(boolean isDeleted) {
        List<Color> colors = colorRepository.findAllByDeleted(isDeleted);
        List<ColorDto> colorDtos = colors.stream()
                .map(ColorMapper::convertColorToColorDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(colorDtos);
    }
}
