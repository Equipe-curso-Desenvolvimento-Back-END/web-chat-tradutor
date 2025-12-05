package com.chat.chat_tradutor.common;

import jakarta.persistence.Entity;

public final class Languages {

    //  Google API
    public static final String[] OTHERS = {"en",
        "zh-CN",
        "pt-BR",
        "de",
        "fr",
        "es"};

    // WEB MODEL
    public static final String[] BCP = {"en-US",
        "zh-CN",
        "pt-BR",
        "de-DE",
        "fr-FR",
        "es-ES"};

    // AWS API
    public static final String[] AWS = {"en",
        "zh",
        "pt",
        "de",
        "fr",
        "es"};

    // format generic

    public static final String ENGLISH = "en";
    public static final String CHINESE = "zn-CN";
    public static final String PORTUGUESE = "pt-BR";
    public static final String GERMAN = "de";
    public static final String FRENCH = "fr";
    public static final String SPANISH = "es";

    // format AWS

    public static final String CHINESE_AWS = "zh";
    public static final String PORTUGUESE_AWS = "pt";

}
