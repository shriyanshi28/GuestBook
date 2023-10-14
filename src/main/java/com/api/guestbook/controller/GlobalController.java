/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.guestbook.controller;

/**
 *
 * @author shriyanshisrivastava
 */
import com.api.guestbook.config.utils.HelperUtils;
import com.api.guestbook.config.utils.JwtTokenUtil;
import com.api.guestbook.config.utils.ResponseHawk;
import com.api.guestbook.dto.LoginDTO;
import com.api.guestbook.dto.UserDto;
import com.api.guestbook.models.RoleMapping;
import com.api.guestbook.models.Roles;
import com.api.guestbook.models.Users;
import com.api.guestbook.repository.UserDetailsRepository;
import com.api.guestbook.repository.UserRoleMappingRepository;
import com.api.guestbook.service.JwtUserDetailsService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("public")
public class GlobalController extends ResponseHawk {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private HelperUtils helperUtils;
    @Autowired
    UserDetailsRepository userDetailsService;
    @Autowired
    UserRoleMappingRepository roleMappingRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity createAuthenticationToken(@RequestBody LoginDTO loginUser) throws Exception {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );

        final String token = jwtTokenUtil.generateToken(authentication);
        return genericSuccess(token);
    }

    @PostMapping({"/registration"})
    public ResponseEntity registration(@Valid @RequestBody UserDto userDTO, BindingResult errors) {

        if (errors.hasErrors()) {
            List<String> error = errors.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return genericError(error.get(0));
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        if (userDetailsService.existsByEmail(userDTO.getEmail())) {
            return genericError("Email already registered.");
        }

        Users userDetails = new Users();
        userDetails.setEmail(userDTO.getEmail());
        userDetails.setName(userDTO.getName());
        userDetails.setPassword(encodedPassword);
        userDetails.setCreatedAt(new Date());
        userDetailsService.save(userDetails);

        RoleMapping roleMapping = new RoleMapping();
        roleMapping.setRoleId(new Roles(userDTO.getRole()));
        roleMapping.setUserId(userDetails);
        roleMappingRepository.save(roleMapping);

        return genericSuccess("User Registered");
    }
}
