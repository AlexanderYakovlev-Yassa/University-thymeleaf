INSERT INTO public.user_roles(
user_id, 
user_role_id
)
VALUES 
(
select user_id from users u where u.user_username='ivanov', 
select role_id from roles r where r.role_name='STUDENT'
), 
(
select user_id from users u where u.user_username='petrov', 
select role_id from roles r where r.role_name='STUDENT'
), 
(
select user_id from users u where u.user_username='sidorov', 
select role_id from roles r where r.role_name='STUDENT'
), 
(
select user_id from users u where u.user_username='fedotov', 
select role_id from roles r where r.role_name='STUDENT'
), 
(
select user_id from users u where u.user_username='konev', 
select role_id from roles r where r.role_name='STUDENT'
),
(
select user_id from users u where u.user_username='davidov', 
select role_id from roles r where r.role_name='LECTURER'
), 
(
select user_id from users u where u.user_username='oystrax', 
select role_id from roles r where r.role_name='LECTURER'
), 
(
select user_id from users u where u.user_username='magition', 
select role_id from roles r where r.role_name='LECTURER'
), 
(
select user_id from users u where u.user_username='aksenov', 
select role_id from roles r where r.role_name='LECTURER'
),
(
select user_id from users u where u.user_username='aksenov', 
select role_id from roles r where r.role_name='ADMINISTRATOR'
);