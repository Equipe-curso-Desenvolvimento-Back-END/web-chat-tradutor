package com.chat.chat_tradutor.common;

public final class UserConstants {

    // Password definido no RN

    // Somente caracteres ocidentais
    public static final String RENGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[&$!*#]).{8,}$";

    // Email definido no RN

    public static final int MAX_SIZE_EMAIL = 100;

    public static final char ESPECIAL_CHAR_EMAIL = '@';

    public static final String[] EMAILS = {

        "gmail.com",
        "outlook.com",
        "hotmail.com",
        "yahoo.com",
        "live.com",
        "icloud.com",
        "proton.me",
        "protonmail.com",
        "aol.com",
        "msn.com",
        "mail.com",
        "yandex.com",
        "fastmail.com",
        "zoho.com",
        "gmx.com",
        "hey.com"

    };

    // Name definido no RN

    public static final int MAX_SIZE_NAME = 50;

    // Roons limit

    public static final int MAX_ROOMS = 5;

}
