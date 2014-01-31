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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Memory Based Session Handler Bundle Activator. Starting of bundle may be slow
 * due to initializing secure random generator
 *
 * @author kazim
 */
public class Activator implements BundleActivator {

    private MemBasedSessionHandler mbsh;
    ScheduledExecutorService cleaner;
    ScheduledFuture<?> cleanerth;

    /**
     * Bundle starter. Registers session handler and starts a schedular
     *
     * @param bc Bundle context
     * @throws Exception any exception
     */
    @Override
    public void start(BundleContext bc) throws Exception {
        Dictionary<String, Object> props = new Hashtable<>();
        mbsh = new MemBasedSessionHandler();
        bc.registerService(NanoSessionHandler.class.getName(), mbsh, props);
        cleaner = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "mbsh-cleaner-thread");
            }
        });
        cleanerth = cleaner.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                mbsh.cleanSesssionManagers();
            }
        }, 5, 5, TimeUnit.MINUTES);
    }

    /**
     * Stops cleaner.
     *
     * @param bc Bundle context
     * @throws Exception any exception
     */
    @Override
    public void stop(BundleContext bc) throws Exception {
        cleanerth.cancel(true);
        cleaner.shutdown();
    }

}
