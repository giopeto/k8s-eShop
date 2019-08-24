package com.store.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StoreConstants {

    public static final String STORE_PREFIX = "store";
    public static final String STORE_VERSION = "V1";
    public static final String STORE_BASE_URL = STORE_PREFIX + "/" + STORE_VERSION;

    public static final String EDGE_SERVICE_GET_CURRENT_ACCOUNT_URL = "http://edge-service-svc:8080/users/V1/users";
    public static final String JSESSIONID = "JSESSIONID";
}
