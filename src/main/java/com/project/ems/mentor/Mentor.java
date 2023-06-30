package com.project.ems.mentor;

import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import com.project.ems.role.Role;
import com.project.ems.study.Study;
import com.project.ems.experience.Experience;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mentors")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private String password;

    private String mobile;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor supervisingMentor;

    @ManyToMany
    @JoinTable(name = "mentors_studies",
          joinColumns = @JoinColumn(name = "mentor_id"),
          inverseJoinColumns = @JoinColumn(name = "study_id"))
    @ToString.Exclude
    private List<Study> studies;

    @ManyToMany
    @JoinTable(name = "mentors_experiences",
          joinColumns = @JoinColumn(name = "mentor_id"),
          inverseJoinColumns = @JoinColumn(name = "experience_id"))
    @ToString.Exclude
    private List<Experience> experiences;

    private Integer nrTrainees;

    private Integer maxTrainees;

    private Boolean isTrainingOpen;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Mentor mentor = (Mentor) o;
        return getId() != null && Objects.equals(getId(), mentor.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
