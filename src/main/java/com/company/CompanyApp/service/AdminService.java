package com.company.CompanyApp.service;

import com.company.CompanyApp.command.EditCommand;
import com.company.CompanyApp.domain.Construction;
import com.company.CompanyApp.domain.Customer;
import com.company.CompanyApp.domain.Material;
import com.company.CompanyApp.domain.Task;
import com.company.CompanyApp.dto.ConstructionDTO;
import com.company.CompanyApp.dto.CustomerDTO;
import com.company.CompanyApp.dto.MaterialDTO;
import com.company.CompanyApp.dto.TaskDTO;
import com.company.CompanyApp.exception.invalid.InvalidCredentialsException;
import com.company.CompanyApp.exception.invalid.InvalidFieldNameException;
import com.company.CompanyApp.exception.notFound.ConstructionNotFoundException;
import com.company.CompanyApp.exception.notFound.CustomerNotFoundException;
import com.company.CompanyApp.exception.notFound.MaterialNotFoundException;
import com.company.CompanyApp.exception.notFound.TaskNotFoundException;
import com.company.CompanyApp.mapper.ConstructionMapper;
import com.company.CompanyApp.mapper.CustomerMapper;
import com.company.CompanyApp.mapper.MaterialMapper;
import com.company.CompanyApp.mapper.TaskMapper;
import com.company.CompanyApp.model.ActionType;
import com.company.CompanyApp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.company.CompanyApp.util.Constants.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ActionService actionService;

    private final EditService editService;

    private final AdminRepository adminRepository;

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomerRepository customerRepository;

    private final MaterialRepository materialRepository;

    private final ConstructionRepository constructionRepository;

    private final TaskRepository taskRepository;

    private final CustomerMapper customerMapper;

    private final MaterialMapper materialMapper;

    private final ConstructionMapper constructionMapper;

    private final TaskMapper taskMapper;

    public void verifyAdminAccessAndSaveAction(ActionType actionType) {
        if (!adminRepository.existsAdminByUsername(customUserDetailsService.getCurrentUsername())) {
            throw new InvalidCredentialsException(ADMIN_NOT_AUTHORIZED_ERROR_MESSAGE);
        }

        actionService.saveAction(actionType);
    }

    public void verifyAdminAccessAndSaveAction(ActionType actionType, Long entityId, String entityType,
                                               String fieldName, String newValue) {
        if (!adminRepository.existsAdminByUsername(customUserDetailsService.getCurrentUsername())) {
            throw new InvalidCredentialsException(ADMIN_NOT_AUTHORIZED_ERROR_MESSAGE);
        }


        editService.verifyFieldName(fieldName, entityType);
        actionService.saveAction(actionType, entityId, entityType, fieldName,
                editService.getOldValue(entityId, entityType, fieldName), newValue);
    }

    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerMapper::customerToCustomerDTO);
    }

    public Page<TaskDTO> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::taskToTaskDTO);
    }

    public CustomerDTO updateCustomer(Long customerId, EditCommand customerEdit) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        switch (customerEdit.getFieldName()) {
            case USERNAME -> customer.setUsername(customerEdit.getNewValue());
            case PASSWORD -> customer.setPassword(customerEdit.getNewValue());
            case FIRST_NAME -> customer.setFirstName(customerEdit.getNewValue());
            case LAST_NAME -> customer.setLastName(customerEdit.getNewValue());
            default -> throw new InvalidFieldNameException(CUSTOMER_FIELD_ERROR_MESSAGE);
        }

        return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
    }

    public MaterialDTO updateMaterial(Long materialId, EditCommand editCommand) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(MaterialNotFoundException::new);

        switch (editCommand.getFieldName()) {
            case MATERIAL_NAME -> material.setName(editCommand.getNewValue());
            case ORDER_DATE -> material.setOrderDate(LocalDate.parse(editCommand.getNewValue()));
            case PRICE -> material.setPrice(BigDecimal.valueOf(Double.parseDouble(editCommand.getNewValue())));
            case QUANTITY -> material.setQuantity(Integer.parseInt(editCommand.getNewValue()));
            default -> throw new InvalidFieldNameException(MATERIAL_FIELD_ERROR_MESSAGE);
        }

        return materialMapper.materialToMaterialDTO(materialRepository.save(material));
    }

    public ConstructionDTO updateConstruction(Long constructionId, EditCommand editCommand) {
        Construction construction = constructionRepository.findById(constructionId)
                .orElseThrow(ConstructionNotFoundException::new);

        switch (editCommand.getFieldName()) {
            case CONSTRUCTION_NAME -> construction.setName(editCommand.getNewValue());
            case PERCENT_OF_REALIZATION -> construction.setPercentOfRealization(Double.parseDouble(editCommand.getNewValue()));
            case PLANNED_PRICE_OF_REALIZATION -> construction.setPlannedPrizeOfRealization(new BigDecimal(editCommand.getNewValue()));
            case CURRENT_COST_OF_REALIZATION -> construction.setCurrentCostOfRealization(new BigDecimal(editCommand.getNewValue()));
            case LOCATION -> construction.setLocation((editCommand.getNewValue()));
            default -> throw new InvalidFieldNameException(CONSTRUCTION_FIELD_ERROR_MESSAGE);
        }

        return constructionMapper.constructionToConstructionDTO(constructionRepository.save(construction));
    }

    public TaskDTO updateTask(Long taskId, EditCommand editCommand) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        switch (editCommand.getFieldName()) {
            case TASK_NAME -> task.setName(editCommand.getNewValue());
            case IS_DONE -> task.setDone(Boolean.parseBoolean(editCommand.getNewValue()));
            case DURATION -> task.setDurationDay(Integer.parseInt(editCommand.getNewValue()));
            default -> throw new InvalidFieldNameException(TASK_FIELD_ERROR_MESSAGE);
        }

        return taskMapper.taskToTaskDTO(taskRepository.save(task));
    }



    public void lockCustomerAccount(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        customer.setAccountNonLocked(false);
        customerRepository.save(customer);
    }

    public void unlockCustomerAccount(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        customer.setAccountNonLocked(true);
        customerRepository.save(customer);
    }

    public void enableCustomerAccount(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        customer.setAccountEnabled(true);
        customerRepository.save(customer);
    }

    public void disableCustomerAccount(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        customer.setAccountEnabled(false);
        customerRepository.save(customer);
    }
}