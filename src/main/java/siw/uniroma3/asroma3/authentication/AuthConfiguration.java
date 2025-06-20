package siw.uniroma3.asroma3.authentication;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import siw.uniroma3.asroma3.model.Credentials;


@Configuration
@EnableWebSecurity
public class AuthConfiguration {


	@Autowired
	private DataSource dataSource;
	
	@Autowired
	  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.jdbcAuthentication()
	    .dataSource(dataSource)
	    .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
	    .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	  }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity)
	throws Exception{
		return httpSecurity
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize // Lambda starts here
                        // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
                        .requestMatchers(HttpMethod.GET, "/", "/index", "/register", "/css/**", "/images/**", "favicon.ico","/associazione/**","/prenota/**").permitAll()
                        // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register
                        .requestMatchers(HttpMethod.POST, "/register", "/login","/prenota/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
                        // tutti gli utenti autenticati possono accere alle pagine rimanenti
                        .anyRequest().authenticated()
                ) // Lambda ends here
                // LOGIN: qui definiamo il login
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                )
                // LOGOUT: qui definiamo il logout
                .logout(logout -> logout
                        // il logout è attivato con una richiesta GET a "/logout"
                        .logoutUrl("/logout")
                        // in caso di successo, si viene reindirizzati alla home
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .clearAuthentication(true).permitAll()
                )
                .build();


	}
}
                		

