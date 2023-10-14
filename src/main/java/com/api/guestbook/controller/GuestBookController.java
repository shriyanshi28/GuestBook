/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.guestbook.controller;

import com.api.guestbook.config.utils.EntityMapper;
import com.api.guestbook.config.utils.HelperUtils;
import com.api.guestbook.config.utils.ResponseHawk;
import com.api.guestbook.dto.EntriesDto;
import com.api.guestbook.models.Entries;
import com.api.guestbook.models.Users;
import com.api.guestbook.repository.EntriesRepository;
import com.api.guestbook.repository.UserDetailsRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author shriyanshisrivastava
 */
@RestController
@CrossOrigin
@RequestMapping("guestbook")
public class GuestBookController extends ResponseHawk {

    @Autowired
    EntriesRepository entriesRepository;
    @Autowired
    UserDetailsRepository userDetailsService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    HelperUtils helperUtils;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity addEntry(@Valid @RequestBody EntriesDto entriesDto, BindingResult errors) throws Exception {
        if (errors.hasErrors()) {
            List<String> error = errors.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return genericError(error.get(0));
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Users cmnUserDetails = userDetailsService.findByEmail(user.getUsername());

        Entries entries = new Entries();
        entries.setCreatedAt(new Date());
        entries.setImageName(entriesDto.getImage());
        entries.setDetails(entriesDto.getDetails());
        entries.setCreatedBy(cmnUserDetails);
        entriesRepository.save(entries);
        return genericSuccess("Posted successfully");
    }

    @RequestMapping(value = "/entries", method = RequestMethod.GET)
    public ResponseEntity getAllEntry() throws Exception {
        List<Entries> entries = entriesRepository.findAll();
        List rows = entries.stream().map(entry -> entityMapper.entryMapper(entry)).collect(Collectors.toList());
        return genericSuccess(rows);
    }

    @RequestMapping(value = "/deleteEntry/{id}", method = RequestMethod.GET)
    public ResponseEntity deleteEntry(@PathVariable Integer id, Authentication authentication) throws Exception {
        Entries entry = entriesRepository.findById(id).orElse(null);
        // Check if the entry exists
        if (entry == null) {
            return genericError("Entry not found with id: " + id);
        }

        // Check if the user is an admin or the creator of the entry
        if (helperUtils.isAdmin(authentication) || helperUtils.isEntryCreatedByUser(authentication, entry)) {
            entriesRepository.delete(entry);
            return genericSuccess("Entry deleted successfully");
        } else {
            return genericError("You do not have permission to delete this entry");
        }
    }

    @RequestMapping(value = "/editEntry/{id}", method = RequestMethod.POST)
    public ResponseEntity editEntry(@Valid @RequestBody EntriesDto entriesDto,
            @PathVariable Integer id,
            BindingResult errors,
            Authentication authentication) throws Exception {

        if (errors.hasErrors()) {
            List<String> error = errors.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return genericError(error.get(0));
        }

        Entries entry = entriesRepository.findById(id).orElse(null);
        // Check if the entry exists
        if (entry == null) {
            return genericError("Entry not found with id: " + id);
        }

        // Check if the user is an admin or the creator of the entry
        if (helperUtils.isAdmin(authentication) || helperUtils.isEntryCreatedByUser(authentication, entry)) {
            entry.setDetails(entriesDto.getDetails());
            entry.setImageName(entriesDto.getImage());
            entriesRepository.save(entry);
            return genericSuccess("Entry updated successfully");
        } else {
            return genericError("You do not have permission to delete this entry");
        }
    }
}
