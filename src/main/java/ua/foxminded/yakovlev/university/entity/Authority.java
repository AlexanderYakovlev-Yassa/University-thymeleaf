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
@Table (name="autorities")
public class Autority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "autority_id")
	private Long id;
	
	@Column(name = "autority_name")
	@NotBlank(message="validator.message.empty_autority_name")
	private String name;
}
