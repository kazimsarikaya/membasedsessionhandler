/*
 Memory Based Session Handler
 Copryright © 2013 Kazım SARIKAYA

 This program is licensed under the terms of Sanal Diyar Software License. Please
 read the license file or visit http://license.sanaldiyar.com
 */
package com.sanaldiyar.projects.nanohttpd.membasedsessionhandler;

import com.sanaldiyar.projects.nanohttpd.nanohttpd.NanoSessionManager;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Memory Based Session Manager. Stores session data inside a hash map
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
    public <T> T get(String key, Class<T> clazz) {
        return (T) values.get(key);
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
