package com.linzi.utilslib.processCall;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.util.Log;

import com.linzi.utilslib.BuildConfig;
import com.linzi.utilslib.processCall.utils.MD5;
import com.linzi.utilslib.processCall.utils.OpenSSlUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @author linzi
 * @date 20-11-6
 * @context 多线程通信和数据数据共享，支持明文存储和加密存储
 * @tips 加密使用的openssl加密方案
 * @tips 需要申请文件读取权限
 */
public class RWHelper {
    //上下文对象
    private static Context mContext;
    //URI路径规范
    private static final String CONTENT="content://";
    private static String AUTHORITY= BuildConfig.APPLICATION_ID +".lock";
    private static final String SEPARATOR= "/";
    private static String CONTENT_URI =CONTENT+AUTHORITY;
    //数据类型表达式
    private static final String TYPE_STRING="string";
    private static final String TYPE_INT="int";
    private static final String TYPE_LONG="long";
    private static final String TYPE_FLOAT="float";
    private static final String TYPE_BOOLEAN="boolean";
    private static final String VALUE= "value";
    private static final String CURSOR_COLUMN_NAME = "cursor_name";
    private static final String CURSOR_COLUMN_TYPE = "cursor_type";
    private static final String CURSOR_COLUMN_VALUE = "cursor_value";
    //文件名
    private static String TAGNAME=null;
    //加密密码
    private static String password=null;

    /**
     * @author linzi
     * @date 20-11-6 下午3:04
     * @context
     * @说明 Uri命名规则  [ content://包名.lock/标识名/key值 ]
     */
    public static void init(Context context){
        Log.e("RWHelper","无密码初始化");
        if(mContext==null){
            mContext=context;
            AUTHORITY= mContext.getPackageName()+".lock";
            CONTENT_URI =CONTENT+AUTHORITY;
            TAGNAME=mContext.getPackageName();
        }
    }
    /**
     * @author linzi
     * @date 20-11-6 下午4:00
     * @context
     * @tagname 标识名
     */
    public static void init(Context context,String tagNAME){
        if(mContext==null){
            mContext=context;
            AUTHORITY= mContext.getPackageName()+".lock";
            CONTENT_URI =CONTENT+AUTHORITY;
            TAGNAME=tagNAME;
        }
    }
    /**
     * @author linzi
     * @date 20-11-6 下午4:00
     * @context
     * @pwd 密码
     */
    public static void initWithPwd(Context context,String pwd){
        Log.e("RWHelper","带密码初始化");
        if(mContext==null){
            mContext=context;
            AUTHORITY= mContext.getPackageName()+".lock";
            CONTENT_URI =CONTENT+AUTHORITY;
            TAGNAME=mContext.getPackageName();
            if(pwd.length()<16){
                password= MD5.INSTANCE.md5(pwd);
            }else{
                password=pwd;
            }
        }
    }
    /**
     * @author linzi
     * @date 20-11-6 下午4:00
     * @context
     * @param tagNAME 标识名
     * @pwd 密码
     */
    public static void initWithPwd(Context context,String tagNAME,String pwd){
        if(mContext==null){
            mContext=context;
            AUTHORITY= mContext.getPackageName()+".lock";
            CONTENT_URI =CONTENT+AUTHORITY;
            TAGNAME=tagNAME;
            if(pwd.length()<16){
                password= MD5.INSTANCE.md5(pwd);
            }else{
                password=pwd;
            }
        }
    }

    private static void checkContext() {
        if (mContext == null) {
            throw new IllegalStateException("context has not been initialed");
        }
    }
    /**
     * @author linzi
     * @date 20-11-6 下午3:12
     * @context 存储
     */
    public synchronized static void put(String key, Object value){
        checkContext();
        ContentResolver cr = mContext.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TAGNAME + SEPARATOR + key);
        ContentValues cv = new ContentValues();
        cv.put(VALUE, value+"");
        cr.insert(uri, cv);
    }

    /**
     * 获取数据操作
     * @param key
     * @param t
     * @param <T>泛型
     * @return
     */
    public synchronized static <T>T get(String key, T t){
        checkContext();
        Object msg="";
        ContentResolver cr = mContext.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + TAGNAME + SEPARATOR + key);
        String rtn = cr.getType(uri);
        if(rtn!=null){
            if (t instanceof Integer) {
                msg = Integer.parseInt(rtn);
            } else if (t instanceof String) {
                msg = rtn;
            } else if (t instanceof Boolean) {
                msg = Boolean.parseBoolean(rtn);
            } else if (t instanceof Float) {
                msg = Float.parseFloat(rtn);
            } else if (t instanceof Long) {
                msg = Long.parseLong(rtn);
            }
        }else{
            msg=t;
        }
        return  (T)msg;
    }
    /**
     * @author linzi
     * @date 20-11-6 下午3:12
     * @context 移出key
     */
    public synchronized static void remove(String key){
        checkContext();
        ContentResolver cr = mContext.getContentResolver();
        Uri uri = Uri.parse(CONTENT_URI + SEPARATOR + mContext.getPackageName() + SEPARATOR + key);
        cr.delete(uri,null,null);
    }
    
    /**
     * @author linzi
     * @date 20-11-6 下午4:00
     * @context 自定义content provider
     */
    public static class RWContentProvider extends ContentProvider{
        //数据内存缓存变量
        private ArrayMap<String,Object> mDatas;
        //本地存储地址
        private String parentPath= Environment.getExternalStorageDirectory()+"/lock/";
        //文件读写锁
        private ReadWriteLock mLock;
        //数据读写锁
        private ReadWriteLock mDataLock;
        @Override
        public boolean onCreate() {
            Log.e("存储路径",parentPath);
            File file=new File(parentPath);
            if(!file.exists()){
                file.mkdirs();
            }else{
                Log.d("error","已经存在");
            }
            mLock=new ReentrantReadWriteLock();
            mDataLock=new ReentrantReadWriteLock();
            return true;
        }
        /**
         * @author linzi
         * @date 20-11-6 下午3:59
         * @context 查询所有
         */
        @Nullable
        @Override
        public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
            String[] path= uri.getPath().split(SEPARATOR);
            String name=path[1];
            checkFile(name);
            if(mDatas!=null) {
                MatrixCursor cursor = new MatrixCursor(new String[]{CURSOR_COLUMN_NAME,CURSOR_COLUMN_TYPE,CURSOR_COLUMN_VALUE});
                mDataLock.readLock().lock();
                try {
                    Set<String> keySet = mDatas.keySet();
                    for (String key : keySet) {
                        Object[] rows = new Object[3];
                        rows[0] = key;
                        rows[2] = mDatas.get(key);
                        if (rows[2] instanceof Boolean) {
                            rows[1] = TYPE_BOOLEAN;
                        } else if (rows[2] instanceof String) {
                            rows[1] = TYPE_STRING;
                        } else if (rows[2] instanceof Integer) {
                            rows[1] = TYPE_INT;
                        } else if (rows[2] instanceof Long) {
                            rows[1] = TYPE_LONG;
                        } else if (rows[2] instanceof Float) {
                            rows[1] = TYPE_FLOAT;
                        }
                        cursor.addRow(rows);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    mDataLock.readLock().unlock();
                }
                return cursor;
            }
            return null;
        }
        /**
         * @author linzi
         * @date 20-11-6 下午3:59
         * @context 单独从缓存中读取key
         */
        @Nullable
        @Override
        public String getType(@NonNull Uri uri) {
            // 用这个来取数值
            String[] path= uri.getPath().split(SEPARATOR);
            String name=path[1];
            String key=path[2];
            checkFile(name);
            if(mDatas!=null) {
                Object value = mDatas.get(key);
                return  value==null? null : ""+value;
            }else{
                return null;
            }

        }
        /**
         * @author linzi
         * @date 20-11-6 下午3:59
         * @context 插入数据
         */
        @Nullable
        @Override
        public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
            String[] path= uri.getPath().split(SEPARATOR);
            String name=path[1];
            String key=path[2];
            Object obj= (Object) values.get(VALUE);
            checkFile(name);
            if(obj!=null&&mDatas!=null) {
                mDataLock.writeLock().lock();
                try {
                    mDatas.put(key, obj);
                } catch (Exception e){
                    e.printStackTrace();
                }finally {
                    mDataLock.writeLock().unlock();
                }
                saveData(name);
            }
            return null;
        }
        /**
         * @author linzi
         * @date 20-11-6 下午3:58
         * @context 删除数据
         */
        @Override
        public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
            String[] path= uri.getPath().split(SEPARATOR);
            String name=path[1];
            String key=path[2];
            checkFile(name);
            if(mDatas!=null) {
                mDataLock.writeLock().lock();
                try {
                    mDatas.remove(key);
                } catch (Exception e){
                    e.printStackTrace();
                }finally {
                    mDataLock.writeLock().unlock();
                }
                saveData(name);
            }
            return 0;
        }
        /**
         * @author linzi
         * @date 20-11-6 下午3:58
         * @context 更新缓存和本地存储
         */
        @Override
        public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
            insert(uri,values);
            return 0;
        }
        /**
         * @author linzi
         * @date 20-11-6 下午3:58
         * @context check本地数据文件，完成初始化
         */
        private void checkFile(String name){
            if(mDatas==null&&mContext!=null){
                File file=new File(parentPath+name+".datas");
                if(file.exists()){
                    readFile(name);
                }else{
                    mDatas=new ArrayMap<String,Object>();
                    mLock.writeLock().lock();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        mLock.writeLock().unlock();
                    }
                }
            }
        }
        /**
         * @author linzi
         * @date 20-11-6 下午3:57
         * @context 从本地读取数据到缓存变量中
         */
        private void readFile(String name){
            mLock.readLock().lock();
            try {
                File file = new File(parentPath + name + ".datas");
                if (file.exists()) {
                    int length = (int) file.length();
                    byte[] bytes = new byte[length];
                    FileInputStream in = new FileInputStream(file);
                    try {
                        in.read(bytes);
                    } catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        in.close();
                    }
                    String contents = new String(bytes);
                    if(password!=null&&contents!=null) {
                        contents = OpenSSlUtils.Companion.decrypt0(contents, password);
                        Log.e("解密",contents);
                    }
                    Log.d("读取到存储的数据",contents);
                    mDatas= getStringToMap(contents);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                mLock.readLock().unlock();
            }
        }
        /**
         * @author linzi
         * @date 20-11-6 下午3:57
         * @context 将变量中缓存的数据存储到本地
         */
        private void saveData(String name){
            mLock.writeLock().lock();
            FileOutputStream fos=null;
            try {
                fos= new FileOutputStream(parentPath+name+".datas");
                String datas=getMapToString(mDatas);
                if(password!=null) {
                    datas = OpenSSlUtils.Companion.encrypt0(datas, password);
                }
                byte[] bytes=datas.getBytes();
                fos.write(bytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    if (fos!=null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mLock.writeLock().unlock();
            }
        }

        /**
         *
         * Map转String
         * @param map
         * @return
         */
        private String getMapToString(Map<String,Object> map){
            Set<String> keySet = map.keySet();
            //将set集合转换为数组
            String[] keyArray = keySet.toArray(new String[keySet.size()]);
            //给数组排序(升序)
            Arrays.sort(keyArray);
            //因为String拼接效率会很低的，所以转用StringBuffer保证线程安全
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < keyArray.length; i++) {
                // 参数值为空，则不参与签名 这个方法trim()是去空格
                if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                    sb.append(keyArray[i]).append("<=>").append(String.valueOf(map.get(keyArray[i])).trim());
                }
                if(i != keyArray.length-1){
                    sb.append("<,>");
                }
            }
            return sb.toString();
        }

        /**
         *
         * String转map
         * @param str
         * @return
         */
        private ArrayMap<String,Object> getStringToMap(String str){
            Log.e("转",str);
            //创建Map对象
            ArrayMap<String, Object> map = new ArrayMap<String, Object>();
            if(str!=null) {
                //根据逗号截取字符串数组
                String[] str1 = str.split("<,>");
                //循环加入map集合
                for (int i = 0; i < str1.length; i++) {
                    //根据":"截取字符串数组
                    String[] str2 = str1[i].split("<=>");
                    //str2[0]为KEY,str2[1]为值
                    map.put(str2[0], str2[1]);
                }
            }
            return map;
        }
    }
}
