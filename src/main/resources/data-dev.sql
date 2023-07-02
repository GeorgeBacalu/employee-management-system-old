INSERT INTO roles(authority) VALUES ('USER'), ('ADMIN');

INSERT INTO users(name, email, password, mobile, address, birthday, role_id) VALUES
('User_name1', 'User_email1@email.com', '#User_password1', '+40700000001', 'User_address1', '1990-01-01', 1),
('User_name2', 'User_email2@email.com', '#User_password2', '+40700000002', 'User_address2', '1990-01-02', 2);

INSERT INTO feedbacks(type, description, sent_at, user_id) VALUES
('ISSUE', 'Feedback_description1', '2010-01-01 00:00', 1),
('OPTIMIZATION', 'Feedback_description2', '2010-01-02 00:00', 2);

INSERT INTO experiences(title, organization, description, type, started_at, finished_at) VALUES
('Experience_title1', 'Experience_organization1', 'Experience_description1', 'APPRENTICESHIP', '2010-01-01', '2011-01-01'),
('Experience_title2', 'Experience_organization2', 'Experience_description2', 'INTERNSHIP', '2011-01-01', '2012-01-01'),
('Experience_title3', 'Experience_organization3', 'Experience_description3', 'TRAINING', '2012-01-01', '2013-01-01'),
('Experience_title4', 'Experience_organization4', 'Experience_description4', 'VOLUNTEERING', '2013-01-01', '2014-01-01');

INSERT INTO studies(title, institution, description, type, started_at, finished_at) VALUES
('Study_title1', 'Study_institution1', 'Study_description1', 'BACHELORS', '2010-01-01', '2014-01-01'),
('Study_title2', 'Study_institution2', 'Study_description2', 'MASTERS', '2014-01-01', '2018-01-01'),
('Study_title3', 'Study_institution3', 'Study_description3', 'SPECIALIZED_TRAINING', '2018-01-01', '2020-01-01'),
('Study_title4', 'Study_institution4', 'Study_description4', 'SPECIALIZED_TRAINING', '2020-01-01', '2022-01-01');

INSERT INTO mentors(name, email, password, mobile, address, birthday, role_id, employment_type, position, grade, mentor_id, nr_trainees, max_trainees, is_training_open) VALUES
('Mentor_name1', 'Mentor_email1@email.com', '#Mentor_password1', '+40700000001', 'Mentor_address1', '1990-01-01', 1, 'FULL_TIME', 'FRONTEND', 'SENIOR', null, 3, 6, false),
('Mentor_name2', 'Mentor_email2@email.com', '#Mentor_password2', '+40700000002', 'Mentor_address2', '1990-01-02', 2, 'FULL_TIME', 'BACKEND', 'SENIOR', 1, 2, 5, true);

INSERT INTO employees(name, email, password, mobile, address, birthday, role_id, employment_type, position, grade, mentor_id) VALUES
('Employee_name1', 'Employee_email1@email.com', '#Employee_password1', '+40700000001', 'Employee_address1', '1990-01-01', 1, 'FULL_TIME', 'FRONTEND', 'MID', 1),
('Employee_name2', 'Employee_email2@email.com', '#Employee_password2', '+40700000002', 'Employee_address2', '1990-01-02', 2, 'PART_TIME', 'BACKEND', 'JUNIOR', 2);

INSERT INTO mentors_experiences(mentor_id, experience_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);

INSERT INTO mentors_studies(mentor_id, study_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);

INSERT INTO employees_experiences(employee_id, experience_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);

INSERT INTO employees_studies(employee_id, study_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);