package org.aicha;

import org.aicha.scheduler.OverdueTasksScheduler;
import org.aicha.scheduler.TokenSchedular;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    private TokenSchedular tokenSchedular;
    private OverdueTasksScheduler taskSchedular;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        tokenSchedular = new TokenSchedular();
        taskSchedular = new OverdueTasksScheduler();
        tokenSchedular.startTokenScheduler();
        taskSchedular.startOverdueTasksScheduler();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        tokenSchedular.stopTokenScheduler();
        taskSchedular.stopOverdueTasksScheduler();
    }
}