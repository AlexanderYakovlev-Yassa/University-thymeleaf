package ua.foxminded.yakovlev.university.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timetable")
public class TimetableController {
	
	private final TimetableRecordService timetableRecordService;
	private final LecturerService lecturerService;
	private final GroupService groupService;
	private final CourseService courseService;
	
	@GetMapping()
    public String show(Model model) {
		model.addAttribute("timetableRecords", timetableRecordService.findAll());		
        return "timetable/show-timetable";
    }
	
	@GetMapping("/new")
    public String showSavePage(Model model) {
		
		model.addAttribute("lecturers", lecturerService.findAll());
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("courses", courseService.findAll());
		
		return "timetable/new-timetable-record";
	}
	
	@GetMapping("/detail")
    public String showDetailPage(Model model, @RequestParam(name = "record-id") long recordId) {		
		
		model.addAttribute("timetableRecord", timetableRecordService.findById(recordId));
		
		return "timetable/show-detail";
	}
	
	@PostMapping("/new")
    public String save(@RequestParam(name = "date") String dateTime,
    		@RequestParam(name = "lecturer-id") long lecturerId,
    		@RequestParam(name = "course-id") long courseId,
    		@RequestParam(name = "groups-id") String groupsId
    		) {
		
		TimetableRecord newTimetableRecord = new TimetableRecord();
		Lecturer lecturer = new Lecturer();
		lecturer.setPersonId(lecturerId);
		Course course = new Course();
		course.setId(courseId);		
		List<Group> groups = parseGroupList(groupsId);
		LocalDateTime date = LocalDateTime.parse(dateTime);
		newTimetableRecord.setLecturer(lecturer);
		newTimetableRecord.setCourse(course);
		newTimetableRecord.setGroupList(groups);
		newTimetableRecord.setDate(date);
		
		timetableRecordService.save(newTimetableRecord);
		
		return "redirect:/timetable";
	}
	
	@GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam(name = "record-id") long recordId) {
		
		model.addAttribute("timetableRecord", timetableRecordService.findById(recordId));
		
		model.addAttribute("lecturers", lecturerService.findAll());
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("courses", courseService.findAll());
		
		return "timetable/edit-timetable-record";
	}
	
	@PostMapping("/edit")
    public String edit(@RequestParam(name = "date") String dateTime,
    		@RequestParam(name = "lecturer-id") long lecturerId,
    		@RequestParam(name = "course-id") long courseId,
    		@RequestParam(name = "record-id") long id,
    		@RequestParam(name = "groups-id") String groupsId
    		) {
		
		TimetableRecord editedTimetableRecord = new TimetableRecord();
		editedTimetableRecord.setId(id);
		Lecturer lecturer = new Lecturer();
		lecturer.setPersonId(lecturerId);
		Course course = new Course();
		course.setId(courseId);		
		List<Group> groups = parseGroupList(groupsId);
		LocalDateTime date = LocalDateTime.parse(dateTime);
		editedTimetableRecord.setLecturer(lecturer);
		editedTimetableRecord.setCourse(course);
		editedTimetableRecord.setGroupList(groups);
		editedTimetableRecord.setDate(date);
		
		timetableRecordService.update(editedTimetableRecord);
		
		return "redirect:/timetable";
	}
	
	
	@PostMapping("/delete")
    public String delete(@RequestParam(name = "id") long id) {
		timetableRecordService.delete(id);
        return "redirect:/timetable";
    }
	
	private List<Group> parseGroupList(String groupsId) {
		
		List<Group> groupList = new ArrayList<>();
		for (String s : groupsId.split(",")) {
			Group group = new Group();
			group.setId(Long.parseLong(s));
			groupList.add(group);
		}
		
		return groupList;
	}
}