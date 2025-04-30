package com.company.CompanyApp.service;

import com.company.CompanyApp.domain.Construction;
import com.company.CompanyApp.domain.Customer;
import com.company.CompanyApp.domain.Material;
import com.company.CompanyApp.domain.Task;
import com.company.CompanyApp.exception.invalid.InvalidFieldNameException;
import com.company.CompanyApp.exception.notFound.ConstructionNotFoundException;
import com.company.CompanyApp.exception.notFound.CustomerNotFoundException;
import com.company.CompanyApp.exception.notFound.MaterialNotFoundException;
import com.company.CompanyApp.exception.notFound.TaskNotFoundException;
import com.company.CompanyApp.repository.ConstructionRepository;
import com.company.CompanyApp.repository.CustomerRepository;
import com.company.CompanyApp.repository.MaterialRepository;
import com.company.CompanyApp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.company.CompanyApp.constans.Constants.*;

@Service
@RequiredArgsConstructor
public class EditService {

    private final CustomerRepository customerRepository;

    private final MaterialRepository materialRepository;

    private final ConstructionRepository constructionRepository;

    private final TaskRepository taskRepository;

    public void verifyFieldName(String fieldName, String entityType) {
        switch (entityType) {
            case CUSTOMER -> verifyCustomerFieldName(fieldName);
            case MATERIAL -> verifyMaterialFieldName(fieldName);
            case CONSTRUCTION -> verifyConstructionFieldName(fieldName);
            case TASK -> verifyTaskFieldName(fieldName);
            default -> throw new InvalidFieldNameException(INVALID_ENTITY_TYPE_ERROR_MESSAGE);
        }
    }

    public String getOldValue(Long entityId, String entityType, String fieldName) {
        return switch (entityType) {
            case CUSTOMER -> getCustomerOldValue(entityId, fieldName);
            case CONSTRUCTION -> getConstructionOldValue(entityId, fieldName);
            case MATERIAL -> getMaterialOldValue(entityId, fieldName);
            case TASK -> getTaskOldValue(entityId, fieldName);
            default -> throw new InvalidFieldNameException(INVALID_ENTITY_TYPE_ERROR_MESSAGE);
        };
    }

    private void verifyConstructionFieldName(String fieldName) {
        if (!CONSTRUCTION_NAME.equals(fieldName) && !LOCATION.equals(fieldName) && !PLANNED_PRICE_OF_REALIZATION.equals(fieldName)
                && !PERCENT_OF_REALIZATION.equals(fieldName) && !CURRENT_COST_OF_REALIZATION.equals(fieldName)
        ) {
            throw new InvalidFieldNameException(CONSTRUCTION_FIELD_ERROR_MESSAGE);
        }
    }

    private void verifyMaterialFieldName(String fieldName) {
        if (!MATERIAL_NAME.equals(fieldName) && !ORDER_DATE.equals(fieldName) && !PRICE.equals(fieldName)
                && !QUANTITY.equals(fieldName)) {
            throw new InvalidFieldNameException(MATERIAL_FIELD_ERROR_MESSAGE);
        }
    }

    private void verifyCustomerFieldName(String fieldName) {
        if (!USERNAME.equals(fieldName) && !LAST_NAME.equals(fieldName)
                && !PASSWORD.equals(fieldName) && !FIRST_NAME.equals(fieldName))  {
            throw new InvalidFieldNameException(CUSTOMER_FIELD_ERROR_MESSAGE);
        }
    }

    private void verifyTaskFieldName(String fieldName){
        if (!TASK_NAME.equals(fieldName) && !DURATION.equals(fieldName)
                && !IS_DONE.equals(fieldName) && !PLANNED_VALUE.equals(fieldName)){
            throw new InvalidFieldNameException(TASK_FIELD_ERROR_MESSAGE);
        }
    }

    private String getCustomerOldValue(Long entityId, String fieldName) {
        Customer customer = customerRepository.findById(entityId)
                .orElseThrow(CustomerNotFoundException::new);

        return switch (fieldName) {
            case USERNAME -> customer.getUsername();
            case PASSWORD -> customer.getPassword();
            case FIRST_NAME -> customer.getFirstName();
            case LAST_NAME -> customer.getLastName();
            default -> throw new InvalidFieldNameException(CUSTOMER_FIELD_ERROR_MESSAGE);
        };
    }

    private String getMaterialOldValue(Long entityId, String fieldName) {
        Material material = materialRepository.findById(entityId)
                .orElseThrow(MaterialNotFoundException::new);

        return switch (fieldName) {
            case MATERIAL_NAME -> material.getName();
            case ORDER_DATE -> String.valueOf(material.getOrderDate());
            case PRICE -> String.valueOf(material.getPrice());
            case QUANTITY -> String.valueOf(material.getQuantity());
            default -> throw new InvalidFieldNameException(MATERIAL_FIELD_ERROR_MESSAGE);
        };
    }

    private String getConstructionOldValue(Long entityId, String fieldName) {
        Construction construction = constructionRepository.findById(entityId)
                .orElseThrow(ConstructionNotFoundException::new);

        return switch (fieldName) {
            case CONSTRUCTION_NAME -> construction.getName();
            case PLANNED_PRICE_OF_REALIZATION -> String.valueOf(construction.getPlannedPrizeOfRealization());
            case PERCENT_OF_REALIZATION -> String.valueOf(construction.getPercentOfRealization());
            case CURRENT_COST_OF_REALIZATION -> String.valueOf(construction.getCurrentCostOfRealization());
            case LOCATION -> String.valueOf(construction.getLocation());
            default -> throw new InvalidFieldNameException(CONSTRUCTION_FIELD_ERROR_MESSAGE);
        };
    }

    private String getTaskOldValue(Long entityId, String fieldName){
        Task task = taskRepository.findTaskById(entityId)
                .orElseThrow(TaskNotFoundException::new);

        return switch (fieldName){
            case TASK_NAME -> task.getName();
            case DURATION -> String.valueOf(task.getDurationDay());
            case IS_DONE -> String.valueOf(task.isDone());
            case PLANNED_VALUE -> String.valueOf(task.getPlannedValue());
            default -> throw new InvalidFieldNameException(TASK_FIELD_ERROR_MESSAGE);
        };
    }
}