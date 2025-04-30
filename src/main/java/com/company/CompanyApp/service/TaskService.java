package com.company.CompanyApp.service;

import com.company.CompanyApp.command.TaskCommand;
import com.company.CompanyApp.domain.Construction;
import com.company.CompanyApp.domain.Customer;
import com.company.CompanyApp.domain.Task;
import com.company.CompanyApp.dto.TaskDTO;
import com.company.CompanyApp.exception.notFound.ConstructionNotFoundException;
import com.company.CompanyApp.exception.notFound.TaskNotFoundException;
import com.company.CompanyApp.mapper.TaskMapper;
import com.company.CompanyApp.repository.ConstructionRepository;
import com.company.CompanyApp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static com.company.CompanyApp.util.Constants.TASK_NOT_FOUND_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final ConstructionRepository constructionRepository;

    private final CustomUserDetailsService customUserDetailsService;

    private final ConstructionService constructionService;

    private final TaskMapper taskMapper;

    public Page<TaskDTO> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable).map(taskMapper::taskToTaskDTO);
    }

    @Transactional
    public void saveTask(TaskCommand taskCommand) {
        Task task = taskMapper.taskCommandToTask(taskCommand);

        Construction construction = constructionRepository.findById(taskCommand.getConstructionId())
                .orElseThrow(() -> new ConstructionNotFoundException("Construction with ID " + taskCommand.getConstructionId() + " not found"));

        task.setConstruction(construction);
        taskRepository.save(task);

        BigDecimal totalCost = getBigDecimal(construction, task);

        construction.setCurrentCostOfRealization(totalCost);
        constructionRepository.save(construction);

        constructionService.updatePercentOfRealization(construction.getId());
    }

    private static @NotNull BigDecimal getBigDecimal(Construction construction, Task task) {
        BigDecimal totalCost = construction.getCurrentCostOfRealization();

        if (task.isDone()) {
            BigDecimal plannedValue = task.getPlannedValue() != null ? task.getPlannedValue() : BigDecimal.ZERO;
            totalCost = totalCost.add(plannedValue);

            BigDecimal laborCost = BigDecimal.ZERO;
            for (Customer customer : task.getCustomers()) {
                BigDecimal hourlyRate = customer.getHourlyRate() != null ? customer.getHourlyRate() : BigDecimal.ZERO;
                BigDecimal days = BigDecimal.valueOf(task.getDurationDay());
                BigDecimal cost = hourlyRate.multiply(days).multiply(BigDecimal.valueOf(8));
                laborCost = laborCost.add(cost);
            }
            totalCost = totalCost.add(laborCost);
        }

        return totalCost;
    }

    public void deleteTask(Long id){
        Task task = findTaskById(id);
        taskRepository.delete(task);
    }

    public Task findTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND_ERROR_MESSAGE));
    }

    public Page<TaskDTO> findAllTasksByCustomerAndDone(boolean done, Pageable pageable) {
        Page<Task> tasks = taskRepository.findTasksByCustomerUsernameAndIsDone(
                customUserDetailsService.getCurrentUsername(), true, pageable);
        return tasks.map(taskMapper::taskToTaskDTO);
    }
}
