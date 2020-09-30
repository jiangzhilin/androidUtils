package com.linzi.utilslib.utils.sphelper;


import com.linzi.utilslib.BuildConfig;

public class ConstantUtil {
    // normal constants
    public static final String CONTENT="content://";
    public static String AUTHORITY= BuildConfig.APPLICATION_ID+".sphelper";
    public static String TAG="linzi";
    public static final String SEPARATOR= "/";
    public static String CONTENT_URI =CONTENT+AUTHORITY+SEPARATOR+TAG+SEPARATOR;
    public static final String TYPE_STRING_SET="string_set";
    public static final String TYPE_STRING="string";
    public static final String TYPE_INT="int";
    public static final String TYPE_LONG="long";
    public static final String TYPE_FLOAT="float";
    public static final String TYPE_BOOLEAN="boolean";
    public static final String VALUE= "value";

    public static final String NULL_STRING= "null";
    public static final String TYPE_CONTAIN="contain";
    public static final String TYPE_CLEAN="clean";
    public static final String TYPE_GET_ALL="get_all";

    public static final String CURSOR_COLUMN_NAME = "cursor_name";
    public static final String CURSOR_COLUMN_TYPE = "cursor_type";
    public static final String CURSOR_COLUMN_VALUE = "cursor_value";

    public static void refreshURI(){
        if(SPHelper.context!=null) {
            AUTHORITY = SPHelper.context.getPackageName() + ".sphelper";
        }
        CONTENT_URI =CONTENT+AUTHORITY+SEPARATOR+TAG+SEPARATOR;
    }
}
