INSERT INTO students(student_person_id, student_group_id)
VALUES
(
select person_id from persons p where p.person_first_name='Иван' and p.person_last_name='Иванов',
select group_id from groups g where g.group_name='aa-01'
),
(
select person_id from persons p where p.person_first_name='Петр' and p.person_last_name='Петров',
select group_id from groups g where g.group_name='aa-01'
),
(
select person_id from persons p where p.person_first_name='Сидор' and p.person_last_name='Сидоров',
select group_id from groups g where g.group_name='aa-01'
),
(
select person_id from persons p where p.person_first_name='Егор' and p.person_last_name='Федотов',
select group_id from groups g where g.group_name='aa-02'
),
(
select person_id from persons p where p.person_first_name='Максим' and p.person_last_name='Конев',
select group_id from groups g where g.group_name='aa-02'
);