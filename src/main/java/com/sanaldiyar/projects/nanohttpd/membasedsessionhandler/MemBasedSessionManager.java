/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanaldiyar.projects.nanohttpd.membasedsessionhandler;

import com.sanaldiyar.projects.nanohttpd.nanohttpd.NanoSessionManager;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author kazim
 */
class MemBasedSessionManager implements NanoSessionManager {

    private String sessionID;
    private HashMap<String, Object> values = new LinkedHashMap<>();
    private Date expires;

    Date getExpires() {
        return expires;
    }

    void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public Object get(String key) {
        return values.get(key);
    }

    @Override
    public void set(String key, Object value) {
        values.put(key, value);
    }

    @Override
    public void delete(String key) {
        values.remove(key);
    }

}
