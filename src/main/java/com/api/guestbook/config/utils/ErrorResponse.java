/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.guestbook.config.utils;

/**
 *
 * @author shriyanshisrivastava
 */
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

    private String statusCode;
    private String errorContent;
    private List<String> messages;

    public ErrorResponse(String statusCode, String errorContent, String messages) {
        this.statusCode = statusCode;
        this.errorContent = errorContent;
        this.messages = new ArrayList<>();
        this.messages.add(messages);
    }

    public ErrorResponse(String statusCode, String errorContent, List<String> messages) {
        this.statusCode = statusCode;
        this.errorContent = errorContent;
        this.messages = messages;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
