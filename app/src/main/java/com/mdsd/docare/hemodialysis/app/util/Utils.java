package com.mdsd.docare.hemodialysis.app.util;

/**
 *
 */

public class Utils {

    public static int bytesToInteger(byte high,byte low) {
        String highHexString = singleBytesToHexString(high);
        String lowHexString = singleBytesToHexString(low);
        return Integer.parseInt(String.format("%s%s",highHexString,lowHexString), 16);
    }
    public static String singleBytesToHexString(byte aSrc){
        StringBuilder stringBuilder = new StringBuilder();
        int v = aSrc & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
        String s = stringBuilder.toString().toUpperCase();
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
}
