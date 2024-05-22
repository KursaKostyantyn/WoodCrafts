package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.ColorDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ColorService {

    ResponseEntity<Void> save(ColorDto colorDto);

    ResponseEntity<ColorDto> findColorById(Long colorId);

    ResponseEntity<Void> deleteColorById(Long colorId);

    ResponseEntity<Void> updateColorById (ColorDto colorDto, Long id);

    ResponseEntity<List<ColorDto>> getAllColors(boolean isDeleted);

}
