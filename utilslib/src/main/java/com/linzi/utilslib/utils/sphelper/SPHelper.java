package com.linzi.utilslib.utils.sphelper;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static com.linzi.utilslib.utils.sphelper.ConstantUtil.*;

public class SPHelper {
    public static final String COMMA_REPLACEMENT="__COMMA__";

    public static Context context;

    public static void checkContext() {
        if (context == null) {
            throw new IllegalStateException("context has not been initialed");
        }
    }

    public static void init(Context application) {
        if(context==null) {
            context = application;
            TAG=application.getPackageName();
            refreshURI();
        }
    }
    public static void init(Context application,String name) {
        if(context==null) {
            context = application;
            TAG=name;
            refreshURI();
        }
    }

    public synchronized static void put(String name, Boolean t) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_BOOLEAN + SEPARATOR + name);
        ContentValues cv = new ContentValues();
        cv.put(VALUE, t);
        cr.update(uri, cv, null, null);
    }

    public synchronized static void put(String name, String t) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_STRING + SEPARATOR + name);
        ContentValues cv = new ContentValues();
        cv.put(VALUE, t);
        cr.update(uri, cv, null, null);
    }

    public synchronized static void put(String name, Integer t) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_INT + SEPARATOR + name);
        ContentValues cv = new ContentValues();
        cv.put(VALUE, t);
        cr.update(uri, cv, null, null);
    }

    public synchronized static void put(String name, Long t) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_LONG + SEPARATOR + name);
        ContentValues cv = new ContentValues();
        cv.put(VALUE, t);
        cr.update(uri, cv, null, null);
    }

    public synchronized static void put(String name, Float t) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_BOOLEAN + SEPARATOR + name);
        ContentValues cv = new ContentValues();
        cv.put(VALUE, t);
        cr.update(uri, cv, null, null);
    }


    public synchronized static void put(String name, Set<String> t) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_STRING_SET + SEPARATOR + name);
        ContentValues cv = new ContentValues();
        Set<String> convert=new HashSet<>();
        for (String string:t){
            convert.add(string.replace(",",COMMA_REPLACEMENT));
        }
        cv.put(VALUE, convert.toString());
        cr.update(uri, cv, null, null);
    }

    /**
     * 获取数据操作
     * @param key
     * @param t
     * @param <T>泛型
     * @return
     */
    public static <T>T get(String key, T t){
        Object msg="";

        if (t instanceof Integer) {
            msg = getInt(key, 0);
        } else if (t instanceof String) {
            msg = getString(key, "");
        } else if (t instanceof Boolean) {
            msg = getBoolean(key, true);
        } else if (t instanceof Float) {
            msg = getFloat(key, 0);
        } else if (t instanceof Long) {
            msg = getLong(key, 0);
        }

        return  (T)msg;
    }

    private static String getString(String name, String defaultValue) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_STRING + SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(NULL_STRING)) {
            return defaultValue;
        }
        return rtn;
    }

    private static int getInt(String name, int defaultValue) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_INT + SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(NULL_STRING)) {
            return defaultValue;
        }
        return Integer.parseInt(rtn);
    }

    private static float getFloat(String name, float defaultValue) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_FLOAT + SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(NULL_STRING)) {
            return defaultValue;
        }
        return Float.parseFloat(rtn);
    }

    private static boolean getBoolean(String name, boolean defaultValue) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_BOOLEAN + SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(NULL_STRING)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(rtn);
    }

    private static long getLong(String name, long defaultValue) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_LONG + SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(NULL_STRING)) {
            return defaultValue;
        }
        return Long.parseLong(rtn);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(String name, Set<String> defaultValue) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_STRING_SET + SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(NULL_STRING)) {
            return defaultValue;
        }
        if (!rtn.matches("\\[.*\\]")){
            return defaultValue;
        }
        String sub=rtn.substring(1,rtn.length()-1);
        String[] spl=sub.split(", ");
        Set<String> returns=new HashSet<>();
        for (String t:spl){
            returns.add(t.replace(COMMA_REPLACEMENT,", "));
        }
        return returns;
    }

    public static boolean contains(String name) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_CONTAIN + SEPARATOR + name);
        String rtn = cr.getType(uri);
        if (rtn == null || rtn.equals(NULL_STRING)) {
            return false;
        } else {
            return Boolean.parseBoolean(rtn);
        }
    }

    public static void remove(String name) {
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_LONG + SEPARATOR + name);
        cr.delete(uri, null, null);
    }

    public static void clear(){
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_CLEAN);
        cr.delete(uri,null,null);
    }

    public static Map<String,?> getAll(){
        checkContext();
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TYPE_GET_ALL);
        Cursor cursor=cr.query(uri,null,null,null,null);
        HashMap resultMap=new HashMap();
        if (cursor!=null && cursor.moveToFirst()){
            int nameIndex=cursor.getColumnIndex(CURSOR_COLUMN_NAME);
            int typeIndex=cursor.getColumnIndex(CURSOR_COLUMN_TYPE);
            int valueIndex=cursor.getColumnIndex(CURSOR_COLUMN_VALUE);
            do {
                String key=cursor.getString(nameIndex);
                String type=cursor.getString(typeIndex);
                Object value = null;
                if (type.equalsIgnoreCase(TYPE_STRING)) {
                    value= cursor.getString(valueIndex);
                    if (((String)value).contains(COMMA_REPLACEMENT)){
                        String str= (String) value;
                        if (str.matches("\\[.*\\]")){
                            String sub=str.substring(1,str.length()-1);
                            String[] spl=sub.split(", ");
                            Set<String> returns=new HashSet<>();
                            for (String t:spl){
                                returns.add(t.replace(COMMA_REPLACEMENT,", "));
                            }
                            value=returns;
                        }
                    }
                } else if (type.equalsIgnoreCase(TYPE_BOOLEAN)) {
                    value= cursor.getString(valueIndex);
                } else if (type.equalsIgnoreCase(TYPE_INT)) {
                    value= cursor.getInt(valueIndex);
                } else if (type.equalsIgnoreCase(TYPE_LONG)) {
                    value= cursor.getLong(valueIndex);
                } else if (type.equalsIgnoreCase(TYPE_FLOAT)) {
                    value= cursor.getFloat(valueIndex);
                } else if (type.equalsIgnoreCase(TYPE_STRING_SET)) {
                    value= cursor.getString(valueIndex);
                }
                resultMap.put(key,value);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return resultMap;
    }

}