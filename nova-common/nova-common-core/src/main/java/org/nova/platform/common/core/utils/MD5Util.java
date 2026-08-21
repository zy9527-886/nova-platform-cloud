package org.nova.platform.common.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /** */
    /**
     * The hex digits.
     */
    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /** */
    /**
     * Transform the byte array to hex string.
     *
     * @param b
     * @return
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /** */
    /**
     * Transform a byte to hex string.
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;

        // get the first four bit
        int d1 = n / 16;

        // get the second four bit
        int d2 = n % 16;

        return hexDigits[d1] + hexDigits[d2];
    }

    /** */
    /**
     * Get the MD5 encrypt hex string of the origin string. <br/>
     * The origin string won't validate here, so who use the API should validate
     * by himself.
     *
     * @param origin
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String MD5Encode(String origin) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return byteArrayToHexString(md.digest(origin.getBytes()));
    }

    public static String MD5Encode16(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(origin.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString().substring(8, 24);// 16位的加密

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}