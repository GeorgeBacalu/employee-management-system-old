package com.project.ems.person;

import com.project.ems.authority.Authority;
import com.project.ems.role.Role;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String name;

    protected String email;

    protected String password;

    protected String mobile;

    protected String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "role_id")
    protected Role role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "authority_id"))
    protected List<Authority> authorities;
}
