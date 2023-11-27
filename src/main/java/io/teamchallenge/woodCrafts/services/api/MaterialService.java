package io.teamchallenge.woodCrafts.services.api;

import io.teamchallenge.woodCrafts.models.dto.MaterialDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MaterialService {

    ResponseEntity<Void> saveMaterial(MaterialDto materialDto);

    ResponseEntity<MaterialDto> findMaterialById(Long id);

    ResponseEntity<Void> deleteMaterialById(Long id);

    ResponseEntity<Void> updateMaterialById(MaterialDto materialDto, Long id);

    ResponseEntity<List<MaterialDto>> findAllMaterials();
}
