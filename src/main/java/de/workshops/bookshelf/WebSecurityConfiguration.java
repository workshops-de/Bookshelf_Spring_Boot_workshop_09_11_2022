package de.workshops.bookshelf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfiguration {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize ->
                                authorize
                                        .antMatchers("/h2-console/**").permitAll()
                                        .antMatchers(
                                                "/swagger-ui.html",
                                                "/swagger-ui/**",
                                                "/v3/api-docs/**",
                                                "/webjars/swagger-ui/**"
                                        ).permitAll()
                                        .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .headers().frameOptions().disable().and()
                .csrf().disable()
                .build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                String sql = "SELECT * FROM bookshelf_user WHERE username = ?";

                return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        Collections.singletonList(
                                new SimpleGrantedAuthority(rs.getString("role"))
                        )
                ), username);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
