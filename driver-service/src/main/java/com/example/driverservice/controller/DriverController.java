package com.example.driverservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.commonlib.dto.DriverLoginRequest;
import com.commonlib.dto.DriverRegisterRequest;
import com.commonlib.util.JwtUtil;
import com.example.driverservice.entity.Driver;
import com.example.driverservice.service.DriverService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody DriverRegisterRequest request) {
        try {
            return ResponseEntity.ok(driverService.register(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody DriverLoginRequest request) {
        try {
            return ResponseEntity.ok(driverService.login(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<Driver>> getAvailableDrivers() {
        try {
            return ResponseEntity.ok(driverService.getAvailableDrivers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/status")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<String> updateStatus(@RequestHeader("Authorization") String token, @RequestParam boolean available) {
        try {
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            String phone = jwtUtil.extractUsername(jwtToken);
            driverService.updateStatusByPhone(phone, available);
            return ResponseEntity.ok("Driver status updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update status: " + e.getMessage());
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<Driver> getProfile(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            String phone = jwtUtil.extractUsername(jwtToken);
            Driver driver = driverService.getProfileByPhone(phone);
            return ResponseEntity.ok(driver);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Global Exception Handler for this Controller
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
