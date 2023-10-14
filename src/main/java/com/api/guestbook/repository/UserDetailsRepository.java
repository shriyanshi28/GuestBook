/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.guestbook.repository;

import com.api.guestbook.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author shriyanshisrivastava
 */
public interface UserDetailsRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);

    Boolean existsByEmail(String email);

}
