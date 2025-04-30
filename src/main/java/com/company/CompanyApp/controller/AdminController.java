package com.company.CompanyApp.controller;

import com.company.CompanyApp.command.ConstructionCommand;
import com.company.CompanyApp.command.EditCommand;
import com.company.CompanyApp.command.MaterialCommand;
import com.company.CompanyApp.command.TaskCommand;
import com.company.CompanyApp.domain.Material;
import com.company.CompanyApp.dto.*;
import com.company.CompanyApp.model.ActionType;
import com.company.CompanyApp.repository.ConstructionRepository;
import com.company.CompanyApp.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.math.BigDecimal;

import static com.company.CompanyApp.constans.Constants.*;


@RequiredArgsConstructor
@RestController
@Tag(name = "Admin controller", description = "Manage customers, tasks, material, construction")
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    private final ConstructionService constructionService;

    private final ActionService actionService;

    private final MaterialService materialService;

    private final TaskService taskService;

    @Operation(summary = "Update customer")
    @ApiResponse(responseCode = "200", description = "Successful update of customer details",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class)))
    @PutMapping("/customers/update/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long customerId,
                                                      @RequestBody EditCommand editCommand) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.EDIT, customerId, CUSTOMER,
                editCommand.getFieldName(), editCommand.getNewValue());
        return new ResponseEntity<>(adminService.updateCustomer(customerId, editCommand), HttpStatus.OK);
    }

    @Operation(summary = "Update material")
    @ApiResponse(responseCode = "200", description = "Successful update of material details",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Material.class)))
    @PutMapping("/material/update/{materialId}")
    public ResponseEntity<MaterialDTO> updateMaterial(@PathVariable Long materialId,
                                                 @RequestBody EditCommand editCommand) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.EDIT, materialId, MATERIAL,
                editCommand.getFieldName(), editCommand.getNewValue());
        return new ResponseEntity<>(adminService.updateMaterial(materialId, editCommand),HttpStatus.OK);
    }

    @Operation(summary = "Update construction")
    @ApiResponse(responseCode = "200", description = "Successful update of construction details",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ConstructionDTO.class)))
    @PutMapping("/construction/update/{constructionId}")
    public ResponseEntity<ConstructionDTO> updateConstruction(@PathVariable Long constructionId,
                                                    @RequestBody @Valid EditCommand editCommand) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.EDIT, constructionId, CONSTRUCTION,
                editCommand.getFieldName(), editCommand.getNewValue());
        return new ResponseEntity<>(adminService.updateConstruction(constructionId, editCommand), HttpStatus.OK);
    }

    @Operation(summary = "Update task")
    @ApiResponse(responseCode = "200", description = "Successful update of task details",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDTO.class)))
    @PutMapping("/task/update/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId,
                                                       @RequestBody @Valid EditCommand editCommand) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.EDIT, taskId, TASK,
                editCommand.getFieldName(), editCommand.getNewValue());
        return new ResponseEntity<>(adminService.updateTask(taskId, editCommand), HttpStatus.OK);
    }

    @Operation(summary = "enable customer account")
    @ApiResponse(responseCode = "200", description = "Successful enabling of customer account")
    @PutMapping("/customers/enable-account/{customerId}")
    public ResponseEntity<Void> enableCustomerAccount(@PathVariable Long customerId) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.ENABLE_CUSTOMER_ACCOUNT);
        adminService.enableCustomerAccount(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Disable customer account")
    @ApiResponse(responseCode = "200", description = "Successful disabling of customer account")
    @PutMapping("/customers/disable-account/{customerId}")
    public ResponseEntity<Void> disableCustomerAccount(@PathVariable Long customerId) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.DISABLE_CUSTOMER_ACCOUNT);
        adminService.disableCustomerAccount(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Lock customer account")
    @ApiResponse(responseCode = "200", description = "Successful locking of customer account")
    @PutMapping("/customers/lock-account/{customerId}")
    public ResponseEntity<Void> lockCustomerAccount(@PathVariable Long customerId) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.LOCK_CUSTOMER_ACCOUNT);
        adminService.lockCustomerAccount(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Unlock customer account")
    @ApiResponse(responseCode = "200", description = "Successful unlocking of customer account")
    @PutMapping("/customers/unlock-account/{customerId}")
    public ResponseEntity<Void> unlockCustomerAccount(@PathVariable Long customerId) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.UNLOCK_CUSTOMER_ACCOUNT);
        adminService.unlockCustomerAccount(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all actions")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all actions",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/action/all")
    public ResponseEntity<Page<ActionDTO>> getAllActions(
            @PageableDefault(size = 15, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.RETRIEVING_ALL_ACTIONS);
        return new ResponseEntity<>(actionService.getActionsForAdmin(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get all customers")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all customers",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("customers/all")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(
            @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.RETRIEVING_ALL_CUSTOMERS);
        return new ResponseEntity<>(adminService.getAllCustomers(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get all materials")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all materials",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping(path = "/materials/all")
    public ResponseEntity<Page<MaterialDTO>> getAllMaterials(
            @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.RETRIEVING_ALL_MATERIALS);
        return new ResponseEntity<>(materialService.getAllMaterials(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get all constructions")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all constructions",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/construction/all")
    public ResponseEntity<Page<ConstructionDTO>> getAllConstruction(
            @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.RETRIEVING_ALL_CONSTRUCTIONS);
        return new ResponseEntity<>(constructionService.getAllConstruction(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get all tasks")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all tasks",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/task/all")
    public ResponseEntity<Page<TaskDTO>> getAllTask(
            @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.RETRIEVING_ALL_TASKS);
        return new ResponseEntity<>(taskService.getAllTasks(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Saving one material")
    @PostMapping("/material/save")
    public ResponseEntity<Void> addMaterial(@RequestBody @Valid MaterialCommand carCommand) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.ADDING_MATERIAL);
        materialService.saveMaterial(carCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Saving construction")
    @PostMapping("/construction/save")
    public ResponseEntity<Void> saveConstruction(@RequestBody @Valid ConstructionCommand constructionCommand) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.ADDING_CONSTRUCTION);
        constructionService.saveConstruction(constructionCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Saving task")
    @PostMapping("/task/save")
    public ResponseEntity<Void> saveTask(@RequestBody @Valid TaskCommand taskCommand) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.ADDING_TASK);
        taskService.saveTask(taskCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete construction")
    @ApiResponse(responseCode = "200", description = "Successful deletion of construction")
    @DeleteMapping("/construction/{constructionId}/delete")
    public ResponseEntity<Void> deleteConstruction(@PathVariable Long constructionId) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.DELETING_TASK);
        constructionService.deleteConstruction(constructionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete material")
    @ApiResponse(responseCode = "200", description = "Successful deletion of material")
    @DeleteMapping("/material/{materialId}/delete")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long materialId) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.DELETING_MATERIAL);
        materialService.deleteMaterial(materialId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete task")
    @ApiResponse(responseCode = "200", description = "Successful deletion of task")
    @DeleteMapping("/task/{taskId}/delete")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.DELETING_TASK);
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all customer from construction")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all customer from construction",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDTO.class)))
    @GetMapping("/construction/{constructionId}/customers")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomersFromConstruction(@PathVariable Long constructionId,
                                                              @PageableDefault(size = 15, sort = "id",
                                                                      direction = Sort.Direction.ASC) Pageable pageable) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.RETRIEVING_ALL_CUSTOMERS_FROM_CONSTRUCTION);
        return new ResponseEntity<>(constructionService.findAllCustomersFromConstruction(constructionId, pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get all tasks from construction")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all task from construction",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDTO.class)))
    @GetMapping("/construction/{constructionId}/tasks")
    public ResponseEntity<Page<TaskDTO>> getAllTasksFromConstruction(@PathVariable Long constructionId,
                                                                             @PageableDefault(size = 15, sort = "id",
                                                                                     direction = Sort.Direction.ASC) Pageable pageable) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.RETRIEVING_ALL_TASKS_FROM_CONSTRUCTION);
        return new ResponseEntity<>(constructionService.findAllTasksFromConstruction(constructionId, pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get the longest task from construction")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of the longest task from given construction",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDTO.class)))
    @GetMapping("/construction/{constructionId}/tasks/the-longest")
    public ResponseEntity<TaskDTO> getTheLongestTaskFromConstruction(@PathVariable Long constructionId) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.RETRIEVING_MOST_EXPENSIVE_TASK_FROM_CONSTRUCTION);
        return new ResponseEntity<>(constructionService.findTheLongestTaskFromConstruction(constructionId), HttpStatus.OK);
    }

    @Operation(summary = "Get current cost of realization for a construction")
    @GetMapping("/construction/{id}/current-cost")
    public ResponseEntity<BigDecimal> getCurrentCost(@PathVariable Long id) {
        BigDecimal currentCost = constructionService.getCurrentCostOfConstruction(id);
        return ResponseEntity.ok(currentCost);
    }

    @Operation(summary = "Get percent of realization for a construction")
    @ApiResponse(responseCode = "200", description = "Successfully calculated percent of realization",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class)))
    @GetMapping("/construction/{constructionId}/realization")
    public ResponseEntity<Double> getPercentOfRealization(@PathVariable Long constructionId) {
        adminService.verifyAdminAccessAndSaveAction(ActionType.RETRIEVING_PERCENT_OF_REALIZATION);

        double percent = constructionService.updatePercentOfRealization(constructionId);
        return new ResponseEntity<>(percent, HttpStatus.OK);
    }

}