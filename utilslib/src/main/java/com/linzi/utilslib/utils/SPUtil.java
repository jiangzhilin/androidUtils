package com.linzi.utilslib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by jiang on 2017/2/16.
 * 简单数据存储工具类
 */

public class SPUtil {
    private static SharedPreferences sp;
    private static Context mContext;

    /**
     * 构造函数（无提示信息）
     * @param context
     * @param filename 文件名
     * @param mode 文件权限
     */
    public static void init(Context context, String filename, int mode){
        mContext=context;
        sp=mContext.getSharedPreferences(filename,mode);
    }
    /**
     * 存储数据的操作，无返回值
     * @param key 关键字
     * @param value 数据
     */
    public static void put(String key, Object value){
        if(sp!=null) {
            SharedPreferences.Editor edit=sp.edit();
            if (value instanceof Integer) {
                edit.putInt(key, (Integer) value).apply();
            } else if (value instanceof String) {
                edit.putString(key, (String) value).apply();
            } else if (value instanceof Boolean) {
                edit.putBoolean(key, (Boolean) value).apply();
            } else if (value instanceof Float) {
                edit.putFloat(key, (Float) value).apply();
            } else if (value instanceof Long) {
                edit.putLong(key, (Long) value).apply();
            }
            edit.commit();
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 存储数组
     * @param key
     * @param values
     */
    public static boolean saveArray(String key,String values) {
        if(sp!=null) {
            int size=sp.getInt(key+"_size",0);

            SharedPreferences.Editor mEdit1 = sp.edit();
            size=size+1;
            mEdit1.remove(key+"_size");
            mEdit1.putInt(key+"_size", size);

            mEdit1.remove(key+"_" + size);
            mEdit1.putString(key+"_" + size, values);

            return  mEdit1.commit();
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 存储数组
     * @param key
     * @param values
     */
    public static boolean saveArray(String key,List<String> values) {
        if(sp!=null) {
            int size=values.size();

            SharedPreferences.Editor mEdit1 = sp.edit();

            mEdit1.putInt(key+"_size", size);
            for(int x=0;x<size;x++) {
                mEdit1.putString(key + "_" + (x+1), values.get(x));
            }

            return  mEdit1.commit();
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static List<String> loadArray(String key) {
        List<String> list=new ArrayList<>();
        if(sp!=null) {
            int size=sp.getInt(key+"_size",0);
            Log.d(TAG, "loadArray: "+size);
            for (int i = 0; i < size; i++) {
                list.add(sp.getString(key+"_" + (i+1), null));
            }
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
        }
        return list;
    }

    /**
     * 移除数组
     * @param key 关键字
     */
    public static void removeArray(String key){
        if(sp!=null) {
            SharedPreferences.Editor edit=sp.edit();
            int size=sp.getInt(key+"_size",0);
            edit.remove(key+"_size");
            for (int i = 0; i < size; i++) {
                edit.remove(key+"_" + (i+1));
            }
            edit.commit();
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 移除数组中的某个值
     * @param key 关键字
     */
    public static void delArray(String key,int position){
        List<String> list=new ArrayList<>();
        if(sp!=null) {
            SharedPreferences.Editor edit=sp.edit();
            int size=sp.getInt(key+"_size",0);
            edit.remove(key+"_size");
            for (int i = 0; i < size; i++) {
                list.add(sp.getString(key+"_" + (i+1), null));
                edit.remove(key+"_" + (i+1));
            }

            list.remove(position);
            edit.putInt(key+"_size", list.size());
            for (int i = 0; i < list.size(); i++) {
                    edit.putString(key+"_" + (i+1), list.get(i));
            }

            edit.commit();
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 关闭编辑器（可以忽略）
     */
    public static void commit(){
        if(sp!=null) {
            sp.edit().commit();
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 清空所有数据
     */
    public static void clear(){
        if(sp!=null) {
            SharedPreferences.Editor edit=sp.edit();
            edit.clear();
            edit.commit();
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 移除某一条数据
     * @param key 关键字
     */
    public static void remove(String key){
        if(sp!=null) {
            SharedPreferences.Editor edit=sp.edit();
            edit.remove(key);
            edit.commit();
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 枚举值，用作标识每次取的值的类型（外部调用）
     */
    public enum Type{
        INT,STR,BOOL,FLO,LONG
    }

    /**
     * 获取数据的操作
     * @param key 关键字
     * @param type 枚举类型
     * @return 返回object
     */
    public static Object get(String key, Type type){
        Object msg="";
        if(sp!=null) {
            if (type == Type.INT) {
                msg = sp.getInt(key, 0);
            } else if (type == Type.STR) {
                msg = sp.getString(key, "");
            } else if (type == Type.BOOL) {
                msg = sp.getBoolean(key, true);
            } else if (type == Type.FLO) {
                msg = sp.getFloat(key, 0);
            } else if (type == Type.LONG) {
                msg = sp.getLong(key, 0);
            }
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
        }
        return  msg;
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
        if(sp!=null) {
            if (t instanceof Integer) {
                msg = sp.getInt(key, 0);
            } else if (t instanceof String) {
                msg = sp.getString(key, "");
            } else if (t instanceof Boolean) {
                msg = sp.getBoolean(key, true);
            } else if (t instanceof Float) {
                msg = sp.getFloat(key, 0);
            } else if (t instanceof Long) {
                msg = sp.getLong(key, 0);
            }
        }else{
            Toast.makeText(mContext,"请先实例化工具类", Toast.LENGTH_SHORT).show();
        }
        return  (T)msg;
    }
}
