package com.company.CompanyApp.mapper;

import com.company.CompanyApp.command.ConstructionCommand;
import com.company.CompanyApp.domain.Construction;
import com.company.CompanyApp.dto.ConstructionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConstructionMapper {

    ConstructionDTO constructionToConstructionDTO(Construction construction);

    Construction constructionCommandToConstruction(ConstructionCommand constructionCommand);
}
