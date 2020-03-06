package io.koverj.agent.java.commons.config;

/**
 * Created by alpa on 3/3/20
 */
public class KoverjConfig {

    public static boolean isSendToKover = Boolean.parseBoolean(System.getProperty("use.kover", "true"));
    public static boolean logLocators = Boolean.parseBoolean(System.getProperty("log.locators", "false"));
}
