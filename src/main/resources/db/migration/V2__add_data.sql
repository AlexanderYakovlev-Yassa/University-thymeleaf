INSERT INTO groups(group_name)	
VALUES 
('aa-01'),
('aa-02'),
('ab-03'),
('ab-04');

INSERT INTO persons(person_first_name, person_last_name)	
VALUES 
('Иван', 'Иванов'),
('Петр', 'Петров'),
('Сидор', 'Сидоров'),
('Егор', 'Федотов'),
('Максим', 'Конев'),
('Андрей', 'Аксенов'),
('Мирон', 'Давыдов'),
('Владимир', 'Ойстрах'),
('Cтанислав', 'Кудесник');

INSERT INTO courses(course_name, course_description)	
VALUES 
('Математика', 'Общий курс математики'),
('Физика', 'Общий курс физики'),
('Музыка', 'Основы музыкальной грамоты'),
('Биология', 'Общий курс биологии');

INSERT INTO students(student_person_id, student_group_id)
VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 2),
(5, 2);

INSERT INTO positions(position_name)
VALUES
('UNIVERSITY_PROFESSOR'),
('PROFESSOR'),
('ASSOCIATE_PROFESSOR'),
('ASSISTANT_PROFESSOR'),
('MASTER_INSTRUCTOR'),
('SENIOR_INSTRUCTOR'),
('INSTRUCTOR'),
('RESEARCH_ASSOCIATE'),
('ADJUNCT_PROFESSOR');

INSERT INTO lecturers(lecturer_person_id, lecturer_position_id)
VALUES
(6, 7),
(7, 7),
(8, 8),
(9, 3);

INSERT INTO timetable_records(timetable_record_time, timetable_record_lecturer_id, timetable_record_course_id)
VALUES
('2020-10-16 09:00:00', 6, 3),
('2020-10-16 10:00:00', 7, 2),
('2020-10-16 12:00:00', 8, 1);

INSERT INTO timetable_record_groups(timetable_record_group_timetable_record_id, timetable_record_group_group_id)
VALUES
(1, 1),
(1, 2),
(2, 1);