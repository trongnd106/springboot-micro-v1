package com.trongdev.employeeservice.command.controller;

import com.trongdev.employeeservice.command.command.CreateEmployeeCommand;
import com.trongdev.employeeservice.command.command.DeleteEmployeeCommand;
import com.trongdev.employeeservice.command.command.UpdateEmployeeCommand;
import com.trongdev.employeeservice.command.model.EmployeeRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addEmployee(@RequestBody EmployeeRequestModel model){
        CreateEmployeeCommand command = new CreateEmployeeCommand(
                model.getEmployeeId(), model.getFirstName(), model.getLastName(), model.getKin(), false);
        commandGateway.sendAndWait(command);
        return "employee added";
    }

    @PutMapping
    public String updateEmployee(@RequestBody EmployeeRequestModel model){
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(
                model.getEmployeeId(), model.getFirstName(), model.getLastName(), model.getKin(), model.getDisciplined()
        );
        commandGateway.sendAndWait(command);
        return "employee updated";
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable String employeeId){
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
        commandGateway.sendAndWait(command);
        return "employee deleted";
    }

}
