package ua.foxminded.yakovlev.university.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data 
@Entity
@Table (name="groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
	private Long id;
	
	@Column(name = "group_name")
	@NotBlank(message="validator.message.empty_group_name")
	@Pattern(regexp="^[a-z]{2}[-][0-9]{2}$", 
			message="validator.message.name_pattern_not_matches")
	private String name;
}
