package ua.foxminded.yakovlev.university.entity;

import java.util.*;

public class Timetable {

	private List<TimetableRecord> timetable;

	public List<TimetableRecord> getTimetable() {
		return timetable;
	}

	public void setTimetable(List<TimetableRecord> timetable) {
		this.timetable = timetable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((timetable == null) ? 0 : timetable.hashCode());
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
		Timetable other = (Timetable) obj;
		if (timetable == null) {
			if (other.timetable != null) {
				return false;
			}
		} else if (!timetable.equals(other.timetable)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Timetable [timetable=");
		builder.append(timetable);
		builder.append("]");
		return builder.toString();
	}
}