package com.project.ems.role;

import com.project.ems.role.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @Positive(message = "Role ID must be positive")
    private Integer id;

    @NotNull(message = "Role type must not be null")
    private RoleType type;
}
