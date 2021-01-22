INSERT INTO timetable_record_groups
(
timetable_record_group_timetable_record_id,
timetable_record_group_group_id
)
VALUES
(
select timetable_record_id from timetable_records t where t.timetable_record_time='2020-10-16 09:00:00',
select group_id from groups g where g.group_name='aa-01'
),
(
select timetable_record_id from timetable_records t where t.timetable_record_time='2020-10-16 09:00:00',
select group_id from groups g where g.group_name='aa-02'
),
(
select timetable_record_id from timetable_records t where t.timetable_record_time='2020-10-16 10:00:00',
select group_id from groups g where g.group_name='aa-01'
);