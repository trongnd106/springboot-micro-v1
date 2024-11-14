package com.trongdev.employeeservice.command.aggregate;

import com.trongdev.employeeservice.command.command.CreateEmployeeCommand;
import com.trongdev.employeeservice.command.command.DeleteEmployeeCommand;
import com.trongdev.employeeservice.command.command.UpdateEmployeeCommand;
import com.trongdev.employeeservice.command.events.EmployeeCreatedEvent;
import com.trongdev.employeeservice.command.events.EmployeeDeletedEvent;
import com.trongdev.employeeservice.command.events.EmployeeUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class EmployeeAggregate {
    @AggregateIdentifier
    private String employeeId;
    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDisciplined;

    public EmployeeAggregate(){}

    @CommandHandler
    public EmployeeAggregate(CreateEmployeeCommand command){
        EmployeeCreatedEvent event = new EmployeeCreatedEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }
    @CommandHandler
    public void handle(UpdateEmployeeCommand command){
        EmployeeUpdatedEvent event = new EmployeeUpdatedEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }
    @CommandHandler
    public void handle(DeleteEmployeeCommand command){
        EmployeeDeletedEvent event = new EmployeeDeletedEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }
// --v go to eventsourcing handler -- go to handle event
    @EventSourcingHandler
    public void on(EmployeeCreatedEvent event){
        this.employeeId = event.getEmployeeId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.kin = event.getKin();
        this.isDisciplined = event.getDisciplined();
    }
    @EventSourcingHandler
    public void on(EmployeeUpdatedEvent event){
        this.employeeId = event.getEmployeeId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.kin = event.getKin();
        this.isDisciplined = event.getDisciplined();
    }
    @EventSourcingHandler
    public void on(EmployeeDeletedEvent event){
        this.employeeId = event.getEmployeeId();
    }
}
