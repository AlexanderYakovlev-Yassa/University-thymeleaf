package ua.foxminded.yakovlev.university.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data 
@Entity
@Table (name="positions")
public class Position {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
	private Long id;
	
	@Column(name = "position_name")
	@NotBlank(message="validator.message.empty_position_name")
	private String name;
}
