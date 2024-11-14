package com.trongdev.employeeservice.query.controller;

import com.trongdev.employeeservice.query.model.EmployeeResponseModel;
import com.trongdev.employeeservice.query.queries.GetAllEmployeesQuery;
import com.trongdev.employeeservice.query.queries.GetEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{employeeId}")
    public EmployeeResponseModel getEmployeeDetail(@PathVariable String employeeId){
        GetEmployeeQuery getEmployeeQuery = new GetEmployeeQuery();
        getEmployeeQuery.setEmployeeId(employeeId);

        EmployeeResponseModel employeeResponseModel = queryGateway.query(getEmployeeQuery,
                ResponseTypes.instanceOf(EmployeeResponseModel.class))
                .join();
        return employeeResponseModel;
    }

    @GetMapping
    public List<EmployeeResponseModel> getAllEmployees(){
        List<EmployeeResponseModel> list = queryGateway.query(new GetAllEmployeesQuery(),
                ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class))
                .join();
        return list;
    }

}
