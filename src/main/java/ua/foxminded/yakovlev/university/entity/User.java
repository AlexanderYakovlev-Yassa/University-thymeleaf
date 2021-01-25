package ua.foxminded.yakovlev.university.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data 
@Entity
@Table (name="users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
	private Long id;
	
	@Column(name = "user_username")
	@NotBlank(message="validator.message.empty_username")
	private String name;
	
	@Column(name = "user_password")
	@NotBlank(message="validator.message.empty_password")
	private String password;
	
	@Column(name = "user_enabled")
	@NotBlank(message="validator.message.empty_password")
	private Boolean enabled;
	
	@ManyToOne
    @JoinColumn(name="user_person_id")
	@NotBlank(message="validator.message.empty_password")
	private Person person;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_role_id")}
    )
	private Set<Role> roleSet;
}
