package ie.ianduffy.carcloud.web.munic.util;

import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;

public class DecoderUtil {

    private DecoderUtil() {
    }

    public static boolean decodeToBoolean(String encrypted) {
        byte[] decrypted = Base64.decodeBase64(encrypted);
        return decrypted[0] == 1 ? true : false;
    }

    public static int decodeToInt(String encrypted) {
        byte[] decrypted = Base64.decodeBase64(encrypted);
        int result = new BigInteger(decrypted).intValue();
        return result;
    }

    public static String decodeToString(String encrypted) {
        byte[] decrypted = Base64.decodeBase64(encrypted);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < decrypted.length; i++) {
            result.append((char) decrypted[i]);
        }
        return result.toString();
    }

}

