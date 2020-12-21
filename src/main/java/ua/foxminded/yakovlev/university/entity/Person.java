package ua.foxminded.yakovlev.university.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.persistence.InheritanceType;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table (name="persons")
public class Person implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
	private Long personId;
	
	@Column(name = "person_first_name")
	@NotBlank(message="validator.message.empty_person_first_name")
	private String firstName;
	
	@Column(name = "person_last_name")
	@NotBlank(message="validator.message.empty_person_last_name")
	private String lastName;
}
