package org.bluetooth.bledemo;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Enumeration;


public final class C0641 {

    private static PrivateKey f717 = null;

    private static final byte[] f718 = new byte[]{
            (byte) 126, (byte) 7, (byte) -60, (byte) -53,
            (byte) 19, (byte) -50, (byte) 50, (byte) -3,
            (byte) -31, (byte) -43, (byte) -13, (byte) 7,
            (byte) -15, (byte) -20, (byte) 0, (byte) -2,
            (byte) -18, (byte) 29, (byte) 20};

    private static byte[] testInfo =
            {

                    (byte) 0x54
                    , (byte) 0x50
                    , (byte) 0x22
                    , (byte) 0xE9
                    , (byte) 0xF6
                    , (byte) 0xC9
                    , (byte) 0xC3
                    , (byte) 0xF9
                    , (byte) 0x79
                    , (byte) 0xD0
                    , (byte) 0xD9
                    , (byte) 0x7B
                    , (byte) 0x98
                    , (byte) 0x36
                    , (byte) 0xF2
                    , (byte) 0xD2
                    , (byte) 0x9D
                    , (byte) 0x68
                    , (byte) 0xB4
                    , (byte) 0x41
                    , (byte) 0x7A
                    , (byte) 0x75
                    , (byte) 0x15
                    , (byte) 0xA8
                    , (byte) 0xF1
                    , (byte) 0x9F
                    , (byte) 0x02
                    , (byte) 0xF1
                    , (byte) 0x7B
                    , (byte) 0x66
                    , (byte) 0xF4
                    , (byte) 0x71
            };


    private static String m591(int i, int i2, int i3) {
        int i4 = i + 4;
        int i5 = 100 - (i3 * 3);
        int i6 = 16 - (i2 * 4);
        byte[] bArr = f718;
        byte[] bArr2 = new byte[i6];
        int i7 = 0;
        while (true) {
            bArr2[i7] = (byte) i5;
            i4++;
            if (i7 == i6 - 1) {
                //return new String(bArr2, 0);
                Log.e("H2BT","M591 ... STR = " +  new String(bArr2));
                return new String(bArr2);
            }
            i7++;
            i5 = (i5 - bArr[i4]) - 8;
        }
    }


    public static PrivateKey m592() {
        return f717;
    }


    public static PrivateKey m593(Context context) {
        Log.e("H2BT","M593 ... LEN = " + context);
        if (context == null) {
            return null;
        }
        if (f717 != null) {
            return f717;
        }
        Log.e("H2BT","M593 ... LEN = " + context);

        try {
            KeyStore instance = KeyStore.getInstance("pkcs12");
            InputStream open = context.getResources().getAssets().open("production.dat");
            byte b = f718[14];
            byte b2 = f718[14];
            instance.load(open, C0641.m591(b - 1, b2, b2).toCharArray());
            Enumeration aliases = instance.aliases();
            while (aliases.hasMoreElements()) {
                Log.e("H2BT","M593 ... OPEN = " + open);
                String str = (String) aliases.nextElement();
                if (instance.isKeyEntry(str)) {
                    Log.e("H2BT","ENUM ... VAL = " + str);
                    b = f718[14];
                    b2 = f718[14];
                    f717 = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(instance.getKey(str, C0641.m591(b - 1, b2, b2).toCharArray()).getEncoded()));
                    Log.e("H2BT","(pKey)f717 ... VAL = " + f717);
                    break;
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return f717;
    }


    public static void rocheEncodeTest(Context ctx) {
        //ArrayList arrayList = null;

        PrivateKey xPrivateKey = C0641.m593(ctx);

        if (xPrivateKey == null) {
            //return;
        }

        byte[] sign;
        byte[] bArr = testInfo;
        try {
            //Log.e("H2BT","MO680 ... TRY ... INFO_LEN = ?? " +  testInfo.length);

            Signature instance = Signature.getInstance("SHA256withRSA");
            instance.initSign(xPrivateKey);
            instance.update(bArr);
            for (int k = 0; k < bArr.length; k++) {
                Log.e("H2BT", "PASSWORD  ..." + k + " - " + bArr[k]);
            }

            sign = instance.sign();
            Log.e("H2BT", "MO680 ... TRY ..." + sign);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            sign = null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            sign = null;
        } catch (SignatureException e3) {
            e3.printStackTrace();
            sign = null;
        }
        if (sign == null) {
            //return;
        }

        Log.e("H2BT", "MO680 ... TRY ... SIGN_LEN = ?? " + sign.length);
        if (sign.length >= 256) {
            Log.e("H2BT", "MO680 ... TRY ... SIGN_D0 = ?? " + sign[0]);
            Log.e("H2BT", "MO680 ... TRY ... SIGN_D1 = ?? " + sign[1]);
            Log.e("H2BT", "MO680 ... TRY ... SIGN_DEnd1 = ?? " + sign[254]);
            Log.e("H2BT", "MO680 ... TRY ... SIGN_DEnd0 = ?? " + sign[255]);
        }

        //int crc = C0636.rocheCrcCalculate(sign, sign.length);
        //Log.e("H2BT","CRC HI ... " + ((crc & 0xFF00)>>8));
        //Log.e("H2BT","CRC LO ... " + (crc & 0xFF));
    }
}


