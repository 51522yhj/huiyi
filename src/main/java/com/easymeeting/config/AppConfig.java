package com.easymeeting.config;

import com.easymeeting.utils.StringTools;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("appConfig")
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    @Value("${ws.port:}")
    private Integer wsPort;
    @Value("${project.folder:}")
    private String projectFolder;
    @Value("${admin.emails:}")
    private String adminEmails;
    public String getProjectFolder() {
        if (!StringTools.isEmpty(projectFolder) && !projectFolder.endsWith("/")) {
            projectFolder += "/";

            return projectFolder;


        }
        return projectFolder;
    }
    public String getAdminEmails() {
        return adminEmails;
    }
    public Integer getWsPort() {
        return wsPort;
    }
}
