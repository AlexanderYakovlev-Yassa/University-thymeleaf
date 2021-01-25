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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data 
@Entity
@Table (name="roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
	private Long id;
	
	@Column(name = "role_name")
	@NotBlank(message="validator.message.empty_role_name")
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_autorities",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "autority_id")}
    )
	private Set<Autority> autorities;
}
