package com.ems.Employee_Management_System.controller;

import java.util.HashMap;

import java.util.List;

import java.util.Optional;
import java.util.Set;

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

import com.ems.Employee_Management_System.customResponse.CustomResponse;
import com.ems.Employee_Management_System.customResponse.CustomResponseHM;
import com.ems.Employee_Management_System.entity.Department;

import com.ems.Employee_Management_System.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public Department saveDepartment(@RequestBody Department department) {
        return departmentService.saveDepartment(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<String>> updateDepartment(@PathVariable Long id,
            @RequestBody Department department) {
        try {
            if (departmentService.updateDepartment(id, department)) {
                CustomResponse<String> customResponse = new CustomResponse<>(200,
                        "Success! Department Updated Successfully");
                return new ResponseEntity<>(customResponse, HttpStatus.OK);
            } else {
                throw new RuntimeException("Department not Found or Internal Problem Arises! Unable to Update");
            }
        } catch (Exception e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(500,
                    "Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @GetMapping("/departMentManager")
    public ResponseEntity<CustomResponse<String>> getDepartmentManagerByName(@RequestParam String name) {
        try {
            Optional<String> managerName = departmentService.getDepartmentManagerByName(name);

            if (managerName.isPresent()) {
                CustomResponse<String> customResponse = new CustomResponse<>(200,
                        "The Manager of the Department " + name + " is: " + managerName.get());
                return new ResponseEntity<>(customResponse, HttpStatus.OK);
            } else {
                throw new RuntimeException(
                        "Department not Found or Internal Problem Arises! Unable to Retrieve Manager");
            }
        } catch (Exception e) {
            CustomResponse<String> errorResponse = new CustomResponse<>(500,
                    "Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/AllInActiveProject")
    public ResponseEntity<Set<String>> getAllWorkingDepartments() {
        Set<String> result = departmentService.getAllWorkingDepartments();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/strengthOfDepartment")
    public ResponseEntity<CustomResponseHM<HashMap<String, Integer>>> strengthPerDepartment() {
        HashMap<String, Integer> hs = departmentService.strengthPerDepartment();
        CustomResponseHM<HashMap<String, Integer>> res = new CustomResponseHM<>(200, hs);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/departmaneNamebyID/{i}")
    public String getDepartmentNameById(@PathVariable Long i) {

        return EmployeeController.deptLister.get(i.intValue() - 1);
    }

    @GetMapping("/maxEmployeeinDepartment")

    public ResponseEntity<CustomResponse<String>> getmaxEmployeeinDepartment() {
        ResponseEntity<CustomResponse<String>> res = departmentService.getmaxEmployeeinDepartment();
        return res;

    }
}
