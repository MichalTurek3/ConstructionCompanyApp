package com.company.CompanyApp.mapper;

import com.company.CompanyApp.command.MaterialCommand;
import com.company.CompanyApp.domain.Material;
import com.company.CompanyApp.dto.MaterialDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    MaterialDTO materialToMaterialDTO(Material material);

    Material materialCommandToMaterial(MaterialCommand materialCommand);
}
