package com.company.CompanyApp.service;

import com.company.CompanyApp.command.MaterialCommand;
import com.company.CompanyApp.domain.Construction;
import com.company.CompanyApp.domain.Material;
import com.company.CompanyApp.dto.MaterialDTO;
import com.company.CompanyApp.exception.notFound.ConstructionNotFoundException;
import com.company.CompanyApp.exception.notFound.TaskNotFoundException;
import com.company.CompanyApp.mapper.MaterialMapper;
import com.company.CompanyApp.repository.ConstructionRepository;
import com.company.CompanyApp.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.company.CompanyApp.constans.Constants.MATERIAL_NOT_FOUND_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    private final MaterialMapper materialMapper;

    private final ConstructionRepository constructionRepository;

    public Page<MaterialDTO> getAllMaterials(Pageable pageable) {
        return materialRepository.findAll(pageable).map(materialMapper::materialToMaterialDTO);
    }

    @Transactional
    public void saveMaterial(MaterialCommand materialCommand) {
        Material material = materialMapper.materialCommandToMaterial(materialCommand);

        materialRepository.save(material);

        Construction construction = material.getConstruction();
        if (construction == null) {
            throw new ConstructionNotFoundException("Material must be assigned to a construction.");
        }

        BigDecimal cost = BigDecimal.valueOf(material.getQuantity())
                .multiply(material.getPrice());

        BigDecimal updatedCost = construction.getCurrentCostOfRealization().add(cost);
        construction.setCurrentCostOfRealization(updatedCost);
        constructionRepository.save(construction);
    }


    public void deleteMaterial(Long id){
        Material material = findMaterialById(id);
        materialRepository.delete(material);
    }

    public Material findMaterialById(Long id){
        return materialRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(MATERIAL_NOT_FOUND_ERROR_MESSAGE));
    }

    public Page<MaterialDTO> findAllOrderedFromPeriod(LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        return materialRepository.findAllByOrderDateIsBetween(dateFrom, dateTo, pageable)
                .map(materialMapper::materialToMaterialDTO);
    }
}
