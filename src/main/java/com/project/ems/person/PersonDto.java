package com.project.ems.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

    @Positive(message = "User ID must be positive")
    private Integer id;

    @NotBlank(message = "User name must not be blank")
    private String name;

    @NotBlank(message = "User email must not be blank")
    @Email(message = "The provided email must be valid")
    private String email;

    @NotBlank(message = "User password must not be blank")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+-=()])(?=\\S+$).{8,30}$", message = "The provided password must be valid")
    private String password;

    @NotBlank(message = "User mobile must not be blank")
    @Pattern(regexp = "^(00|\\+?40|0)(7\\d{2}|\\d{2}[13]|[2-37]\\d|8[02-9]|9[0-2])\\s?\\d{3}\\s?\\d{3}$", message = "The provided mobile must be valid")
    private String mobile;

    @NotBlank(message = "User address must not be blank")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotNull(message = "Role ID must not be null")
    @Positive(message = "Role ID must be positive")
    private Integer roleId;
}
