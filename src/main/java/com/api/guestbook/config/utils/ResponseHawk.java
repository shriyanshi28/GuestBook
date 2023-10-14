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
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public class ResponseHawk {

    public ResponseEntity genericSuccess(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.STATUS, true);
        map.put("data", data);
        return ResponseEntity.ok(map);
    }

    public ResponseEntity genericError(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.STATUS, false);
        map.put("data", data);
        return ResponseEntity.ok(map);
    }

}
