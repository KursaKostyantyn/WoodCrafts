package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.models.dto.MaterialDto;
import io.teamchallenge.woodCrafts.services.api.MaterialService;
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
@RequestMapping()
@AllArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping("/materials")
    public ResponseEntity<Void> createMaterial(@Valid @RequestBody MaterialDto materialDto) {
        return materialService.createMaterial(materialDto);
    }

    @GetMapping("/materials/{id}")
    public ResponseEntity<MaterialDto> findMaterialById(@PathVariable Long id) {
        return materialService.findMaterialById(id);
    }

    @DeleteMapping("/materials/{id}")
    public ResponseEntity<Void> deleteMaterialById(@PathVariable Long id) {
        return materialService.deleteMaterialById(id);
    }

    @PatchMapping("/materials/{id}")
    public ResponseEntity<Void> updateMaterialById(@Valid @RequestBody MaterialDto materialDto,
                                                   @PathVariable Long id) {
        return materialService.updateMaterialById(materialDto, id);
    }

    @GetMapping("/materials")
    public ResponseEntity<List<MaterialDto>> findAllMaterials(@RequestParam(required = false, defaultValue = "false") boolean isDeleted) {
        return materialService.findAllMaterials(isDeleted);
    }
}
