package cz.cellar.springreview.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Výpis 21
//Třída implementující UserDetails rozhraní
//Součást zabezpečení
public class CustomUserDetails implements UserDetails {

    private Person person;

    public CustomUserDetails(Person person){
        this.person=person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+this.person.getRole().getName());
        authorities.add(authority);
        return authorities;

    }
    public Long getId(){
        return this.person.getId();
    }

    public String getRoleString(){
        return this.person.getRole().getName();
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
