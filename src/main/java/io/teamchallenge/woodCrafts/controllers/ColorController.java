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

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/colors")
public class ColorController {

    private final ColorService colorService;

    @PostMapping("/create")
    public ResponseEntity<Void> saveColor(@RequestBody ColorDto colorDto) {
        return colorService.saveColor(colorDto);
    }

    @GetMapping("/findById")
    public ResponseEntity<ColorDto> findColorById(@RequestParam Long id) {
        return colorService.findColorById(id);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteColorById(@RequestParam Long id) {
        return colorService.deleteColorById(id);
    }

    @PutMapping("/updateById")
    public ResponseEntity<Void> updateColorById(@RequestBody ColorDto colorDto, @RequestParam Long id) {
        return colorService.updateColorById(colorDto, id);
    }

    @GetMapping("/getAllColors")
    public ResponseEntity<List<ColorDto>> getAllColors() {
        return colorService.getAllColors();
    }
}
