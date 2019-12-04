package com.files.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FilesConstants {

    public static final String FILES_PREFIX = "files";
    public static final String FILES_VERSION = "v1";
    public static final String FILES_BASE_URL = FILES_PREFIX + "/" + FILES_VERSION;

    public static final String AUTHENTICATION_SERVICE_GET_CURRENT_ACCOUNT_URL = "/authentication/v1/users";
    public static final String JSESSIONID = "JSESSIONID";
}
