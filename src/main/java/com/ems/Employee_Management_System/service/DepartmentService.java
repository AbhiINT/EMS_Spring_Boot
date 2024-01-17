package com.ems.Employee_Management_System.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ems.Employee_Management_System.customResponse.CustomResponse;
import com.ems.Employee_Management_System.entity.Department;
import com.ems.Employee_Management_System.entity.Employee;
import com.ems.Employee_Management_System.repositories.DepartmentRepository;
import com.ems.Employee_Management_System.repositories.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Transactional
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Transactional
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public boolean updateDepartment(Long id, Department department) {
        Department existingDepartment = getDepartmentById(id);

        if (existingDepartment != null) {
            existingDepartment.setManagerName(department.getManagerName());
            existingDepartment.setDepartmentName(department.getDepartmentName());
            existingDepartment.setDepartmentId(department.getDepartmentId());
            existingDepartment.setInProjectStatus(department.isInProjectStatus());
            saveDepartment(existingDepartment);
            return true;
        } else {
            return false;
        }
    }

    public Optional<String> getDepartmentManagerByName(String departmentName) {
        List<Department> departments = getAllDepartments();

        for (Department department : departments) {
            if (department.getDepartmentName().equalsIgnoreCase(departmentName)) {
                return Optional.ofNullable(department.getManagerName());
            }
        }

        return Optional.empty();
    }

    public ResponseEntity<CustomResponse<String>> getmaxEmployeeinDepartment() {
        List<Employee> employees = employeeRepository.findAll();
        HashMap<String, Integer> resMap = new HashMap<>();

        for (Employee employee : employees) {
            String departmentName = employee.getDepartment() != null ? employee.getDepartment().getDepartmentName()
                    : "Unknown";
            resMap.put(departmentName, resMap.getOrDefault(departmentName, 0) + 1);
        }
        int maxValue = 0;
        String dept = null;
        for (Map.Entry<String, Integer> entry : resMap.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                dept = entry.getKey();
            }
        }
        if (employees.size() == 0) {
            CustomResponse<String> customResponse = new CustomResponse<>(500,
                    "NO Employee Found ! Oertaion Remain Unsuccessfully");
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);

        } else {
            CustomResponse<String> customResponse = new CustomResponse<>(500,
                    "Max employe is in " + dept + " department total employee:" + maxValue);
            return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);

        }

    }

    public HashMap<String, Integer> strengthPerDepartment() {
        List<Employee> employees = employeeRepository.findAll();
        HashMap<String, Integer> departmentStrength = new HashMap<>();

        for (Employee employee : employees) {
            String departmentName = (employee.getDepartment() != null) ? employee.getDepartment().getDepartmentName()
                    : "Unknown";
            departmentStrength.put(departmentName, departmentStrength.getOrDefault(departmentName, 0) + 1);
        }

        return departmentStrength;
    }

    public Set<String> getAllWorkingDepartments() {
        List<Department> departments = getAllDepartments();
        Set<String> workingDepartments = new HashSet<>();

        for (Department department : departments) {
            if (department.isInProjectStatus()) {
                workingDepartments.add(department.getDepartmentName());
            }
        }

        return workingDepartments;
    }

}