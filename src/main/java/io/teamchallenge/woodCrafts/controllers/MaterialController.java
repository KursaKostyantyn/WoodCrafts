package io.teamchallenge.woodCrafts.controllers;

import io.teamchallenge.woodCrafts.models.dto.MaterialDto;
import io.teamchallenge.woodCrafts.services.api.MaterialService;
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
@RequestMapping("/materials")
@AllArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping("/createMaterial")
    public ResponseEntity<Void> createMaterial(@RequestBody MaterialDto materialDto) {
        return materialService.createMaterial(materialDto);
    }

    @GetMapping("/findMaterialById")
    public ResponseEntity<MaterialDto> findMaterialById(@RequestParam Long id) {
        return materialService.findMaterialById(id);
    }

    @DeleteMapping("/deleteMaterialById")
    public ResponseEntity<Void> deleteMaterialById(Long id){
        return materialService.deleteMaterialById(id);
    }

    @PutMapping("/updateMaterialById")
    public ResponseEntity<Void> updateMaterialById(@RequestBody MaterialDto materialDto, @RequestParam Long id){
        return materialService.updateMaterialById(materialDto,id);
    }

    @GetMapping("/getAllMaterials")
    public ResponseEntity<List<MaterialDto>> findAllMaterials(){
        return materialService.findAllMaterials();
    }
}
