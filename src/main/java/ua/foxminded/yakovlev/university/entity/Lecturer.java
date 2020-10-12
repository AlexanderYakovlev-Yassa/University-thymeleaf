package ua.foxminded.yakovlev.university.entity;

public class Lecturer extends Person {

	private Position position;
	
    public Lecturer() {
    	super();
    }

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Lecturer other = (Lecturer) obj;
		if (position != other.position) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Lecturer [position=");
		builder.append(position);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getFirstName()=");
		builder.append(getFirstName());
		builder.append(", getLastName()=");
		builder.append(getLastName());
		builder.append("]");
		return builder.toString();
	}
}