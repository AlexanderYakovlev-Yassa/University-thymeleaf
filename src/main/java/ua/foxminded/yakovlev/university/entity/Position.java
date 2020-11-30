package ua.foxminded.yakovlev.university.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data 
@Entity
@Table (name="positions")
public class Position {

	@Id
    @Column(name = "position_id")
	private Long id;
	
	@Column(name = "position_name, nullable=false, unique=true")
	private String name;
}
