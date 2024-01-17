package com.ems.Employee_Management_System.entity;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Department {
    static List<String> deptLister = Arrays.asList("Java", "DotNet", "JS", "Python", "Php", "Rust", "Flutter");
    @Id
    @SequenceGenerator(name = "department_id_seq", sequenceName = "department_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_seq")
    private Long id;
    private Integer departmentId;
    private String DepartmentName;
    private String ManagerName;
    private boolean inProjectStatus;

    public Department(Long id, Integer departmentId, String departmentName, String managerName,
            boolean inProjectStatus) {
        this.id = id;
        this.departmentId = departmentId;
        DepartmentName = departmentName;
        ManagerName = managerName;
        this.inProjectStatus = inProjectStatus;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        ManagerName = managerName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public Department() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isInProjectStatus() {
        return inProjectStatus;
    }

    public void setInProjectStatus(boolean inProjectStatus) {
        this.inProjectStatus = inProjectStatus;
    }

}
