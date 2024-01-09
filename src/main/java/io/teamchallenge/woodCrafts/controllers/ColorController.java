package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.models.dto.ColorDto;
import io.teamchallenge.woodCrafts.services.api.ColorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/colors")
public class ColorController {

    private final ColorService colorService;

    @PostMapping("/createColor")
    public ResponseEntity<Void> createColor(@Valid @RequestBody ColorDto colorDto) {
        return colorService.createColor(colorDto);
    }

    @GetMapping("/findColorById")
    public ResponseEntity<ColorDto> findColorById(@RequestParam Long id) {
        return colorService.findColorById(id);
    }

    @DeleteMapping("/deleteColorById")
    public ResponseEntity<Void> deleteColorById(@RequestParam Long id) {
        return colorService.deleteColorById(id);
    }

    @PutMapping("/updateColorById")
    public ResponseEntity<Void> updateColorById(
            @Valid
            @RequestBody ColorDto colorDto,
            @RequestParam Long id
    ) {
        return colorService.updateColorById(colorDto, id);
    }

    @GetMapping("/getAllColors")
    public ResponseEntity<List<ColorDto>> getAllColors(@RequestParam(required = false, defaultValue = "false") boolean isDeleted) {
        return colorService.getAllColors(isDeleted);
    }
}
