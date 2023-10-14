/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.guestbook.dto;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author shriyanshisrivastava
 */
public class EntriesDto {

    @NotBlank(message = "Details is mandatory")
    String details;
    String image;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
