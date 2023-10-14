/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.guestbook.config.utils;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author shriyanshisrivastava
 */
public class Constants {

    public Constants() {
        // Do nothing Constructor

    }

    public static final String MESSAGE = "message";
    public static final String INVALID_ROLE = "Invalid role selected";
    public static final String STATUS = "status";

    public static final List<Integer> ROLE_ID = Arrays.asList(2, 3, 4, 5);
    // 2 - Admin ,  3- Manager , 4 - Customer

}
