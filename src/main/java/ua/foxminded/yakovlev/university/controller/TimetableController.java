package ua.foxminded.yakovlev.university.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Course;
import ua.foxminded.yakovlev.university.entity.Group;
import ua.foxminded.yakovlev.university.entity.Lecturer;
import ua.foxminded.yakovlev.university.entity.TimetableRecord;
import ua.foxminded.yakovlev.university.entity.User;
import ua.foxminded.yakovlev.university.mapper.CourseMapper;
import ua.foxminded.yakovlev.university.mapper.GroupMapper;
import ua.foxminded.yakovlev.university.mapper.LecturerMapper;
import ua.foxminded.yakovlev.university.service.CourseService;
import ua.foxminded.yakovlev.university.service.GroupService;
import ua.foxminded.yakovlev.university.service.LecturerService;
import ua.foxminded.yakovlev.university.service.TimetableRecordService;
import ua.foxminded.yakovlev.university.service.impl.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timetable")
public class TimetableController {
	
	private final TimetableRecordService timetableRecordService;
	private final LecturerService lecturerService;
	private final LecturerMapper lecturerMapper;
	private final GroupService groupService;
	private final GroupMapper groupMapper;
	private final CourseService courseService;	
	private final CourseMapper courseMapper;
	private final UserService userService;
	@Qualifier(value="messageSource")
	private final ResourceBundleMessageSource messageSource;
	private final Validator validator;
	
	@GetMapping()
	@PreAuthorize("hasAuthority('READ_TIMETABLE')")
    public String show(Model model) {
		model.addAttribute("timetableRecords", timetableRecordService.findAll());		
        return "timetable/show-timetable";
    }
	
	@GetMapping("/new")
	@PreAuthorize("hasAuthority('MANAGE_TIMETABLE')")
    public String showSavePage(
    		@RequestParam(name = "date", required = false) LocalDateTime date,
    		@RequestParam(name = "courseId", required = false) Long courseId,
    		@RequestParam(name = "lecturerId", required = false) Long lecturerId,
    		@RequestParam(name = "groupsId", required = false) String groups,
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			Model model) {
		
		model.addAttribute("lecturers", lecturerMapper.toLecturerDtos(lecturerService.findAll()));
		model.addAttribute("groups", groupMapper.toGroupDtos(groupService.findAll()));
		model.addAttribute("courses", courseMapper.toCourseDtos(courseService.findAll()));		
		
		TimetableRecord record = new TimetableRecord();
		
		record.setDate(date != null ? date : LocalDateTime.now());
		record.setCourse(courseId != null ? courseService.findById(courseId) : new Course());
		record.setLecturer(lecturerId != null ? lecturerService.findById(lecturerId) : new Lecturer());
		record.setGroupList(groups != null ? parseGroupList(groups) : null);
		
		model.addAttribute("newRecord", record);
		model.addAttribute("groupsId", groups);		
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "timetable/new-timetable-record";
	}
	
	@PostMapping("/new")
	@PreAuthorize("hasAuthority('MANAGE_TIMETABLE')")
    public String save(
			RedirectAttributes redirectAttributes,
			@ModelAttribute("newRecord") TimetableRecord record,
			@RequestParam("groupsId") String groups
    		) {
				
		TimetableRecord newRecord = collectRecord(record, groups);
		
		Set<ConstraintViolation<TimetableRecord>> violations = validator.validate(newRecord);	
		
		if (violations.size() != 0) {
			
			List<String> errorMessageList = violations.stream().
					map(e -> (messageSource.getMessage(e.getMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			
			redirectAttributes.addAttribute("date", newRecord.getDate() != null ? newRecord.getDate() : null);
			redirectAttributes.addAttribute("courseId", newRecord.getCourse() != null ? newRecord.getCourse().getId() : null);
			redirectAttributes.addAttribute("lecturerId", newRecord.getLecturer() != null ? newRecord.getLecturer().getPersonId() : null);			
			redirectAttributes.addAttribute("groupsId", groups);
			
			return "redirect:/timetable/new";
		}
						
		timetableRecordService.save(newRecord);
		
		return "redirect:/timetable";
	}
	
	@GetMapping("/detail")
	@PreAuthorize("hasAuthority('READ_TIMETABLE')")
    public String showDetailPage(Model model, @RequestParam(name = "recordId") Long recordId) {		
		
		model.addAttribute("timetableRecord", timetableRecordService.findById(recordId));
		
		return "timetable/show-detail";
	}
	
	@GetMapping("/edit")
	@PreAuthorize("hasAuthority('MODIFY_TIMETABLE')")
    public String showEditPage(
    		@RequestParam(name = "recordId", required = false) Long recordId,
    		@RequestParam(name = "date", required = false) LocalDateTime date,
    		@RequestParam(name = "courseId", required = false) Long courseId,
    		@RequestParam(name = "lecturerId", required = false) Long lecturerId,
    		@RequestParam(name = "groupsId", required = false) String groups,
			@RequestParam(name = "errorMessage", required = false) List<String> errorMessageList,
			Model model) {
		
		model.addAttribute("lecturers", lecturerMapper.toLecturerDtos(lecturerService.findAll()));
		model.addAttribute("groups", groupMapper.toGroupDtos(groupService.findAll()));
		model.addAttribute("courses", courseMapper.toCourseDtos(courseService.findAll()));
		
		TimetableRecord record = timetableRecordService.findById(recordId);
		
		record.setDate(date != null ? date : record.getDate());
		record.setCourse(courseId != null ? courseService.findById(courseId) : record.getCourse());
		record.setLecturer(lecturerId != null ? lecturerService.findById(lecturerId) : record.getLecturer());
		record.setGroupList(groups != null ? parseGroupList(groups) : record.getGroupList());
		
		model.addAttribute("newRecord", record);
		model.addAttribute("groupsId", groups);		
		model.addAttribute("errorMessageList", errorMessageList);
		
		return "timetable/edit-timetable-record";
	}
	
	@PostMapping("/edit")
	@PreAuthorize("hasAuthority('MODIFY_TIMETABLE')")
    public String edit(
			RedirectAttributes redirectAttributes,
			@ModelAttribute("newRecord") TimetableRecord record,
			@RequestParam("groupsId") String groups
    		) {
				
		TimetableRecord newRecord = collectRecord(record, groups);
		
		Set<ConstraintViolation<TimetableRecord>> violations = validator.validate(newRecord);	
		
		if (violations.size() != 0) {
			
			List<String> errorMessageList = violations.stream().
					map(e -> (messageSource.getMessage(e.getMessage(), null, Locale.getDefault())))
					.collect(Collectors.toList());
			
			redirectAttributes.addAttribute("errorMessage", errorMessageList);
			
			redirectAttributes.addAttribute("date", newRecord.getDate() != null ? newRecord.getDate() : null);
			redirectAttributes.addAttribute("courseId", newRecord.getCourse() != null ? newRecord.getCourse().getId() : null);
			redirectAttributes.addAttribute("lecturerId", newRecord.getLecturer() != null ? newRecord.getLecturer().getPersonId() : null);			
			redirectAttributes.addAttribute("groupsId", groups);
			
			return "redirect:/timetable/new";
		}
						
		timetableRecordService.update(newRecord);
		
		return "redirect:/timetable";
	}	
	
	@PostMapping("/delete")
	@PreAuthorize("hasAuthority('MANAGE_TIMETABLE')")
    public String delete(@RequestParam(name = "id") long id) {
		timetableRecordService.delete(id);
        return "redirect:/timetable";
    }
	
	@GetMapping("/personal")
	@PreAuthorize("hasAuthority('READ_TIMETABLE')")
    public String showPersonalTimetable(
    		@RequestParam(name="start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, 
    		@RequestParam(name="numberOfDays", required = false) Long numberOfDays, 
    		Principal principal, 
    		Model model) {
		
		if (principal == null) {
			throw new IllegalArgumentException("Undefined user");
		}
		
		User activeUser = userService.findByUsername(principal.getName());
		
		model.addAttribute("timetableRecords", timetableRecordService.findByUser(activeUser, start, numberOfDays));
		model.addAttribute("startDate", start);
		model.addAttribute("numberOfDays", numberOfDays);
		
        return "timetable/show-personal-timetable";
    }
	
	private TimetableRecord collectRecord(TimetableRecord record, String groups) {
		
		record.setCourse(record.getCourse().getId() != null ? courseService.findById(record.getCourse().getId()) : null);
		record.setLecturer(record.getLecturer().getPersonId() != null ? lecturerService.findById(record.getLecturer().getPersonId()) : null);
		
		List<Group> groupList = parseGroupList(groups);
		
		record.setGroupList(groupList.size() != 0 ? groupList : null);
		
		return record;
	}
	
	private List<Group> parseGroupList(String groupsId) {
		
		List<Group> groupList = new ArrayList<>();
		
		if (groupsId.isEmpty()) {
			return groupList;
		}
		
		for (String s : groupsId.split(",")) {
			Group group = groupService.findById(Long.parseLong(s));
			groupList.add(group);
		}
		
		return groupList;
	}
}