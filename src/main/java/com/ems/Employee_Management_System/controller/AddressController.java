package com.ems.Employee_Management_System.controller;

import java.util.LinkedHashSet;
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

import com.ems.Employee_Management_System.customResponse.CustomResponse;
import com.ems.Employee_Management_System.entity.Address;
import com.ems.Employee_Management_System.entity.Employee;
import com.ems.Employee_Management_System.service.AddressService;
import com.ems.Employee_Management_System.service.EmployeeService;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @GetMapping("/namesOfAllStates")
    public LinkedHashSet<String> getNameOfAllState() {

        List<Address> list = addressService.getAllAddresses();
        LinkedHashSet<String> hs = new LinkedHashSet<>();
        for (Address x : list) {
            if (x.getState() != null)
                hs.add(x.getState());
        }
        return hs;
    }

    @GetMapping("/countByStateName")
    public String getCountOfEmployeeByState(@RequestParam String name) {
        List<Address> list = addressService.getAllAddresses();
        int count = 0;
        for (Address x : list) {
            if (x.getState().equalsIgnoreCase(name))
                count++;
        }

        return "The total employee from the state " + name + " is :" + count;
    }

    @PostMapping
    public Address saveAddress(@RequestBody Address address) {
        return addressService.saveAddress(address);
    }

    @PutMapping("/updateAddress/{id}")
    public ResponseEntity<CustomResponse<String>> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        Employee existingAddress = employeeService.getEmployeeById(id);
        if (existingAddress != null) {
            existingAddress.getAddress().setStreet(address.getStreet());
            existingAddress.getAddress().setCity(address.getCity());
            existingAddress.getAddress().setState(address.getState());
            existingAddress.getAddress().setZipCode(address.getZipCode());
            employeeService.saveEmployee(existingAddress);
            CustomResponse<String> customResponse = new CustomResponse<>(200,
                    "Success ! Address Updated Successfully");
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        } else {

            CustomResponse<String> errorResponse = new CustomResponse<>(500,
                    "Employee not Found ! Unable to Delete");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }
}
