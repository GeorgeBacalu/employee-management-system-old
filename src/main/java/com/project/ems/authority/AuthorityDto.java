package com.project.ems.authority;

import com.project.ems.authority.enums.AuthorityType;
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
public class AuthorityDto {

    @Positive(message = "Role ID must be positive")
    private Integer id;

    @NotNull(message = "Authority type must not be null")
    private AuthorityType type;
}
