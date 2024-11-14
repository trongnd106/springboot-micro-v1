package com.trongdev.employeeservice.query.projection;

import com.trongdev.employeeservice.command.data.Employee;
import com.trongdev.employeeservice.command.data.EmployeeRepository;
import com.trongdev.employeeservice.query.model.EmployeeResponseModel;
import com.trongdev.employeeservice.query.queries.GetAllEmployeesQuery;
import com.trongdev.employeeservice.query.queries.GetEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeProjection {
    @Autowired
    EmployeeRepository employeeRepository;

    @QueryHandler
    public EmployeeResponseModel handle(GetEmployeeQuery query){
        EmployeeResponseModel model = new EmployeeResponseModel();
        Employee employee = employeeRepository.findById(query.getEmployeeId()).orElseThrow(
                () -> new RuntimeException("Employee not found")
        );
        BeanUtils.copyProperties(employee,model);
        return model;
    }

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeesQuery query){
        List<EmployeeResponseModel> listModel = new ArrayList<>();
        List<Employee> listEntity = employeeRepository.findAll();
        listEntity.forEach(e -> {
            EmployeeResponseModel model = new EmployeeResponseModel();
            BeanUtils.copyProperties(model,e);
            listModel.add(model);
        });
        return listModel;
    }
}
