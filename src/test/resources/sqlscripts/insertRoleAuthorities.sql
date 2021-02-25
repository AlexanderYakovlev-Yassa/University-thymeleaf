INSERT INTO role_authorities(role_id, authority_id)	
VALUES 
(
select role_id from roles r where r.role_name='STUDENT', 
select authority_id from authorities a where a.authority_name='Manage students'
),
(
select role_id from roles r where r.role_name='LECTURER', 
select authority_id from authorities a where a.authority_name='Manage students'
),
(
select role_id from roles r where r.role_name='LECTURER', 
select authority_id from authorities a where a.authority_name='Manage lecturers'
),
(
select role_id from roles r where r.role_name='LECTURER', 
select authority_id from authorities a where a.authority_name='Manage timetable'
),
(
select role_id from roles r where r.role_name='ADMINISTRATOR', 
select authority_id from authorities a where a.authority_name='Manage students'
),
(
select role_id from roles r where r.role_name='ADMINISTRATOR', 
select authority_id from authorities a where a.authority_name='Manage lecturers'
),
(
select role_id from roles r where r.role_name='ADMINISTRATOR', 
select authority_id from authorities a where a.authority_name='Manage timetable'
),
(
select role_id from roles r where r.role_name='ADMINISTRATOR', 
select authority_id from authorities a where a.authority_name='Manage courses'
);