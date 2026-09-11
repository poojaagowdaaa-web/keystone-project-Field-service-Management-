package com.keystone.field_service.Controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('MANAGER')")
    public String managerTest() {
        return "Manager access successful!";
    }
}

