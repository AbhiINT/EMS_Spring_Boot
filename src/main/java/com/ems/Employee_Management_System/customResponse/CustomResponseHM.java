package com.ems.Employee_Management_System.customResponse;

import java.util.HashMap;

public class CustomResponseHM<T> {

    private int status;
    HashMap<String, Integer> StrengthOfDepartment;

    public CustomResponseHM(int status, HashMap<String, Integer> strengthOfDepartment) {
        this.status = status;
        StrengthOfDepartment = strengthOfDepartment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HashMap<String, Integer> getStrengthOfDepartment() {
        return StrengthOfDepartment;
    }

    public void setStrengthOfDepartment(HashMap<String, Integer> strengthOfDepartment) {
        StrengthOfDepartment = strengthOfDepartment;
    }

}
