package ua.foxminded.yakovlev.university.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private String name;
}
