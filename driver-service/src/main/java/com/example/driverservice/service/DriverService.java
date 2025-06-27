package com.example.driverservice.service;

import com.commonlib.dto.DriverLoginRequest;
import com.commonlib.dto.DriverRegisterRequest;
import com.example.driverservice.entity.Driver;

import java.util.List;

public interface DriverService {
    String register(DriverRegisterRequest request);
    String login(DriverLoginRequest request);
    List<Driver> getAvailableDrivers();
    void updateStatusByPhone(String phone, boolean available);
    Driver getProfileByPhone(String phone); // New method
}