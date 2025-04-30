package com.company.CompanyApp.service;

import com.company.CompanyApp.command.ConstructionCommand;
import com.company.CompanyApp.domain.Construction;
import com.company.CompanyApp.domain.Task;
import com.company.CompanyApp.dto.ConstructionDTO;
import com.company.CompanyApp.dto.CustomerDTO;
import com.company.CompanyApp.dto.TaskDTO;
import com.company.CompanyApp.exception.notFound.ConstructionNotFoundException;
import com.company.CompanyApp.exception.notFound.TaskNotFoundException;
import com.company.CompanyApp.mapper.ConstructionMapper;
import com.company.CompanyApp.mapper.CustomerMapper;
import com.company.CompanyApp.mapper.TaskMapper;
import com.company.CompanyApp.repository.ConstructionRepository;
import com.company.CompanyApp.repository.CustomerRepository;
import com.company.CompanyApp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

import static com.company.CompanyApp.constans.Constants.CONSTRUCTION_NOT_FOUND_ERROR_MESSAGE;
import static com.company.CompanyApp.constans.Constants.TASK_NOT_FOUND_ERROR_MESSAGE;


@Service
@RequiredArgsConstructor
public class ConstructionService {

    private final ConstructionRepository constructionRepository;

    private final ConstructionMapper constructionMapper;

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;


    public void saveConstruction(ConstructionCommand constructionCommand) {
        Construction construction = constructionMapper.constructionCommandToConstruction(constructionCommand);
        constructionRepository.save(construction);
    }

    public void deleteConstruction(Long id) {
        Construction construction = findConstructionById(id);
        constructionRepository.delete(construction);
    }

    public Page<ConstructionDTO> getAllConstruction(Pageable pageable) {
        return constructionRepository.findAll(pageable).map(constructionMapper::constructionToConstructionDTO);
    }

    public Construction findConstructionById(Long id) {
        return constructionRepository.findById(id)
                .orElseThrow(() -> new ConstructionNotFoundException(CONSTRUCTION_NOT_FOUND_ERROR_MESSAGE));
    }

    public Page<CustomerDTO> findAllCustomersFromConstruction(Long id, Pageable pageable) {
        return customerRepository.findAllByConstructionId(id, pageable)
                .map(customerMapper::customerToCustomerDTO);
    }

    public Page<TaskDTO> findAllTasksFromConstruction(Long id, Pageable pageable) {
        return taskRepository.findAllByConstructionId(id, pageable)
                .map(taskMapper::taskToTaskDTO);
    }

    public TaskDTO findTheLongestTaskFromConstruction(Long id){
        return findConstructionById(id).getTasks().stream()
                .max(Comparator.comparing(Task::getDurationDay))
                .map(taskMapper::taskToTaskDTO)
                .orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND_ERROR_MESSAGE));
    }

    public Page<ConstructionDTO> findAllConstructionByCustomer(Long customerId, Pageable pageable) {
        return constructionRepository.findAllByCustomers_Id(customerId, pageable)
                .map(constructionMapper::constructionToConstructionDTO);
    }

    public BigDecimal getCurrentCostOfConstruction(Long id) {
        Construction construction = constructionRepository.findById(id)
                .orElseThrow(() -> new ConstructionNotFoundException(CONSTRUCTION_NOT_FOUND_ERROR_MESSAGE));
        return construction.getCurrentCostOfRealization();
    }

    public double updatePercentOfRealization(Long constructionId) {
        Construction construction = findConstructionById(constructionId);
        BigDecimal plannedTotal = construction.getPlannedPrizeOfRealization();
        if (plannedTotal == null || plannedTotal.compareTo(BigDecimal.ZERO) == 0) {
            construction.setPercentOfRealization(0.0);
        }

        BigDecimal completedTasksValue = construction.getTasks().stream()
                .filter(Task::isDone)
                .map(task -> task.getPlannedValue() != null ? task.getPlannedValue() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal materialsCost = construction.getMaterials().stream()
                .map(m -> {
                    BigDecimal price = m.getPrice() != null ? m.getPrice() : BigDecimal.ZERO;
                    BigDecimal quantity = BigDecimal.valueOf(m.getQuantity());
                    return price.multiply(quantity);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal actualRealized = completedTasksValue.add(materialsCost);

        double percent = actualRealized
                .divide(plannedTotal, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();

        construction.setPercentOfRealization(percent);
        constructionRepository.save(construction);

        return construction.getPercentOfRealization();
    }


}
