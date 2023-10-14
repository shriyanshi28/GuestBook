/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.guestbook.config.utils;

import com.api.guestbook.models.Entries;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author shriyanshisrivastava
 */
@Component
public class EntityMapper {

    public Map<String, Object> entryMapper(Entries entry) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", entry.getId());
        map.put("created_at", entry.getCreatedAt());
        map.put("created_by", entry.getCreatedBy().getName());
        map.put("details", entry.getDetails());
        map.put("image", entry.getImageName());
        return map;
    }

}
