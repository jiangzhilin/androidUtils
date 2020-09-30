package com.linzi.utilslib.utils.nfc;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author linzi
 * @date 2019/11/18
 */
public class HexUtils {
    /**
     * 16进制数字字符集
     */
    private static String hexString="0123456789ABCDEF";

    /**
     * 转化字符串为十六进制编码
     */
    public static String toHexString(String s){
        String str="";
        for (int i=0;i<s.length();i++){
            int ch = (int)s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 转化十六进制编码为字符串
     */
    public static String toStringHex(String s){
        byte[] baKeyword = new byte[s.length()/2];
        for(int i = 0; i < baKeyword.length; i++){
            try{
                baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        try{
            s = new String(baKeyword, "utf-8");//UTF-16le:Not
        }catch (Exception e1){
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    public static String encode(String str){
        //根据默认编码获取字节数组
        byte[] bytes=str.getBytes();
        StringBuilder sb=new StringBuilder(bytes.length*2);
        //将字节数组中每个字节拆解成2位16进制整数
        for(int i=0;i<bytes.length;i++){
            sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
            sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
        }
        return sb.toString();
    }
    /**
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String decode(String bytes)
    {
        ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
        //将每2位16进制整数组装成一个字节
        for(int i=0;i<bytes.length();i+=2)
            baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));
        return new String(baos.toByteArray());
    }

    /**
     * 将字符串转换成16进制编码字符串
     *<p>Title: ToHexString</p>
     *<p>Description: </p>
     *@param str
     *@return
     */
    public static String ToHexString(String str) {
        String str1 = "";
        try {
            byte[] b = str.getBytes("gbk");
            int i = 0;
            int max = b.length;
            for (; i < max; i++) {
                str1 = str1 + Integer.toHexString(b[i] & 0xFF);
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("异常信息ToHexString" + e.getMessage());
        }
        return str1;
    }
    /**
     * 16进制编码字符串转字节数组
     *<p>Title: toByteArray</p>
     *<p>Description: </p>
     *@param hexString
     *@return
     */
    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
    /**
     * 字节数组转16进制编码字符串
     *<p>Title: toHexString</p>
     *<p>Description: </p>
     *@param byteArray
     *@return
     */
    public static String toHexString(byte[] byteArray) {
        String str = null;
        if (byteArray != null && byteArray.length > 0) {
            StringBuffer stringBuffer = new StringBuffer(byteArray.length);
            for (byte byteChar : byteArray) {
                stringBuffer.append(String.format("%02X", byteChar));
            }
            str = stringBuffer.toString();
        }
        return str;
    }

    /**
     * 字节数组转字符串
     *<p>Title: ByteArrayDecode</p>
     *<p>Description: </p>
     *@param bytes
     *@return
     */
    public static String ByteArray2String(byte[] bytes) {
        String hexStr = toHexString(bytes);
        return decode(hexStr);
    }

    public static void main(String[] args) {
        String str = "你好asbc121f,smf,m,ewflwf健康的";
        String sljzstr = encode(str);
        System.out.println("字符串转16进制数字____"+sljzstr);
        System.out.println("16进制数字转成字符串____"+decode(sljzstr));
        System.out.println("字节数组转字符串:"+ByteArray2String(str.getBytes()));
        int a = 1<<4|2;
        System.out.println("a="+a);
    }
}