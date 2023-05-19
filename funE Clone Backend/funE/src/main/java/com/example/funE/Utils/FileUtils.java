package com.example.funE.Utils;

import java.util.Objects;
import java.util.Optional;

public class FileUtils {
    public static Optional<String> getExtensionByStringHandling(String fileName) {
        return Optional.ofNullable(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(fileName.lastIndexOf(".") + 1));
    }
}
