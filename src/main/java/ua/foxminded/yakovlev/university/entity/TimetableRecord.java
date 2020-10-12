package ua.foxminded.yakovlev.university.entity;

import java.time.LocalDateTime;
import java.util.List;

public class TimetableRecord {

	private Long id;
	private LocalDateTime date;
	private Course course;
	private List<Group> groupList;
	private Lecturer lecturer;
	
    public TimetableRecord() {
    	super();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((groupList == null) ? 0 : groupList.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lecturer == null) ? 0 : lecturer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TimetableRecord other = (TimetableRecord) obj;
		if (course == null) {
			if (other.course != null) {
				return false;
			}
		} else if (!course.equals(other.course)) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (groupList == null) {
			if (other.groupList != null) {
				return false;
			}
		} else if (!groupList.equals(other.groupList)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lecturer == null) {
			if (other.lecturer != null) {
				return false;
			}
		} else if (!lecturer.equals(other.lecturer)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimetableRecord [id=");
		builder.append(id);
		builder.append(", date=");
		builder.append(date);
		builder.append(", course=");
		builder.append(course);
		builder.append(", groupList=");
		builder.append(groupList);
		builder.append(", lecturer=");
		builder.append(lecturer);
		builder.append("]");
		return builder.toString();
	}
}