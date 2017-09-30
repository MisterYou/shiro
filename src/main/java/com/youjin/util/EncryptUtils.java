package com.youjin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by train on 17/2/22.
 */
public class EncryptUtils {
    public EncryptUtils(){

    }

    /**
     * md5加密
     * @param data
     * @return
     */
    public static String md5(String data){

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(data.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        byte[] md5Bytes = md5.digest();
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;//转化成为16进制的字节
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    public static void main(String[] args) {
        String s=md5("杨渊rongxuntech123456");
        System.out.println(s);

    }
}
