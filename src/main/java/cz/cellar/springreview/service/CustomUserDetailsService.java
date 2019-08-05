package cz.cellar.springreview.service;

import cz.cellar.springreview.model.CustomUserDetails;
import cz.cellar.springreview.model.Person;
import cz.cellar.springreview.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private PersonRepository personRepository;
    @Autowired
    public CustomUserDetailsService(PersonRepository personRepository){
        this.personRepository=personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = personRepository.findByUsername(username);
        optionalPerson
                .orElseThrow(()->new UsernameNotFoundException("Username not found"));
        return optionalPerson
                .map(person ->
                    new CustomUserDetails(person)
                ).get();
    }
}
