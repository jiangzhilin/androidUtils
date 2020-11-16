package com.linzi.utilslib.processCall.utils


import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class OpenSSlUtils {
    companion object{
        /**
         * aes明文密码加密
         */
        fun encrypt0(input: String,key:String): String {
            //1. 创建CBC模式的cipher对象
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            //2. 初始化cipher
            //自己指定的秘钥
            /**
             * @author linzi
             * @date 2020/4/3 14:29
             * @context 完整新加密
             */
            val keySpec = SecretKeySpec(key.toByteArray(),"AES")
            if(key.length<=16) {
                val iv = IvParameterSpec(key.toByteArray())
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
            }else{
                val iv = IvParameterSpec(key.substring(0,16).toByteArray())
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
            }
            //3. 加密和解密
            val encrypt = cipher.doFinal(input.toByteArray())
            return Base64.encode(encrypt)
        }
        /**
         * aes MD5密码加密
         */
        fun encrypt1(input: String,key:String): String {
            //1. 创建CBC模式的cipher对象
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            //2. 初始化cipher
            //自己指定的秘钥
            /**
             * @author linzi
             * @date 2020/4/3 14:29
             * @context MD5秘钥截取值加密
             */
            val keySpec = SecretKeySpec(MD5.md5(key).toLowerCase().substring(0,32).toByteArray(),"AES")
            val iv= IvParameterSpec(MD5.md5(key).toLowerCase().substring(0,16).toByteArray())


            cipher.init(Cipher.ENCRYPT_MODE, keySpec,iv)
            //3. 加密和解密
            val encrypt = cipher.doFinal(input.toByteArray())
            return Base64.encode(encrypt)
        }

        /**
         * aes 明文密码解密
         */
        fun decrypt0(input: String,key:String): String {
            //1. 创建CBC模式的cipher对象
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            //2. 初始化cipher
            /**
             * @author linzi
             * @date 2020/4/3 14:30
             * @context 完整秘钥加密数据解密
             */
            val keySpec = SecretKeySpec(key.toByteArray(),"AES")
            if(key.length<=16) {
                val iv = IvParameterSpec(key.toByteArray())
                cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
            }else{
                val iv = IvParameterSpec(key.substring(0,16).toByteArray())
                cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
            }
            //3. 加密和解密
            val decrypt = cipher.doFinal(Base64.decode(input))
            return String(decrypt)
        }

        /**
         * aes MD5密码解密
         */
        fun decrypt1(input: String,key:String): String {
            //1. 创建CBC模式的cipher对象
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            //2. 初始化cipher
            /**
             * @author linzi
             * @date 2020/4/3 14:30
             * @context md5秘钥解密
             */
            val keySpec = SecretKeySpec(MD5.md5(key).toLowerCase().substring(0,32).toByteArray(),"AES")
            val iv= IvParameterSpec(MD5.md5(key).toLowerCase().substring(0,16).toByteArray())
            cipher.init(Cipher.DECRYPT_MODE, keySpec,iv)
            //3. 加密和解密
            val decrypt = cipher.doFinal(Base64.decode(input))
            return String(decrypt)
        }
    }
}