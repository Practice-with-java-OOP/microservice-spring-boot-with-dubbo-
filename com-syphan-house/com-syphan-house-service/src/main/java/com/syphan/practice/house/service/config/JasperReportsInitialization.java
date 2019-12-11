package com.syphan.practice.house.service.config;

import com.syphan.practice.house.service.utils.Constants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

@Component
public class JasperReportsInitialization implements ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(JasperReportsInitialization.class);

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Value("classpath:report_templates/*.jrxml")
    private Resource[] resources;

    @PostConstruct
    public void init() {
        System.setProperty("java.awt.headless", "true");
        Arrays.asList(resources).forEach(resource -> {
            try {
                InputStream reportStream = resource.getInputStream();
                JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
                int index = Objects.requireNonNull(resource.getFilename()).lastIndexOf(".");
                String name = resource.getFilename().substring(0, index);
                String saveName = String.format("%s%s", name, Constants.JASPER_EXTENSION);
                JRSaver.saveObject(jasperReport, saveName);
                logger.debug("Built to jasper file: {}", saveName);
            } catch (IOException | JRException e) {
                logger.error(null, e);
                SpringApplication.exit(context, () -> 0);
            }
        });
    }
}
