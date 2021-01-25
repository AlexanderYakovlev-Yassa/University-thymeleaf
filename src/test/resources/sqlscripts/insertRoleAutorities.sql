INSERT INTO role_autorities(role_id, autority_id)	
VALUES 
(
select role_id from roles r where r.role_name='STUDENT', 
select autority_id from autorities a where a.autority_name='Manage students'
),
(
select role_id from roles r where r.role_name='LECTURER', 
select autority_id from autorities a where a.autority_name='Manage students'
),
(
select role_id from roles r where r.role_name='LECTURER', 
select autority_id from autorities a where a.autority_name='Manage lecturers'
),
(
select role_id from roles r where r.role_name='LECTURER', 
select autority_id from autorities a where a.autority_name='Manage timetable'
),
(
select role_id from roles r where r.role_name='ADMINISTRATOR', 
select autority_id from autorities a where a.autority_name='Manage students'
),
(
select role_id from roles r where r.role_name='ADMINISTRATOR', 
select autority_id from autorities a where a.autority_name='Manage lecturers'
),
(
select role_id from roles r where r.role_name='ADMINISTRATOR', 
select autority_id from autorities a where a.autority_name='Manage timetable'
),
(
select role_id from roles r where r.role_name='ADMINISTRATOR', 
select autority_id from autorities a where a.autority_name='Manage courses'
);