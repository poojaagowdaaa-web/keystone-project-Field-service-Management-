package com.kajal.FiledServiceMProject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRequest {
    @NotNull
    private Long technicianId;
}
