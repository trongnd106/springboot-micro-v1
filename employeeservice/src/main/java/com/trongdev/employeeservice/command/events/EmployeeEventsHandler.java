package com.trongdev.employeeservice.command.events;

import com.trongdev.employeeservice.command.data.Employee;
import com.trongdev.employeeservice.command.data.EmployeeRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeEventsHandler {
    @Autowired
    EmployeeRepository employeeRepository;

    @EventHandler
    public void on(EmployeeCreatedEvent event){
        Employee employee = new Employee();
        BeanUtils.copyProperties(event,employee);
        employeeRepository.save(employee);
    }

    @EventHandler
    public void on(EmployeeUpdatedEvent event){
        Employee employee = employeeRepository.findById(event.getEmployeeId()).orElseThrow(
                () -> new RuntimeException("Employee not found")
        );
        employee.setFirstName(event.getFirstName());
        employee.setLastName(event.getLastName());
        employee.setKin(event.getKin());
        employee.setDisciplined(event.getDisciplined());
        employeeRepository.save(employee);
    }

    @EventHandler
    public void on(EmployeeDeletedEvent event){
        employeeRepository.deleteById(event.getEmployeeId());
    }
}
