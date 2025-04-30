package com.company.CompanyApp.controller;

import com.company.CompanyApp.dto.ConstructionDTO;
import com.company.CompanyApp.dto.TaskDTO;
import com.company.CompanyApp.service.ConstructionService;
import com.company.CompanyApp.service.CustomUserDetailsService;
import com.company.CompanyApp.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final TaskService taskService;

    private final ConstructionService constructionService;

    private final CustomUserDetailsService customUserDetailsService;

    @Operation(summary = "Get all tasks by customer")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all tasks by customer",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/tasks")
    public ResponseEntity<Page<TaskDTO>> getAllTasksByCustomer(
            @PageableDefault(size = 15, sort = "durationDay", direction = Sort.Direction.ASC) Pageable pageable) {
        customUserDetailsService.verifyCustomerAccess();
        return new ResponseEntity<>(taskService.getAllTasks(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get all tasks by customer and done")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all tasks by customer and done",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/tasks/all/done/{done}")
    public ResponseEntity<Page<TaskDTO>> getAllTasksByCustomerAndDone(@PathVariable boolean done,
                                                                     @PageableDefault(size = 15, sort = "durationDay",
                                                                             direction = Sort.Direction.ASC) Pageable pageable) {
        customUserDetailsService.verifyCustomerAccess();
        return new ResponseEntity<>(taskService.findAllTasksByCustomerAndDone(done, pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get all constructions by customer")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all customers by customer",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/constructions/all/customer/{customerId}")
    public ResponseEntity<Page<ConstructionDTO>> getAllConstructionByCustomer(@PathVariable Long customerId,
                                                                              @PageableDefault(size = 15, sort = "location",
                                                                              direction = Sort.Direction.ASC) Pageable pageable) {
        customUserDetailsService.verifyCustomerAccess();
        return new ResponseEntity<>(constructionService.findAllConstructionByCustomer(customerId, pageable), HttpStatus.OK);
    }

}