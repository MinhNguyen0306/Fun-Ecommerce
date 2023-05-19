package com.example.funE.Utils;

import org.springframework.beans.factory.annotation.Value;

public class AppConstants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "10";
    public static final String SORT_BY = "id";
    public static final String SORT_DIR = "asc";

    @Value("${project.image}")
    public static String path;
}
