package com.youjin.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by train on 17/2/27.
 */
@Configuration
public class UTCTimeZoneConfiguration implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        //System.setProperty("user.timezone", "UTC");
        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public void contextDestroyed(ServletContextEvent event) {}
}
