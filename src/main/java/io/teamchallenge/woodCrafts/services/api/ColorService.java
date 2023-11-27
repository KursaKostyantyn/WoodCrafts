package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.ColorDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ColorService {

    ResponseEntity<Void> saveColor (ColorDto colorDto);

    ResponseEntity<ColorDto> findColorById(Long id);

    ResponseEntity<Void> deleteColorById(Long id);

    ResponseEntity<Void> updateColorById (ColorDto colorDto, Long id);

    ResponseEntity<List<ColorDto>> getAllColors();

}
