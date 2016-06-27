package com.sck.engine.config.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by KINCERS on 4/23/2015.
 */
public final class DataTypes {

    public static final List<String> EXCEL_LEGACY = Collections.unmodifiableList(
            Arrays.asList(
                    "application/vnd.ms-excel"
            )
    );

    public static final List<String> EXCEL = Collections.unmodifiableList(
            Arrays.asList(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            )
    );

    public static final List<String> FLATFILE = Collections.unmodifiableList(
            Arrays.asList(
                    "text/plain",
                    "text/csv",
                    "application/octet-stream"
            )
    );

    public static final List<String> JSON = Collections.unmodifiableList(
            Arrays.asList(
                    "application/json"
            )
    );

    public static final List<String> XML = Collections.unmodifiableList(
            Arrays.asList(
                    "application/xml",
                    "text/xml"
            )
    );

    public static final List<String> HTML = Collections.unmodifiableList(
            Arrays.asList(
                    "text/html"
            )
    );


}
