
package com.example.driverservice.controller;

import com.commonlib.dto.DriverLoginRequest;
import com.commonlib.dto.DriverRegisterRequest;
import com.example.driverservice.entity.Driver;
import com.example.driverservice.service.DriverService;
import com.commonlib.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody DriverRegisterRequest request) {
        return ResponseEntity.ok(driverService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody DriverLoginRequest request) {
        return ResponseEntity.ok(driverService.login(request));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Driver>> getAvailableDrivers() {
        return ResponseEntity.ok(driverService.getAvailableDrivers());
    }

    @PutMapping("/status")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<String> updateStatus(@RequestHeader("Authorization") String token, @RequestParam boolean available) {
        // Extract the token from the "Authorization" header
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Extract the phone number from the JWT
        String phone = jwtUtil.extractUsername(jwtToken);

        // Update the driver's status using the phone number
        driverService.updateStatusByPhone(phone, available);
        return ResponseEntity.ok("Driver status updated");
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<Driver> getProfile(@RequestHeader("Authorization") String token) {
        // Extract the token from the "Authorization" header
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Extract the phone number from the JWT
        String phone = jwtUtil.extractUsername(jwtToken);

        // Fetch the driver's profile using the phone number
        Driver driver = driverService.getProfileByPhone(phone);
        return ResponseEntity.ok(driver);
    }
}
