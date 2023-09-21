drop table if exists employees_studies;
drop table if exists employees_experiences;
drop table if exists mentors_studies;
drop table if exists mentors_experiences;
drop table if exists users_authorities;
drop table if exists employees;
drop table if exists mentors;
drop table if exists studies;
drop table if exists experiences;
drop table if exists feedbacks;
drop table if exists users;
drop table if exists roles;
drop table if exists authorities;

create table if not exists authorities
(
    id   int auto_increment primary key,
    type varchar(255) not null
);

create table if not exists roles
(
    id   int auto_increment primary key,
    type varchar(255) not null
);

create table if not exists users
(
    birthday date,
    id       int auto_increment primary key,
    role_id  int,
    address  varchar(255),
    email    varchar(255),
    mobile   varchar(255),
    name     varchar(255),
    password varchar(255),
    foreign key (role_id) references roles (id)
);

create table if not exists feedbacks
(
    id          int auto_increment primary key,
    user_id     int,
    sent_at     timestamp(6),
    description varchar(255),
    type        varchar(255) not null,
    foreign key (user_id) references users (id)
);

create table if not exists experiences
(
    finished_at  date,
    id           int auto_increment primary key,
    started_at   date,
    description  varchar(255),
    organization varchar(255),
    title        varchar(255),
    type         varchar(255) not null
);

create table if not exists studies
(
    finished_at date,
    id          int auto_increment primary key,
    started_at  date,
    description varchar(255),
    institution varchar(255),
    title       varchar(255),
    type        varchar(255) not null
);

create table if not exists mentors
(
    birthday          date,
    id                int auto_increment primary key,
    open_for_training boolean,
    max_trainees      int,
    mentor_id         int,
    nr_trainees       int,
    role_id           int,
    address           varchar(255),
    email             varchar(255),
    employment_type   varchar(255) not null,
    grade             varchar(255) not null,
    mobile            varchar(255),
    name              varchar(255),
    password          varchar(255),
    position          varchar(255) not null,
    foreign key (mentor_id) references mentors (id),
    foreign key (role_id) references roles (id)
);

create table if not exists employees
(
    birthday        date,
    id              int auto_increment primary key,
    mentor_id       int,
    role_id         int,
    address         varchar(255),
    email           varchar(255),
    employment_type varchar(255) not null,
    grade           varchar(255) not null,
    mobile          varchar(255),
    name            varchar(255),
    password        varchar(255),
    position        varchar(255) not null,
    foreign key (mentor_id) references mentors (id),
    foreign key (role_id) references roles (id)
);

create table if not exists users_authorities
(
    authority_id int not null,
    user_id      int not null,
    foreign key (authority_id) references authorities (id),
    foreign key (user_id) references users (id)
);

create table if not exists mentors_experiences
(
    experience_id int not null,
    mentor_id     int not null,
    foreign key (experience_id) references experiences (id),
    foreign key (mentor_id) references mentors (id)
);

create table if not exists mentors_studies
(
    mentor_id int not null,
    study_id  int not null,
    foreign key (mentor_id) references mentors (id),
    foreign key (study_id) references studies (id)
);

create table if not exists employees_experiences
(
    experience_id int not null,
    employee_id   int not null,
    foreign key (experience_id) references experiences (id),
    foreign key (employee_id) references employees (id)
);

create table if not exists employees_studies
(
    employee_id int not null,
    study_id    int not null,
    foreign key (employee_id) references employees (id),
    foreign key (study_id) references studies (id)
);