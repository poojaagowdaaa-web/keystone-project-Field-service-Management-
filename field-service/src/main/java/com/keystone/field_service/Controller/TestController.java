package com.keystone.field_service.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test() {
        return "JWT authentication is working!";
    }

        @GetMapping("/user-test")
        @PreAuthorize("hasRole('USER')")
        public String userTest() {
            return "USER access is working!";
        }

        @GetMapping("/admin-test")
        @PreAuthorize("hasRole('ADMIN')")
        public String adminTest() {
            return "ADMIN access is working!";
    }
}
