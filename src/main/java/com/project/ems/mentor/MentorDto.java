package com.project.ems.mentor;

import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorDto {

    private Integer id;

    private String name;

    private String email;

    private String password;

    private String mobile;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Integer roleId;

    private EmploymentType employmentType;

    private Position position;

    private Grade grade;

    private Integer supervisingMentorId;

    private List<Integer> studiesIds;

    private List<Integer> experiencesIds;

    private Integer nrTrainees;

    private Integer maxTrainees;

    private Boolean isTrainingOpen;
}
