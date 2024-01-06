package com.jsonex.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static final Path USER_JSON_PATH =
            Path.of("src", "main", "java", "resources", "dbContent", "users.json");
    public static final Path CATEGORY_JSON_PATH =
            Path.of("src", "main", "java", "resources", "dbContent", "categories.json");
    public static final Path PRODUCTS_JSON_PATH =
            Path.of("src", "main", "java", "resources", "dbContent", "categories.json");
}
