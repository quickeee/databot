package com.sck.engine.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by KINCERS on 7/13/2015.
 */
public final class ConfigValue {


    // File
    public static final String FILE_TYPE = "FILE";
    public static final String FILE_CONFIG_TYPE = "File";
    public static final String FILEFETCH_CONFIG_TYPE = "FileFetch";
    public static final String FILELOCAL_CONFIG_TYPE = "FileLocal";
    public static final String FILEFTP_CONFIG_TYPE = "FileFTP";

    public static final List<String> FILE_REQUIRED_VALUES = Collections.unmodifiableList(
            Arrays.asList(
                    "locationType"
            )
    );
    
    // Database
    public static final String DATABASE_TYPE = "DATABASE";
    public static final String DATABASE_CONFIG_TYPE = "Database";

    public static final List<String> DATABASE_REQUIRED_VALUES = Collections.unmodifiableList(
            Arrays.asList(
                    ""
            )
    );

    // Web Service
    public static final String WEBSERVICE_TYPE = "WEBSERVICE";
    public static final String WEBSERVICE_CONFIG_TYPE = "WebService";

    public static final List<String> WEBSERVICE_REQUIRED_VALUES = Collections.unmodifiableList(
            Arrays.asList(
                    ""
            )
    );


}
