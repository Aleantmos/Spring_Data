package com.jsonex.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static final Path USER_JSON_PATH =
            Path.of("src", "main", "resources", "dbContent", "jsons", "users.json");
    public static final Path CATEGORY_JSON_PATH =
            Path.of("src", "main", "resources", "dbContent", "jsons", "categories.json");
    public static final Path PRODUCTS_JSON_PATH =
            Path.of("src", "main", "resources", "dbContent", "jsons", "products.json");
    public static final Path PRODUCTS_WITHOUT_BUYERS_IN_RANGE_JSON_PATH =
            Path.of("src", "main", "resources", "outputs", "jsons", "products-in-range.json");
    public static final Path USERS_WITH_SOLD_PRODUCTS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs", "jsons", "users-sold-products.json");
    public static final Path CATEGORIES_BY_PRODUCTS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs", "jsons", "categories-by-products.json");
    public static final Path USERS_AND_PRODUCTS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs", "jsons", "users-and-products.json");


    public static final Path USER_XML_PATH =
            Path.of("src", "main", "resources", "dbContent", "xmls", "users.xml");
    public static final Path CATEGORY_XML_PATH =
            Path.of("src", "main", "resources", "dbContent", "xmls", "categories.xml");
    public static final Path PRODUCTS_XML_PATH =
            Path.of("src", "main", "resources", "dbContent", "xmls", "products.xml");
    public static final Path PRODUCTS_WITHOUT_BUYERS_IN_RANGE_XML_PATH =
            Path.of("src", "main", "resources", "outputs", "xmls", "products-in-range.xml");
    public static final Path USERS_WITH_SOLD_PRODUCTS_XML_PATH =
            Path.of("src", "main", "resources", "outputs", "xmls", "users-sold-products.xml");
    public static final Path CATEGORIES_BY_PRODUCTS_XML_PATH =
            Path.of("src", "main", "resources", "outputs", "xmls", "categories-by-products.xml");
    public static final Path USERS_AND_PRODUCTS_XML_PATH =
            Path.of("src", "main", "resources", "outputs", "xmls", "users-and-products.xml");
}
