/*
Memory Based Session Handler
Copryright © 2013 Kazım SARIKAYA

This program is licensed under the terms of Sanal Diyar Software License. Please
read the license file or visit http://license.sanaldiyar.com
 */
package com.sanaldiyar.projects.nanohttpd.membasedsessionhandler;

import com.sanaldiyar.projects.nanohttpd.nanohttpd.NanoSessionHandler;
import java.util.Dictionary;
import java.util.Hashtable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Memory Based Session Handler Bundle Activator.
 * Starting of bundle may be slow due to initializing secure random generator
 * @author kazim
 */
public class Activator implements BundleActivator {

    /**
     * Bundle starter.
     * @param bc Bundle context
     * @throws Exception any exception
     */
    @Override
    public void start(BundleContext bc) throws Exception {
        Dictionary<String, Object> props = new Hashtable<>();
        bc.registerService(NanoSessionHandler.class.getName(), new MemBasedSessionHandler(), props);
    }

    @Override
    public void stop(BundleContext bc) throws Exception {

    }

}
