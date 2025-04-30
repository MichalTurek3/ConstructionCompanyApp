package com.company.CompanyApp.mapper;

import com.company.CompanyApp.command.AdminCommand;
import com.company.CompanyApp.domain.Admin;
import com.company.CompanyApp.dto.AdminDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminDTO adminToAdminDTO(Admin admin);

    AdminCommand adminCommandToAdmin(AdminCommand adminCommand);

}