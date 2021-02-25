INSERT INTO users(user_username, user_password, user_person_id, user_enabled)
VALUES (
'ivanov', 
'$2y$12$8Vzwr/y5iy3DfsXikJka4eJZsUcz5ZGZRK6ZmbWrY.kB5TAPFROUi', 
select person_id from persons p where p.person_last_name='Иванов', 
true
),
(
'petrov', 
'$2y$12$8Vzwr/y5iy3DfsXikJka4eJZsUcz5ZGZRK6ZmbWrY.kB5TAPFROUi', 
select person_id from persons p where p.person_last_name='Петров', 
true
),
(
'sidorov', 
'$2y$12$8Vzwr/y5iy3DfsXikJka4eJZsUcz5ZGZRK6ZmbWrY.kB5TAPFROUi', 
select person_id from persons p where p.person_last_name='Сидоров', 
true
),
(
'fedotov', 
'$2y$12$8Vzwr/y5iy3DfsXikJka4eJZsUcz5ZGZRK6ZmbWrY.kB5TAPFROUi', 
select person_id from persons p where p.person_last_name='Федотов', 
true
),
(
'konev', 
'$2y$12$8Vzwr/y5iy3DfsXikJka4eJZsUcz5ZGZRK6ZmbWrY.kB5TAPFROUi', 
select person_id from persons p where p.person_last_name='Конев', 
true
),
(
'aksenov', 
'$2y$12$8Vzwr/y5iy3DfsXikJka4eJZsUcz5ZGZRK6ZmbWrY.kB5TAPFROUi', 
select person_id from persons p where p.person_last_name='Аксенов', 
true
),
(
'davidov', 
'$2y$12$8Vzwr/y5iy3DfsXikJka4eJZsUcz5ZGZRK6ZmbWrY.kB5TAPFROUi', 
select person_id from persons p where p.person_last_name='Давыдов', 
true
),
(
'oystrax', 
'$2y$12$8Vzwr/y5iy3DfsXikJka4eJZsUcz5ZGZRK6ZmbWrY.kB5TAPFROUi', 
select person_id from persons p where p.person_last_name='Ойстрах', 
true
),
(
'magition', 
'$2y$12$8Vzwr/y5iy3DfsXikJka4eJZsUcz5ZGZRK6ZmbWrY.kB5TAPFROUi', 
select person_id from persons p where p.person_last_name='Кудесник', 
true
);