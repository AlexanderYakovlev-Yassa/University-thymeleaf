DROP TABLE IF EXISTS timetable_record_groups;
DROP TABLE IF EXISTS timetable_records;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS lecturers;
DROP TABLE IF EXISTS positions;
DROP TABLE IF EXISTS persons;

CREATE TABLE public.courses
(
    course_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    course_name character varying,
    course_description character varying,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id),
    CONSTRAINT course_name_unique UNIQUE (course_name)
);

CREATE TABLE public.groups
(
    group_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    group_name character varying,
    CONSTRAINT pk_group_id PRIMARY KEY (group_id),
    CONSTRAINT group_name_unique UNIQUE (group_name)
);

CREATE TABLE public.positions
(
    position_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    position_name character varying,
    CONSTRAINT positions_pkey PRIMARY KEY (position_id),
    CONSTRAINT position_name_unique UNIQUE (position_name)
);

CREATE TABLE public.persons
(
    person_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    person_first_name character varying,
    person_last_name character varying,
    CONSTRAINT persons_pkey PRIMARY KEY (person_id)
);

CREATE TABLE public.lecturers
(
    lecturer_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    lecturer_person_id bigint NOT NULL,
    lecturer_position_id bigint,
    CONSTRAINT lecturers_pkey PRIMARY KEY (lecturer_id),
    CONSTRAINT lecturer_person_id_fkey FOREIGN KEY (lecturer_person_id)
        REFERENCES public.persons (person_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT lecturer_position_id_fkey FOREIGN KEY (lecturer_position_id)
        REFERENCES public.positions (position_id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.students
(
    student_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    student_group_id bigint,
    student_person_id bigint,
    CONSTRAINT students_pkey PRIMARY KEY (student_id),
    CONSTRAINT student_group_id_fkey FOREIGN KEY (student_group_id)
        REFERENCES public.groups (group_id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT student_person_id_fkey FOREIGN KEY (student_person_id)
        REFERENCES public.persons (person_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE public.timetable_records
(
    timetable_record_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    timetable_record_time timestamp without time zone NOT NULL,
    timetable_record_lecturer_id bigint NOT NULL,
    timetable_record_course_id bigint NOT NULL,
    CONSTRAINT timetable_records_pkey PRIMARY KEY (timetable_record_id),
    CONSTRAINT time_and_lecturer_unique UNIQUE (timetable_record_time, timetable_record_lecturer_id),
    CONSTRAINT timetable_record_course_id_fkey FOREIGN KEY (timetable_record_course_id)
        REFERENCES public.courses (course_id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT timetable_record_lecturer_id_fkey FOREIGN KEY (timetable_record_lecturer_id)
        REFERENCES public.persons (person_id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.timetable_record_groups
(
    timetable_record_group_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    timetable_record_group_timetable_record_id bigint NOT NULL,
    timetable_record_group_group_id bigint NOT NULL,
    CONSTRAINT timetable_record_groups_pkey PRIMARY KEY (timetable_record_group_id),
    CONSTRAINT timetable_and_group_unique UNIQUE (timetable_record_group_timetable_record_id, timetable_record_group_group_id),
    CONSTRAINT timetable_record_group_timetable_record_id_fkey FOREIGN KEY (timetable_record_group_timetable_record_id)
        REFERENCES public.timetable_records (timetable_record_id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT timetable_record_groups_group_id_fkey FOREIGN KEY (timetable_record_group_group_id)
        REFERENCES public.groups (group_id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

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