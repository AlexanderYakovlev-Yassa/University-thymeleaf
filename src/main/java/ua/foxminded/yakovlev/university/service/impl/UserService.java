package ua.foxminded.yakovlev.university.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ua.foxminded.yakovlev.university.entity.Authority;
import ua.foxminded.yakovlev.university.entity.Role;
import ua.foxminded.yakovlev.university.entity.User;
import ua.foxminded.yakovlev.university.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
	
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = findByUsername(username);
				
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthorities(user.getRoleSet()));
	}
	
	public User findByUsername(String username) {
		
		User user = userRepository.findByName(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " is not found");
		}
		
		return user;
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
		
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		for (Role r : roles) {
			for (Authority a : r.getAuthorities()) {
				authorities.add(new SimpleGrantedAuthority(a.getName()));
			}
		}
		
		return authorities;
	}
}
