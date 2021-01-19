package ua.foxminded.yakovlev.university.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;

public class TimetableGenerator {
	
	private static final String YAML_FILE_PATH = "src/test/resources/entity/timetable.yaml";

	public TimetableRecord getTimetableRecord(Long id, LocalDateTime date, Lecturer lecturer, Course course, List<Group> groupList) {
		
		TimetableRecord timetableRecord = new TimetableRecord();
		timetableRecord.setId(id);
		timetableRecord.setDate(date);
		timetableRecord.setLecturer(lecturer);
		timetableRecord.setCourse(course);
		timetableRecord.setGroupList(groupList);
		
		return timetableRecord;
	}
	
	public List<TimetableRecord> getTimetable() {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
				.registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule())
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		List<TimetableRecord> timetableRecordList;
		
		try {
			timetableRecordList = mapper.readValue(new File(YAML_FILE_PATH), new TypeReference<List<TimetableRecord>>(){});
		} catch (IOException e) {
			throw new IllegalArgumentException("Bad yaml file " + YAML_FILE_PATH);
		}

		return timetableRecordList;
	}
}