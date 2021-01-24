INSERT INTO lecturers(lecturer_person_id, lecturer_position_id)
VALUES
(
select person_id from persons p where p.person_first_name='Андрей' and p.person_last_name='Аксенов',
select position_id from positions ps where ps.position_name='INSTRUCTOR'
),
(
select person_id from persons p where p.person_first_name='Мирон' and p.person_last_name='Давыдов',
select position_id from positions ps where ps.position_name='INSTRUCTOR'
),
(
select person_id from persons p where p.person_first_name='Владимир' and p.person_last_name='Ойстрах',
select position_id from positions ps where ps.position_name='RESEARCH_ASSOCIATE'
),
(
select person_id from persons p where p.person_first_name='Cтанислав' and p.person_last_name='Кудесник',
select position_id from positions ps where ps.position_name='ASSOCIATE_PROFESSOR'
);