package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.mapper.MaterialMapper;
import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.dto.MaterialDto;
import io.teamchallenge.woodCrafts.repository.MaterialRepository;
import io.teamchallenge.woodCrafts.services.api.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    @Override
    public ResponseEntity<Void> saveMaterial(MaterialDto materialDto) {
        Material material = MaterialMapper.convertMaterialDtoToMaterial(materialDto);
        materialRepository.save(material);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<MaterialDto> findMaterialById(Long id) {
        Material material = materialRepository.findById(id).orElse(null);
        if (material == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(MaterialMapper.convertMaterialToMaterialDto(material));
    }

    @Override
    public ResponseEntity<Void> deleteMaterialById(Long id) {
        Material material = materialRepository.findById(id).orElse(null);
        if (material == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateMaterialById(MaterialDto materialDto, Long id) {
        Material material = materialRepository.findById(id).orElse(null);
        if (material == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        material.setName(materialDto.getName());
        materialRepository.save(material);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<List<MaterialDto>> findAllMaterials() {
        List<MaterialDto> materials = materialRepository.findAll().stream().map(MaterialMapper::convertMaterialToMaterialDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(materials);
    }
}
