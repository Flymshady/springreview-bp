package cz.cellar.springreview.config;

import cz.cellar.springreview.repository.PersonRepository;
import cz.cellar.springreview.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
@EnableJpaRepositories(basePackageClasses = PersonRepository.class)

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private CustomUserDetailsService userDetailsService;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService){
        this.userDetailsService= customUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)  {

        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("**/admin/**", "/admin/**", "/api/items/admin/**").hasRole("ADMIN")
                .antMatchers("/api/reviews/create/**", "/api/reviews/update/**", "/api/reviews/remove/**").authenticated()
                .antMatchers("**/reviews/update/**", "**/reviews/create").authenticated()
                .anyRequest().permitAll()
                .and().formLogin().permitAll()
        .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");


    }

  @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        return daoAuthenticationProvider;
  }

  @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
  }
}


