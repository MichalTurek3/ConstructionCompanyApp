package com.company.CompanyApp.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    /// /// URLs //////

    public static final String CUSTOMER_URL = "/api/v1/customer/**";

    public static final String ADMIN_URL = "/api/v1/admin/**";

    public static final String AUTH_URL = "/api/v1/auth/**";

    public static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "v2/api-docs",
            "/swagger-resources",
            "swagger-resources",
            "/swagger-resources/**",
            "swagger-resources/**",
            "/configuration/ui",
            "configuration/ui",
            "/configuration/security",
            "configuration/security",
            "/swagger-ui.html",
            "swagger-ui.html",
            "webjars/**",
            // -- Swagger UI v3
            "/v3/api-docs/**",
            "v3/api-docs/**",
            "/swagger-ui/**",
            "swagger-ui/**",
    };

    /// /// Roles //////

    public static final String USER = "USER";

    public static final String ADMIN = "ADMIN";

    /// /// Entity types //////

    public static final String CUSTOMER = "customer";

    public static final String CONSTRUCTION = "construction";

    public static final String MATERIAL = "material";

    public static final String TASK = "task";

    /// /// Customer field names //////

    public static final String FIRST_NAME = "firstName";

    public static final String LAST_NAME = "lastName";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    private static final String CUSTOMER_FIELDS = "username, password, firstName, lastName";

    /// /// Construction field names //////

    public static final String CONSTRUCTION_NAME = "name";

    public static final String PLANNED_PRICE_OF_REALIZATION = "plannedPriceOfRealization";

    public static final String PERCENT_OF_REALIZATION = "percentOfRealization";

    public static final String CURRENT_COST_OF_REALIZATION = "currentCostOfRealization";

    public static final String LOCATION = "location";

    private static final String CONSTRUCTION_FIELDS = "name, plannedPriceOfRealization, percentOfRealization, " +
            "currentCostOfRealization, location";

    /// /// Material field names //////

    public static final String MATERIAL_NAME = "name";

    public static final String ORDER_DATE = "orderDate";

    public static final String PRICE = "price";

    public static final String QUANTITY = "quantity";

    private static final String MATERIAL_FIELDS = "materialName, orderDate, price, quantity";

    /// /// Task field names //////

    public static final String TASK_NAME = "name";

    public static final String DURATION = "duration";

    public static final String IS_DONE = "isDone";

    public static final String PLANNED_VALUE = "plannedValue";

    private static final String TASK_FIELDS = "name, duration, isDone, plannedValue";

    /// /// Error messages //////

    public static final String USER_NOT_FOUND_ERROR_MESSAGE = "User not found";

    public static final String CONSTRUCTION_NOT_FOUND_ERROR_MESSAGE = "Construction not found";

    public static final String MATERIAL_NOT_FOUND_ERROR_MESSAGE = "Material not found";

    public static final String TASK_NOT_FOUND_ERROR_MESSAGE = "Task not found exception";

    private static final String INVALID_FIELD_NAME_ERROR_MESSAGE = "Invalid field name, choose from ";

    public static final String CUSTOMER_FIELD_ERROR_MESSAGE = INVALID_FIELD_NAME_ERROR_MESSAGE + CUSTOMER_FIELDS;

    public static final String MATERIAL_FIELD_ERROR_MESSAGE = INVALID_FIELD_NAME_ERROR_MESSAGE + MATERIAL_FIELDS;

    public static final String CONSTRUCTION_FIELD_ERROR_MESSAGE = INVALID_FIELD_NAME_ERROR_MESSAGE + CONSTRUCTION_FIELDS;

    public static final String TASK_FIELD_ERROR_MESSAGE = INVALID_FIELD_NAME_ERROR_MESSAGE + TASK_FIELDS;

    public static final String INVALID_ENTITY_TYPE_ERROR_MESSAGE = "Invalid entity type choose from customer, construction, material";

    public static final String ADMIN_NOT_AUTHORIZED_ERROR_MESSAGE = "Admin not authorized";

    public static final String CUSTOMER_NOT_AUTHORIZED_ERROR_MESSAGE = "Customer not authorized";

}
