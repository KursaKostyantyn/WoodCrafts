package io.teamchallenge.woodCrafts.services.impl;

import io.teamchallenge.woodCrafts.exception.DuplicateException;
import io.teamchallenge.woodCrafts.exception.EntityNotFoundException;
import io.teamchallenge.woodCrafts.mapper.MaterialMapper;
import io.teamchallenge.woodCrafts.models.Material;
import io.teamchallenge.woodCrafts.models.dto.MaterialDto;
import io.teamchallenge.woodCrafts.repository.MaterialRepository;
import io.teamchallenge.woodCrafts.services.api.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    @Transactional
    @Override
    public ResponseEntity<Void> save(MaterialDto materialDto) {
        Optional<Material> existingMaterial = materialRepository.findByName(materialDto.getName());
        if (existingMaterial.isPresent()) {
            throw new DuplicateException(String.format("Material with name='%s' already exists", materialDto.getName()));
        }
        Material material = MaterialMapper.convertMaterialDtoToMaterial(materialDto);
        materialRepository.save(material);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<MaterialDto> findMaterialById(Long id) {
        Material material = materialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Material with id='%s' not found", id)));

        return ResponseEntity.status(HttpStatus.OK).body(MaterialMapper.convertMaterialToMaterialDto(material));
    }

    @Override
    public ResponseEntity<Void> deleteMaterialById(Long id) {
        Material material = materialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Material with id='%s' not found", id)));
        material.setDeleted(true);
        materialRepository.save(material);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> updateMaterialById(MaterialDto materialDto, Long id) {
        Material material = materialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Material with id='%s' not found", id)));
        Optional<Material> existingMaterial = materialRepository.findByName(materialDto.getName());
        if (existingMaterial.isPresent() && existingMaterial.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Material with name='%s' already exists", materialDto.getName()));
        }
        material.setName(materialDto.getName());
        material.setDeleted(materialDto.isDeleted());
        materialRepository.save(material);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<List<MaterialDto>> findAllMaterials(boolean isDeleted) {
        List<MaterialDto> materials = materialRepository.findAllByDeleted(isDeleted)
                .stream()
                .map(MaterialMapper::convertMaterialToMaterialDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(materials);
    }
}
