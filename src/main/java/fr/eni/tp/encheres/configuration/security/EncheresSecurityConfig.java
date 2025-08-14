package fr.eni.tp.encheres.configuration.security;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class EncheresSecurityConfig {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("select pseudo, mot_de_passe, 1 from UTILISATEURS where pseudo=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select u.pseudo, r.role FROM UTILISATEURS u INNER JOIN roles r"
											+" ON r.is_admin = u.administrateur WHERE u.pseudo = ?");
		
		return jdbcUserDetailsManager;
	}
	
	
	//Restriction des URLs seln la connexion utilisateur et leurs rôles
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/", "/css/**", "/images/**", "/js/**").permitAll()
			.requestMatchers("/index").permitAll()
			.requestMatchers(HttpMethod.GET, "/profil/creer").permitAll()
			.requestMatchers(HttpMethod.POST, "/profil/creer").permitAll()
			.requestMatchers("/profil").authenticated()
			.requestMatchers("/vendre").authenticated()
			.requestMatchers("/vendre/photo").authenticated()
			.requestMatchers(HttpMethod.GET, "/encheres").permitAll()
			.requestMatchers(HttpMethod.GET, "/encheres/list").permitAll()
			.anyRequest().authenticated());
		
	
		//Customise le formulaire
		http.formLogin(form -> {
			form.loginPage("/login").permitAll();
			form.defaultSuccessUrl("/session", true).permitAll();
		});
		
		//Logout --> vider la sesion et le contexte de sécurité
		http.logout(logout -> logout
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll());
		
		return http.build();
	}
	
	

}