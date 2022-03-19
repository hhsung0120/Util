package com.heeseong.util.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {
    private static final String TRANSFORM = "AES/CBC/PKCS5Padding";
    private static final String STATIC_KEY = " 16bytes"; // must be 16bytes

    /*
     * 암호화 (양방향)
     */
    public static String encrypt(String plainText) throws Exception {
        if ("".equals(StringUtil.isEmpty(plainText))) {
            return "";
        }
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        byte[] raw = CryptoUtil.STATIC_KEY.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(TRANSFORM);

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return asHex(encrypted);
    }

    /*
     * 복호화 (양방향)
     */
    public static String decrypt(String cipherText) throws Exception {
        if ("".equals(StringUtil.isEmpty(cipherText))) {
            return "";
        }
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        byte[] raw = CryptoUtil.STATIC_KEY.getBytes("UTF-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(TRANSFORM);

        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] original = cipher.doFinal(fromString(cipherText));
        String originalString = new String(original);
        return originalString;
    }

    private static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if ((buf[i] & 0xff) < 0x10)
                strbuf.append("0");

            strbuf.append(Long.toString(buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    private static byte[] fromString(String hex) {
        int len = hex.length();
        byte[] buf = new byte[((len + 1) / 2)];

        int i = 0, j = 0;
        if ((len % 2) == 1)
            buf[j++] = (byte) fromDigit(hex.charAt(i++));

        while (i < len) {
            buf[j++] = (byte) ((fromDigit(hex.charAt(i++)) << 4) | fromDigit(hex.charAt(i++)));
        }
        return buf;
    }

    private static int fromDigit(char ch) {
        if (ch >= '0' && ch <= '9')
            return ch - '0';
        if (ch >= 'A' && ch <= 'F')
            return ch - 'A' + 10;
        if (ch >= 'a' && ch <= 'f')
            return ch - 'a' + 10;

        throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
    }

    /*
     * 입력 패스워드 값 암호화 (단방향)
     */
    public static String encryptPassword(String enteredPW) {
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm("SHA-512");
        String encryptedPassword = passwordEncryptor.encryptPassword(enteredPW);
        return encryptedPassword;
    }

    /*
     * 패스워드 값 비교
     * submittedPassword : 입력 패스워드 값
     * encryptedPassword : DB에 저장된 패스워드 값
     */
    public static boolean checkPassword(String submittedPassword, String encryptedPassword) {
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm("SHA-512");
        return passwordEncryptor.checkPassword(submittedPassword, encryptedPassword);
    }
}
