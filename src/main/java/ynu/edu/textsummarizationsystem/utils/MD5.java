package ynu.edu.textsummarizationsystem.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5{
    private String password;


    //test function
    public static void main(String[] args) {
        String password = "admin3";
        MD5 md5 = new MD5("admin3");
        String md5Password = md5.getMD5();
        System.out.println("原始密码：" + password);
        System.out.println("MD5加密后的密码：" + md5Password);
    }

    public  String getMD5() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public MD5(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
