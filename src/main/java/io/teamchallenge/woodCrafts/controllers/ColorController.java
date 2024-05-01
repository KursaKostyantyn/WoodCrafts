package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.models.dto.ColorDto;
import io.teamchallenge.woodCrafts.services.api.ColorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping()
public class ColorController {

    private final ColorService colorService;

    @PostMapping("colors")
    public ResponseEntity<Void> createColor(@Valid @RequestBody ColorDto colorDto) {
        return colorService.save(colorDto);
    }

    @GetMapping("colors/{id}")
    public ResponseEntity<ColorDto> findColorById(@PathVariable Long id) {
        return colorService.findColorById(id);
    }

    @DeleteMapping("colors/{id}")
    public ResponseEntity<Void> deleteColorById(@PathVariable Long id) {
        return colorService.deleteColorById(id);
    }

    @PatchMapping("colors/{id}")
    public ResponseEntity<Void> updateColorById(
            @Valid
            @RequestBody ColorDto colorDto,
            @PathVariable Long id
    ) {
        return colorService.updateColorById(colorDto, id);
    }

    @GetMapping("colors")
    public ResponseEntity<List<ColorDto>> getAllColors(@RequestParam(required = false, defaultValue = "false") boolean isDeleted) {
        return colorService.getAllColors(isDeleted);
    }
}
