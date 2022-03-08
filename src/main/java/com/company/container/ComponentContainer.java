package com.company.container;

import com.company.Bot;
import com.company.enums.UserStatus;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public abstract class ComponentContainer {

    public static Bot MY_TELEGRAM_BOT;
    public static final String ADMIN = "1438229631";
    public static Map<String, UserStatus> userStatusMap = new HashMap<>();


    private static int generalId = 1;

    public static int nextId() {
        return generalId++;
    }



}
