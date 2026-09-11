package com.kajal.FiledServiceMProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private String role;
    private Long organizationId;
    private String organizationName;
    private String inviteCode;
}
