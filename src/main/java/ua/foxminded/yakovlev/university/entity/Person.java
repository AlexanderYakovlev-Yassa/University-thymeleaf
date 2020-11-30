package ua.foxminded.yakovlev.university.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data 
@Entity
@Table (name="persons")
public class Person {

	@Id
    @Column(name = "person_id")
	private Long personId;
	
	@Column(name = "person_first_name, nullable=false, unique=false")
	private String firstName;
	
	@Column(name = "person_last_name, nullable=false, unique=false")
	private String lastName;
}
