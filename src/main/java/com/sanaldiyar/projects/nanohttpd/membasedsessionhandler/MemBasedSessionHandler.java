/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanaldiyar.projects.nanohttpd.membasedsessionhandler;

import com.sanaldiyar.projects.nanohttpd.nanohttpd.Cookie;
import com.sanaldiyar.projects.nanohttpd.nanohttpd.NanoSessionHandler;
import com.sanaldiyar.projects.nanohttpd.nanohttpd.NanoSessionManager;
import com.sanaldiyar.projects.nanohttpd.nanohttpd.Request;
import com.sanaldiyar.projects.nanohttpd.nanohttpd.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author kazim
 */
public class MemBasedSessionHandler implements NanoSessionHandler {

    private final static String SESSIONCOOKIEID = "__MEMBASEDSESSIONHANDLER__";
    private final SecureRandom srng;
    private final HashMap<String, MemBasedSessionManager> managers;

    public MemBasedSessionHandler() {
        SecureRandom sr = new SecureRandom();
        srng = new SecureRandom(sr.generateSeed(16));
        managers = new LinkedHashMap<>();
    }

    @Override
    public NanoSessionManager parseRequest(Request request) {
        MemBasedSessionManager nanoSessionManager = null;
        String sessionid = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(SESSIONCOOKIEID)) {
                sessionid = cookie.getValue();
                break;
            }
        }
        if (sessionid != null) {
            nanoSessionManager = managers.get(sessionid);
            if (nanoSessionManager != null) {
                if (nanoSessionManager.getExpires().getTime() <= new Date().getTime()) {
                    managers.remove(sessionid);
                    nanoSessionManager = null;
                }
            }
        }
        if (nanoSessionManager == null) {
            do {
                sessionid = new BigInteger(128, srng).toString(32);
            } while (managers.containsKey(sessionid) && !sessionid.equals("0"));
            nanoSessionManager = new MemBasedSessionManager();
            nanoSessionManager.setSessionID(sessionid);
            managers.put(sessionid, nanoSessionManager);
        }
        return nanoSessionManager;
    }

    @Override
    public void parseResponse(NanoSessionManager nanoSessionManager, Response response) {
        if (!(nanoSessionManager instanceof MemBasedSessionManager)) {
            return;
        }
        MemBasedSessionManager mbsm = (MemBasedSessionManager) nanoSessionManager;
        String sessionid = mbsm.getSessionID();
        Cookie sesscookie = new Cookie(SESSIONCOOKIEID, sessionid, 15, TimeUnit.MINUTES, response.getRequestURL().getHost(), "/", false, true);
        mbsm.setExpires(sesscookie.getExpires());
        response.getCookies().add(sesscookie);
    }

}
