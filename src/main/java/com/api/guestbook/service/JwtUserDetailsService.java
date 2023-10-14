/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.guestbook.service;

/**
 *
 * @author shriyanshisrivastava
 */
import com.api.guestbook.models.Users;
import com.api.guestbook.repository.UserDetailsRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users CmnUserDetails = userDetailsService.findByEmail(username);
        return new User(CmnUserDetails.getEmail(), CmnUserDetails.getPassword(),
                getAuthority(CmnUserDetails));

    }

    private Set<SimpleGrantedAuthority> getAuthority(Users user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoleMappingList().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleId().getRole()));
        });
        return authorities;
    }

}
