package med.voll.api.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Table(name = "usuarios")
@Entity(name="Usuario")
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Usuario implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String login;
	private String password;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	@Override
	public String getUsername() {
		return login;
	}
	@Override
	public String getPassword(){
		return password;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true ;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Usuario usuario = (Usuario) o;
		return getId() != null && Objects.equals(getId(), usuario.getId());
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
