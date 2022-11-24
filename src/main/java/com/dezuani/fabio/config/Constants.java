package com.dezuani.fabio.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String CLASSE_ENTITY = "classe";
    public static final String COMPITO_ENTITY = "compito";
    public static final String COMPITO_SVOLTO_ENTITY = "compito_svolto";
    public static final String ALUNNO_ENTITY = "alunno";

    private Constants() {}
}
