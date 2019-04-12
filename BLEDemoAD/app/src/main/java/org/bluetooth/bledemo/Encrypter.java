//package com.roche.bluenileupgraderandroidcomponent;
package org.bluetooth.bledemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class Encrypter {
    private static final String ENCRYPTER_BASE_SEED = "encrypter_base_seed";
    private static final String SALT = "23lknrmab0vne0rvna034vnafdklngb3q4gmgna";
    private static final String SHARED_PREFERENCE_KEY = "Amazon_Settings";
    private final Cipher _decrypter;

    public static Encrypter GenerateEncrypter(Context context) {
        try {
            return new Encrypter(setupCipher(getKey(new File(context.getFilesDir().toString(), "key.store"), generatePassword(GetSeed(context.getSharedPreferences(SHARED_PREFERENCE_KEY, 0))))));
        } catch (Exception e) {
            return null;
        }
    }

    public static void AndroidGenerateEncrypter() {
        try {
            byte [] clear = {1, 2, 3, 4, 5, 6};
            Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            //Context context = new Context(this);
                    //getApplicationContext();
            //
            //File file = new File(context.getFilesDir().toString(), "key.store");
            //Log.e("H2BT","KEY FILE --- ... HAHA " + file);


            //Key localKey = null;
            //decryptCipher.init(2, localKey);

            //byte[] encrypted = decryptCipher.doFinal(clear);
            //int xLen = encrypted.length;
            //Log.e("H2BT","CRYPT ROCHE --- ... HAHA " + xLen);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            Log.e("H2BT","KEY STORE --- ... HAHA " + keyStore);
            Log.e("H2BT","KEY STORE --- ... HAHA " + KeyStore.getDefaultType());

            char[] dataToWriteBg = generatePassword(androidGetSeed("0"));
            int dLen = dataToWriteBg.length;
            Log.e("H2BT","CFG ROCHE --- ... HAHA " + dLen);
            for(int i=0; i<dLen; i++) {
                Log.e("H2BT","CFG ROCHE  " + i + " " + dataToWriteBg[i]);
            }
            //return new Encrypter(setupCipher(getKey(void , generatePassword(GetSeed()))));
        } catch (Exception e) {
            return;
        }
    }

    /*
    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
    */


    private static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        int length = hash.length;
        for (int i = 0; i < length; i++) {
            formatter.format("%02x", new Object[]{Byte.valueOf(hash[i])});
        }
        String getFormattedString = formatter.toString();
        formatter.close();
        return getFormattedString;
    }

    private static char[] generatePassword(String seed) throws NoSuchAlgorithmException {
        return byteArray2Hex(MessageDigest.getInstance("SHA1").digest(seed.getBytes())).toCharArray();
    }

    private static String androidGetSeed(String settings) {
        return settings + SALT;
    }

    private static String GetSeed(SharedPreferences settings) {
        Log.e("H2BT","GET SEED --- ... ");
        String seed = settings.getString(ENCRYPTER_BASE_SEED, null) + SALT;
        int seedLen = seed.length();
        Log.e("H2BT","GET SEED --- ... " + seed);
        for(int i=0; i<seedLen; i++) {
            //Log.e("H2BT","GET SEED --- ... " + i + " " +);
        }

        return settings.getString(ENCRYPTER_BASE_SEED, null) + SALT;
    }

    private Encrypter(Cipher decrypter) {
        this._decrypter = decrypter;
    }

    private static Key androidGetKey(File keyFile, char[] password) {
        Exception e;
        Throwable th;
        InputStream is = null;
        boolean xy = true;
        Log.e("H2BT","KEY FILE --- ... KEY ");
        try {
            Key localKey = null;
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //if (keyFile.exists()) {
            if(xy) {
                //InputStream is2 = new FileInputStream(keyFile);
                try {
                    //j keyStore.load(is2, password);
                    localKey = keyStore.getKey("acconnect", password);
                    //is2.close();
                    //is = is2;
                } catch (Exception e2) {
                    e = e2;
                    //is = is2;
                    try {
                        throw new RuntimeException(e);
                    } catch (Throwable th2) {
                        th = th2;
                        //if (is != null) {
                        //    try {
                        //        is.close();
                        //    } catch (Exception e3) {
                        //        throw new RuntimeException(e3);
                        //    }
                        //}
                        //throw th;
                        throw th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    //is = is2;
                    //if (is != null) {
                    //    is.close();
                    //}
                    throw th3;
                }
            }
            //if (is != null) {
            //    try {
            //        is.close();
            //    } catch (Exception e32) {
            //        throw new RuntimeException(e32);
            //    }
            //}
            return localKey;
        } catch (Exception e4) {
            //e32 = e4;
            //throw new RuntimeException(e32);
            throw new RuntimeException(e4);
        }
    }

    private static Key getKey(File keyFile, char[] password) {
        Exception e;
        Throwable th;
        InputStream is = null;
        Log.e("H2BT","GET KEY --- ... ");
        byte[] keyBuffer = new byte[64];

        int pwLen = password.length;
        for (int i=0; i<pwLen; i++) {
            Log.e("H2BT"," PW --- "+ i + " " +password[i]);
        }
        try {
            Key localKey = null;
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            Log.e("H2BT","GET KEY_STRORE --- ... " + keyStore);
            Log.e("H2BT","GET KEY_STRORE --- ... " + KeyStore.getDefaultType());
            if (keyFile.exists()) {
                InputStream is2 = new FileInputStream(keyFile);
                Log.e("H2BT","GET KEY (IS2) --- ... " + is2);
                int bytesread = is2.read(keyBuffer, 0, 4);
                for (int i=0; i<bytesread; i++) {
                    Log.e("H2BT","BY --- ... " + i + " " + keyBuffer[i]);
                }

                try {
                    keyStore.load(is2, password);
                    Log.e("H2BT","GET KEY (IS2 ===) --- ... " + is2);
                    localKey = keyStore.getKey("acconnect", password);
                    //localKey = keyStore.getKey(null, password);
                    is2.close();
                    is = is2;
                } catch (Exception e2) {
                    Log.e("H2BT","KEY ERROR --- ... e2 " + e2);
                    e = e2;
                    is = is2;
                    try {
                        throw new RuntimeException(e);
                    } catch (Throwable th2) {
                        th = th2;
                        if (is != null) {
                            try {
                                is.close();
                            } catch (Exception e3) {
                                Log.e("H2BT","KEY ERROR --- e3 ... " + e3);
                                throw new RuntimeException(e3);
                            }
                        }
                        //throw th;
                        Log.e("H2BT","KEY ERROR  th2 --- ... " + th2);
                        throw th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    is = is2;
                    if (is != null) {
                        is.close();
                    }
                    Log.e("H2BT","KEY ERROR --- ...th3 " + th3);
                    throw th3;
                }
            } else {
                Log.e("H2BT","GET KEY --- ... FILE Not Existing");
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e32) {
                    throw new RuntimeException(e32);
                }
            }
            return localKey;
        } catch (Exception e4) {
            //e32 = e4;
            //throw new RuntimeException(e32);
            throw new RuntimeException(e4);
        }
    }

    private static Cipher setupCipher(Key localKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptCipher.init(2, localKey);
        return decryptCipher;
    }

    public synchronized String unpackString(String s) {
        String str;
        try {
            str = new String(this._decrypter.doFinal(Base64.decode(s, 0)));
        } catch (Exception e) {
            str = null;
        }
        return str;
    }
}
