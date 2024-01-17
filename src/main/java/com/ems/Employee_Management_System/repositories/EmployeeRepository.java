package com.ems.Employee_Management_System.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ems.Employee_Management_System.dto.EmployeeDTO;
import com.ems.Employee_Management_System.dto.SingleEmployeeDTO;
import com.ems.Employee_Management_System.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

     @Query("SELECT e FROM Employee e " +
           "JOIN e.address a " +
           "JOIN e.department d")
    List<Employee> getEmployeesWithDetails();

     @Query("SELECT new com.ems.Employee_Management_System.dto.EmployeeDTO(e.id, e.name, e.email, e.phoneNumber, e.designation, e.salary) FROM Employee e")
    List<EmployeeDTO> findAllEmployees();

     @Query("SELECT new com.ems.Employee_Management_System.dto.SingleEmployeeDTO(e.id, e.name, e.email, e.phoneNumber, e.designation, e.salary) FROM Employee e WHERE e.id = :employeeId")
    SingleEmployeeDTO findSingleEmployeeById(@Param("employeeId") Long employeeId);

}
