package com.ems.Employee_Management_System.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.ems.Employee_Management_System.customResponse.CustomResponse;
import com.ems.Employee_Management_System.entity.Address;
import com.ems.Employee_Management_System.entity.Department;
import com.ems.Employee_Management_System.entity.Employee;
import com.ems.Employee_Management_System.repositories.AddressRepository;
import com.ems.Employee_Management_System.repositories.DepartmentRepository;
import com.ems.Employee_Management_System.repositories.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    static List<String> deptLister = Arrays.asList("Java", "DotNet", "JS", "Python", "Php", "Rust", "Flutter");
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public boolean saveEmployee(Employee employee) {
        List<Employee> e = this.getAllEmployees();
        for (Employee emp : e) {
            if (emp.getEmail().equalsIgnoreCase(employee.getEmail())
                    || emp.getPhoneNumber().equals(employee.getPhoneNumber())) {
                return false;

            }
        }
        Address address = employee.getAddress();
        if (address != null) {
            addressRepository.save(address);
        }

        Department department = employee.getDepartment();
        if (department != null) {
            departmentRepository.save(department);
        }
        employee.getDepartment().setDepartmentId(deptLister.indexOf(employee.getDepartment().getDepartmentName()) + 1);
        employeeRepository.save(employee);

        return true;
    }

    @Transactional
    public Address findAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID " + addressId));
    }

    @Transactional
    public Department findDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with ID " + departmentId));
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId)
               .orElse(null);
    }

    @Transactional
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Transactional
    public boolean updateEmployee(Long id, Employee emp) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
           

            // Assuming Address is a separate entity
            Address existingAddress = existingEmployee.getAddress();
            Address newAddress = emp.getAddress();

            Department exixtingDepartment = existingEmployee.getDepartment();
            Department newDept = emp.getDepartment();

            exixtingDepartment.setDepartmentId(newDept.getDepartmentId());
            exixtingDepartment.setDepartmentName(newDept.getDepartmentName());
            exixtingDepartment.setManagerName(newDept.getManagerName());
            exixtingDepartment.setInProjectStatus(newDept.isInProjectStatus());

            // Update the existing address with values from the new address
            existingAddress.setStreet(newAddress.getStreet());
            existingAddress.setCity(newAddress.getCity());
            existingAddress.setState(newAddress.getState());
            existingAddress.setZipCode(newAddress.getZipCode());

            existingEmployee.setName(emp.getName());
            existingEmployee.setSalary(emp.getSalary());
            existingEmployee.setDesignation(emp.getDesignation());
            existingEmployee.setEmail(emp.getEmail());
            existingEmployee.setPhoneNumber(emp.getPhoneNumber());
            existingEmployee.setDepartment(emp.getDepartment());

          

            employeeRepository.save(existingEmployee);

            return true;
        } else {
            return false;
        }
    }

    public Employee getEmployeeWithHighestSalary() {
        List<Employee> employees = getAllEmployees();
        return employees.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }

    public List<String> getNamesStartsWith(String namePrefix) {
        List<Employee> employees = getAllEmployees();
        List<String> result = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getName().startsWith(namePrefix)) {
                result.add(employee.getName());
            }
        }

        return result;
    }

    public List<String> getEmployeeNamesByDepartmentManagerName(String managerName) {
        List<Employee> employees = getAllEmployees();
        List<String> result = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getDepartment() != null &&
                    employee.getDepartment().getManagerName().equals(managerName)) {
                result.add(employee.getName());
            }
        }

        return result;
    }

}