package com.company.CompanyApp.mapper;

import com.company.CompanyApp.command.CustomerCommand;
import com.company.CompanyApp.domain.Customer;
import com.company.CompanyApp.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerCommandToCustomer(CustomerCommand customerCommand);
}
