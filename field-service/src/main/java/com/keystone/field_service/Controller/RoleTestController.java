package com.keystone.field_service.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleTestController {

    @GetMapping("/test")
    public String managerTest() {
        return "MANAGER access granted!";
    }

    @GetMapping("/api/dispatcher/test")
    public String dispatcherTest() {
        return "DISPATCHER access granted!";
    }

    @GetMapping("/api/technician/test")
    public String technicianTest() {
        return "TECHNICIAN access granted!";
    }
}