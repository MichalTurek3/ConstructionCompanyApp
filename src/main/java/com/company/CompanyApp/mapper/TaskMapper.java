package com.company.CompanyApp.mapper;

import com.company.CompanyApp.command.TaskCommand;
import com.company.CompanyApp.domain.Task;
import com.company.CompanyApp.dto.TaskDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDTO taskToTaskDTO(Task task);

    Task taskCommandToTask(TaskCommand taskCommand);
}
