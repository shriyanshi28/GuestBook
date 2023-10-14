/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.guestbook.config.utils;

import com.api.guestbook.models.Entries;
import com.api.guestbook.models.Users;
import com.api.guestbook.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author shriyanshisrivastava
 */
@Component
public class HelperUtils {

    @Autowired
    UserDetailsRepository userDetailsService;

    public boolean isImageFile(MultipartFile file) {
        return file.getContentType() != null && (file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)
                || file.getContentType().equals(MediaType.IMAGE_PNG_VALUE)
                || file.getContentType().equals(MediaType.IMAGE_GIF_VALUE));
    }

    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean isEntryCreatedByUser(Authentication authentication, Entries entry) {
        String username = authentication.getName(); // Get the username from authentication
        Users user = userDetailsService.findByEmail(username);
        if (user != null) {
            return entry.getCreatedBy().equals(user); // Assuming there's a createdBy property in Entries.
        }
        return false;
    }
}
