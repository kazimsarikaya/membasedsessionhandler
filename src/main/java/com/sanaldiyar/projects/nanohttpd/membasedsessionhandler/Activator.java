/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanaldiyar.projects.nanohttpd.membasedsessionhandler;

import com.sanaldiyar.projects.nanohttpd.nanohttpd.NanoSessionHandler;
import java.util.Dictionary;
import java.util.Hashtable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author kazim
 */
public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext bc) throws Exception {
        Dictionary<String, Object> props = new Hashtable<>();
        bc.registerService(NanoSessionHandler.class.getName(), new MemBasedSessionHandler(), props);
    }

    @Override
    public void stop(BundleContext bc) throws Exception {

    }

}
