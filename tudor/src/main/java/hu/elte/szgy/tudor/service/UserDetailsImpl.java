package hu.elte.szgy.tudor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hu.elte.szgy.tudor.model.User;
import hu.elte.szgy.tudor.model.User.UserType;


public class UserDetailsImpl extends User implements UserDetails {
	private static final long serialVersionUID = 1L;
	private User user;
	private List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>(5);
	
	public UserDetailsImpl(User user) {
		this.user = user;
		
		//assign role from usertype field: ROLE_UGYFEL, ROLE_TUDOR, etc.
		auths.add(new SimpleGrantedAuthority("ROLE_" + user.getType().name()));
		
		
		/*if (user.getType() != UserType.UGYFEL) {
			auths.add(new SimpleGrantedAuthority("ROLE_DOLGOZO"));
		}*/
	}
	
	public java.util.Collection<? extends GrantedAuthority> getAuthorities() { return auths; }
	public java.lang.String getUsername() { return user.getUsername(); }
	public java.lang.String getPassword() { return user.getPassword(); }
	public int getTudorId() { return user.getUserid(); }

	public boolean isEnabled() { return true; }
	public boolean isCredentialsNonExpired() { return true; }
	public boolean isAccountNonExpired() { return true; }
	public boolean isAccountNonLocked() { return true; }
	
	
	
}
