package com.ems.Employee_Management_System.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.Employee_Management_System.customException.DuplicateDataFoundException;
import com.ems.Employee_Management_System.customException.EmployeeNotFoundException;
import com.ems.Employee_Management_System.customResponse.CustomResponse;
import com.ems.Employee_Management_System.dto.EmployeeDTO;
import com.ems.Employee_Management_System.dto.SingleEmployeeDTO;
import com.ems.Employee_Management_System.entity.Employee;
import com.ems.Employee_Management_System.repositories.EmployeeRepository;
import com.ems.Employee_Management_System.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    static List<String> deptLister = Arrays.asList("Java", "DotNet", "JS", "Python", "Php", "Rust", "Flutter");

    @Autowired
    private EmployeeService employeeService;

    @Autowired EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        System.out.println("Getting hit postman");
        return ResponseEntity.ok(employeeRepository.findAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable String id) {

        try {
            Long employeeId = Long.parseLong(id);
            SingleEmployeeDTO employee = employeeRepository.findSingleEmployeeById(employeeId);
    
            if (employee == null) {
                throw new EmployeeNotFoundException("Employee with ID not found","Employee You are trying to search or assess is Not found in the database");
            }
    
            return ResponseEntity.ok(employee);
        } catch (NumberFormatException e) {
            throw new EmployeeNotFoundException("Invalid employee ID format","The Id You are using is not valid format");
        }

    }

    @PostMapping
    public ResponseEntity<CustomResponse<String>> saveEmployee(@RequestBody Employee employee) {

      
            if (employeeService.saveEmployee(employee) == false) {
                throw new DuplicateDataFoundException("Duplicate Data Found! Unable to add");
            }

            CustomResponse<String> customResponse = new CustomResponse<>(200, "Success! Employee Added Successfully");
            return new ResponseEntity<>(customResponse, HttpStatus.OK);

       

        

    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> updateEmployee(@PathVariable Long id,
            @RequestBody Employee employee) {
        try {
            if (employeeService.updateEmployee(id, employee)) {
                CustomResponse<String> successResponse = new CustomResponse<>(200,
                        "Success! Details Updated Successfully");
                return new ResponseEntity<>(successResponse, HttpStatus.OK);
            } else {
                throw new RuntimeException("Employee not Found or Internal Problem Arises! Unable to Update");
            }
        } catch (Exception e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(500,
                    "Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> deleteEmployee(@PathVariable Long id) {
        try {
            if (employeeService.getEmployeeById(id) != null) {
                employeeService.deleteEmployee(id);
                CustomResponse<String> customResponse = new CustomResponse<>(200,
                        "Success! Employee Deleted Successfully");
                return new ResponseEntity<>(customResponse, HttpStatus.OK);
            } else {
                throw new RuntimeException("Employee not Found! Unable to Delete");
            }
        } catch (Exception e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(500,
                    "Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/EmployeeBydepartmentName")
    public ResponseEntity<List<String>> getEmployeeNamesByDepartmentManagerName(@RequestParam String name) {
        List<String> namesByDepartmentManager = employeeService.getEmployeeNamesByDepartmentManagerName(name);

        return new ResponseEntity<>(namesByDepartmentManager, HttpStatus.OK);
    }

    @GetMapping("/highest-salary")
    public ResponseEntity<Employee> getEmployeeWithHighestSalary() {
        try {
            Employee employeeWithMaxSalary = employeeService.getEmployeeWithHighestSalary();

            if (employeeWithMaxSalary != null) {
                return new ResponseEntity<>(employeeWithMaxSalary, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/salaryById/{id}")
    public ResponseEntity<CustomResponse<String>> getEmployeeSalary(@PathVariable Long id) {

        Employee e = employeeService.getEmployeeById(id);
        try {
            if (e != null) {

                CustomResponse<String> customResponse = new CustomResponse<>(200,
                        "Salary of the Employee " + e.getName() + " Employee id :" + e.getId() + " is:"
                                + e.getSalary());
                return new ResponseEntity<>(customResponse, HttpStatus.OK);

            }
        } catch (Exception ex) {
             System.out.println(ex.getMessage());
            

        }
        CustomResponse<String> customResponse = new CustomResponse<>(500,
                    "Employee with ID" + id + " is not Found ! Please Enter Valid Id");
            return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/nameStartsWith")
    public ResponseEntity<List<String>> getNamesStartsWith(@RequestParam String name) {
        List<String> namesStartsWith = employeeService.getNamesStartsWith(name);

        return new ResponseEntity<>(namesStartsWith, HttpStatus.OK);
    }
    @GetMapping("/withDetails")
    public List<Employee> getEmployeesWithDetails() {
        return employeeRepository.getEmployeesWithDetails();
    }
}
