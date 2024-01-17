package com.ems.Employee_Management_System.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.Employee_Management_System.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}