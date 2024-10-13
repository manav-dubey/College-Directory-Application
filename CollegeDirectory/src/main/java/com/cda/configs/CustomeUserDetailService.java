package com.cda.configs;

import com.cda.entities.Roles;
import com.cda.entities.Users;
import com.cda.repositries.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class CustomeUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomeUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Users user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + usernameOrEmail));

        return new CustomeUserDetails(user.getId(), user.getEmail(), user.getPassword(), mapRolesToAuthorities(user));
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Users user) {
//        return roles.stream().map(role -> new
//                SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(); // use list if you wish
        for (String role : user.getRole().getRoleName().toString().split(",")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return grantedAuthorities;
    }



}
