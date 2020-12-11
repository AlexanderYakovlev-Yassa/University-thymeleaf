package ua.foxminded.yakovlev.university.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data 
@Entity
@Table (name="timetable_records")
public class TimetableRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timetable_record_id")
	private Long id;
	
	@Column(name = "timetable_record_time")
	private LocalDateTime date;
	
	@ManyToOne
    @JoinColumn(name="timetable_record_course_id")    
	private Course course;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "timetable_record_groups",
            joinColumns = {@JoinColumn(name = "timetable_record_group_timetable_record_id")},
            inverseJoinColumns = {@JoinColumn(name = "timetable_record_group_group_id")}
    )
	private List<Group> groupList;
		
	@ManyToOne
    @JoinColumn(name="timetable_record_lecturer_id")
	private Lecturer lecturer;
}