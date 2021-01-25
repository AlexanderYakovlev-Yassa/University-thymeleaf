INSERT INTO timetable_records(
timetable_record_time, 
timetable_record_lecturer_id, 
timetable_record_course_id
)
VALUES
(
'2020-10-16 09:00:00',
select person_id from persons p where p.person_first_name='Андрей' and p.person_last_name='Аксенов',
select course_id from courses c where c.course_name='Музыка' 
),
(
'2020-10-16 10:00:00',
select person_id from persons p where p.person_first_name='Владимир' and p.person_last_name='Ойстрах',
select course_id from courses c where c.course_name='Физика'
),
(
'2020-10-16 12:00:00',
select person_id from persons p where p.person_first_name='Cтанислав' and p.person_last_name='Кудесник',
select course_id from courses c where c.course_name='Математика'
),(
'2020-10-17 09:00:00',
select person_id from persons p where p.person_first_name='Андрей' and p.person_last_name='Аксенов',
select course_id from courses c where c.course_name='Музыка' 
),
(
'2020-10-17 10:00:00',
select person_id from persons p where p.person_first_name='Владимир' and p.person_last_name='Ойстрах',
select course_id from courses c where c.course_name='Физика'
),
(
'2020-10-17 12:00:00',
select person_id from persons p where p.person_first_name='Cтанислав' and p.person_last_name='Кудесник',
select course_id from courses c where c.course_name='Математика'
),(
'2020-10-18 09:00:00',
select person_id from persons p where p.person_first_name='Андрей' and p.person_last_name='Аксенов',
select course_id from courses c where c.course_name='Музыка' 
),
(
'2020-10-18 10:00:00',
select person_id from persons p where p.person_first_name='Владимир' and p.person_last_name='Ойстрах',
select course_id from courses c where c.course_name='Физика'
),
(
'2020-10-18 12:00:00',
select person_id from persons p where p.person_first_name='Cтанислав' and p.person_last_name='Кудесник',
select course_id from courses c where c.course_name='Математика'
);