package ua.foxminded.yakovlev.university.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.Position;
import ua.foxminded.yakovlev.university.entity.Student;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;

public class PersistedEntityCache {

	private Map<String, Course> courseCache;
	private Map<String, Group> groupCache;
	private Map<String, Position> positionCache;
	private Map<String, Student> studentCache;
	private Map<String, Lecturer> lecturerCache;
	private Map<String, TimetableRecord> timetableRecordCache;
	
	TestEntityManager em;
	
	public PersistedEntityCache(TestEntityManager em) {
		courseCache = new HashMap<>();
		groupCache = new HashMap<>();
		positionCache = new HashMap<>();
		studentCache = new HashMap<>();
		lecturerCache = new HashMap<>();
		timetableRecordCache = new HashMap<>();
		this.em = em;		
	}
	
	public Course persistCourse(Course course) {
		
		String key = course.getName();
		
		if (courseCache.containsKey(key)) {
			return courseCache.get(key);
		}
		
		course.setId(null);
		course = em.persist(course);
		courseCache.put(key, course);
		
		return course;
	}
	
	public Group persistGroup(Group group) {
		
		String key = group.getName();
		
		if (groupCache.containsKey(key)) {
			return groupCache.get(key);
		}
		
		group.setId(null);
		group = em.persist(group);
		groupCache.put(key, group);
		
		return group;
	}
	
	public Position persistPosition(Position position) {
		
		String key = position.getName();
		
		if (positionCache.containsKey(key)) {			
			return positionCache.get(key);
		}
		
		position.setId(null);
		position = em.persist(position);
		positionCache.put(key, position);
		
		return position;
	}
	
	public Student persistStudent(Student student) {
		
		String key = student.getFirstName() + student.getLastName();
		
		if (studentCache.containsKey(key)) {
			return studentCache.get(key);
		}
		
		student.setGroup(persistGroup(student.getGroup()));
		student.setPersonId(null);
		student = em.persist(student);
		studentCache.put(key, student);
		
		return student;
	}
	
	public Lecturer persistLecturer(Lecturer lecturer) {
		
		String key = lecturer.getFirstName() + lecturer.getLastName();
		
		if (lecturerCache.containsKey(key)) {
			return lecturerCache.get(key);
		}		
		
		lecturer.setPosition(persistPosition(lecturer.getPosition()));
		
		lecturer.setPersonId(null);
		lecturer = em.persist(lecturer);		
		lecturerCache.put(key, lecturer);

		return lecturer;
	}
	
	public TimetableRecord persistTimetableRecord(TimetableRecord record) {
		
		String key = record.getDate().toString()
				+ record.getLecturer().getFirstName()
				+ record.getLecturer().getLastName()
				+ record.getCourse().getName();
		
		if (timetableRecordCache.containsKey(key)) {
			return timetableRecordCache.get(key);
		}
		
		if (record.getGroupList() != null) {
			for (int i = 0; i < record.getGroupList().size(); i++) {
				record.getGroupList().set(i, persistGroup(record.getGroupList().get(i)));
			}
		}
		
		record.setLecturer(persistLecturer(record.getLecturer()));
		record.setCourse(persistCourse(record.getCourse()));
		
		record.setId(null);		
		record = em.persist(record);		
		timetableRecordCache.put(key, record);

		return record;
	}
	
	public void persistCourseList(List<Course> timetableRecordList) {		
		for (int i = 0; i < timetableRecordList.size(); i++) {
			timetableRecordList.set(i, persistCourse(timetableRecordList.get(i)));
		}
	}
	
	public void persistGroupList(List<Group> groupList) {		
		for (int i = 0; i < groupList.size(); i++) {
			groupList.set(i, persistGroup(groupList.get(i)));
		}
	}
	
	public void persistPositionList(List<Position> positionList) {		
		for (int i = 0; i < positionList.size(); i++) {
			positionList.set(i, persistPosition(positionList.get(i)));
		}
	}
	
	public void persistStudentList(List<Student> studentList) {		
		for (int i = 0; i < studentList.size(); i++) {
			studentList.set(i, persistStudent(studentList.get(i)));
		}
	}
	
	public void persistLecturerList(List<Lecturer> lecturerList) {		
		for (int i = 0; i < lecturerList.size(); i++) {
			lecturerList.set(i, persistLecturer(lecturerList.get(i)));
		}
	}
	
	public void persistTimetableRecordList(List<TimetableRecord> timetableRecordList) {		
		for (int i = 0; i < timetableRecordList.size(); i++) {
			timetableRecordList.set(i, persistTimetableRecord(timetableRecordList.get(i)));
		}
	}
}