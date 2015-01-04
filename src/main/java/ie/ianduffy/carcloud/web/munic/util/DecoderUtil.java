package ie.ianduffy.carcloud.web.munic.util;

import org.apache.commons.codec.binary.Base64;

public class DecoderUtil {

    private DecoderUtil() {
    }

    public static String decodeToString(String encrypted) {
        byte[] decrypted = Base64.decodeBase64(encrypted);
        StringBuilder result = new StringBuilder();
        for(int i=0; i < decrypted.length; i++) {
            result.append((char) decrypted[i]);
        }
        return result.toString();
    }

    public static int decodeToInt(String encrypted) {
        byte[] decrypted = Base64.decodeBase64(encrypted);
        int result = 0;
        for(int i=0; i < decrypted.length; i++) {
            result = result << 8;
            result += decrypted[i];
        }
        return result;
    }

    public static boolean decodeToBoolean(String encrypted) {
        byte[] decrypted = Base64.decodeBase64(encrypted);
        return decrypted[0] == 1 ? true : false;
    }

}

