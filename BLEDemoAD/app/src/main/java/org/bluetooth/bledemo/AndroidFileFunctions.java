package org.bluetooth.bledemo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.KeyStore;

import android.content.Context;
import android.util.Log;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class AndroidFileFunctions {

    public static String getFileValue(String fileName, Context context) {
        try {
            StringBuffer outStringBuf = new StringBuffer();
            String inputLine = "";
            /*
             * We have to use the openFileInput()-method the ActivityContext
             * provides. Again for security reasons with openFileInput(...)
             */
            FileInputStream fIn = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader inBuff = new BufferedReader(isr);
            while ((inputLine = inBuff.readLine()) != null) {
                outStringBuf.append(inputLine);
                outStringBuf.append("\n");
            }
            inBuff.close();
            return outStringBuf.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean appendFileValue(String fileName, String value,
            Context context) {
        return writeToFile(fileName, value, context, Context.MODE_APPEND);
    }

    public static boolean setFileValue(String fileName, String value,
            Context context) {
        //return writeToFile(fileName, value, context, Context.MODE_WORLD_READABLE);MODE_PRIVATE
        return writeToFile(fileName, value, context, Context.MODE_PRIVATE);

    }

    public static boolean writeToFile(String fileName, String value,
            Context context, int writeOrAppendMode) {
        // just make sure it's one of the modes we support
        //if (writeOrAppendMode != Context.MODE_WORLD_READABLE
        //        && writeOrAppendMode != Context.MODE_WORLD_WRITEABLE
        //        && writeOrAppendMode != Context.MODE_APPEND) {
        //    return false;
        //}
        try {
            /*
             * We have to use the openFileOutput()-method the ActivityContext
             * provides, to protect your file from others and This is done for
             * security-reasons. We chose MODE_WORLD_READABLE, because we have
             * nothing to hide in our file
             */
            FileOutputStream fOut = context.openFileOutput(fileName,
                    writeOrAppendMode);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            // Write the string to the file
            osw.write(value);
            // save and close
            osw.flush();
            osw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static void deleteFile(String fileName, Context context) {
        context.deleteFile(fileName);
    }

    // By Jason
    public static boolean createFile(Context context) {
        //File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");
        //File file = new File(context.getFilesDir().toString() + File.separator + "test.txt");
        File tmpFile = new File(context.getFilesDir().toString(), "key.store");
        try {
            tmpFile.createNewFile();
            //write the bytes in file
            Log.e("H2BT","FILE CREATE ... ");
            Log.e("H2BT","FILE CREATE ... " + context.getFilesDir().toString());
            Log.e("H2BT","FILE CREATE ... " + tmpFile);
            Log.e("H2BT","FILE CREATE ... " + tmpFile.exists());
            //acconnect
            //byte[] data1={1,1,0,0};
            byte[] data1={'a', 'c', 'c', 'o', 'n', 'n', 'e', 'c', 't'};
            if(tmpFile.exists())
            {
                Log.e("H2BT","FILE CREATE ... EXISTS");
                //InputStream is2 = new FileInputStream(file);
                OutputStream fo = new FileOutputStream(tmpFile);
                fo.write(data1);
                fo.close();
                System.out.println("file created: "+tmpFile);
                //url = upload.upload(file);
            }
        } catch (IOException e) {
            return false;
        }

        //Log.e("H2BT","KEY STORE --- ... HAHA " + KeyStore.getDefaultType());
        //write the bytes in file


//deleting the file
        //tmpFile.delete();
        System.out.println("file deleted");
        return true;
    }

    //private static C0512 m605(ByteBuffer byteBuffer) {
    private static void m605(ByteBuffer byteBuffer, byte[] xParser, Context ctx) {
        //C0512 c0512 = new C0512();
        //final
        int f436 = 0;
        byte[] f437 = null;
        try {
            //c0512.f436 = byteBuffer.getShort() & 65535;
            f436 = byteBuffer.getShort() & 65535;
            byte[] bArr = new byte[(byteBuffer.getShort() & 65535)];
            byteBuffer.get(bArr);
            //c0512.f437 = bArr;
            f437 = bArr;
            Log.e("H2BT","M605 ... ??? == " + f436);
            Log.e("H2BT","M605 ... " + f437 + " - buffer len = " + bArr.length);
            for (int k=0; k<bArr.length; k++) {
                Log.e("H2BT","F437 ... " + k + " - " + bArr[k]);
            }
            //return c0512;
            mo680(xParser, ctx);
            return ;
        } catch (Exception e) {
            //return null;
            return ;
        }
    }

    //public final void mo680() {
    private static void mo680(byte[] xParser, Context ctx) {
        ArrayList arrayList = null;
        //if (this.f605 == null) {
        //    m329(new C0486(this.f370, 5, "Invalid challenge buffer"));
        //    return;
        //}
        Log.e("H2BT","MO680 ... CALL ??");
        //PrivateKey xPrivateKey = C0641.m592();
        PrivateKey xPrivateKey = C0641.m593(ctx);
        Log.e("H2BT","MO680 ... " + xPrivateKey);
        if (xPrivateKey == null) {
            //m329(new C0486(this.f370, 6, "Invalid Private Key"));
            //return;
        }
        byte[] sign;
        //C0642 c0642 = new C0642();
        try {
            Log.e("H2BT","MO680 ... TRY ..." );
            //byte[] bArr = this.f605;
            //byte[] bArr = {0, 0, 0, 0};
            Signature instance = Signature.getInstance("SHA256withRSA");
            instance.initSign(xPrivateKey);
            //instance.update(bArr);
            instance.update(xParser);

            sign = instance.sign();
            Log.e("H2BT","MO680 ... TRY ..." + sign);
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
            //m329(new C0486(this.f370, 7, "Failed to Sign challenge"));
            //return;
        }
        //for (int j=0; j<sign.length; j++) {
        //    Log.e("H2BT","MO680 SIGN ..." + j + " "+ sign[j] );
        //}
        //CoN.m224(sign);

        //Object ˊ2 = C0562.m455(new C0587(sign));
        byte [] xdata2 = m455(new C0587(sign));
        if (!(xdata2 == null || xdata2.length == 0)) {
            List arrayList2 = new ArrayList();
            int length = xdata2.length;
            int i = length / 18;
            if (length % 18 != 0) {
                i++;
            }
            int i2 = length;
            length = 0;
            while (length < i) {
                int min = Math.min(i2, 18) + 2;
                byte[] obj = new byte[min];
                obj[0] = (byte) (length + 1);
                obj[1] = (byte) i;
                System.arraycopy(xdata2, length * 18, obj, 2, min - 2);
                arrayList2.add(obj);
                length++;
                i2 -= 18;
                Log.e("H2BT","MO680 ARRAY!! ..." + length + " " + obj[2] + " " + obj[3] + " " + obj[4]);
            }

            Log.e("H2BT","MO680 MAYBE GOOD!! ..." + " " );
            //arrayList = arrayList2;
        }
        //this.f606 = arrayList;
        //m484();

    }

    /* renamed from: ˊ */
    public static byte[] m455(C0569 c0569) {
    //public static byte[] m455() {
        int i = 0;
        if (c0569 == null) {
            return null;
        }
        C0512[] c0512Arr = c0569.f527 != null ? (C0512[]) c0569.f527.toArray(new C0512[0]) : null;
        List<byte[]> arrayList = new ArrayList();
        if (c0512Arr == null) {
            return null;
        }
        int length = c0512Arr.length;
        int i2 = 0;
        while (i2 < length) {
            //Object array;
            byte [] array = null;
            C0512 c0512 = c0512Arr[i2];
            if (c0512.f437 != null) {
                ByteBuffer allocate = ByteBuffer.allocate(c0512.f437.length + 4);
                allocate.putShort((short) c0512.f436);
                allocate.putShort((short) c0512.f437.length);
                allocate.put(c0512.f437);
                array = allocate.array();
            } else {
                array = null;
            }
            if (array == null) {
                i = -1;
                break;
            }
            //int length2 = array.length + i;
            int length2 = array.length + i;
            arrayList.add(array);
            i2++;
            i = length2;
        }
        if (i == -1) {
            return null;
        }
        int i3 = (i + 6) + 2;
        ByteBuffer allocate2 = ByteBuffer.allocate(i3);
        allocate2.order(ByteOrder.BIG_ENDIAN);
        allocate2.putShort((short) c0569.f528);
        allocate2.putShort((short) c0512Arr.length);
        allocate2.putShort((short) i);
        for (byte[] put : arrayList) {
            allocate2.put(put);
        }
        allocate2.putShort((short) C0636.rocheCrcCalculate(allocate2.array(), i3 - 2));
        return allocate2.array();
    }
/*

    public static byte[] m456(C0569 c0569) {
        Object ˊ = C0562.m455(c0569);
        if (ˊ == null || ˊ.length <= 0 || ˊ.length > 18) {
            return null;
        }
        Object obj = new byte[(ˊ.length + 2)];
        obj[1] = 1;
        obj[0] = 1;
        System.arraycopy(ˊ, 0, obj, 2, obj.length - 2);
        return obj;
    }
*/
/*
    private static C0512 m605(ByteBuffer byteBuffer) {
        C0512 c0512 = new C0512();
        try {
            c0512.f436 = byteBuffer.getShort() & 65535;
            byte[] bArr = new byte[(byteBuffer.getShort() & 65535)];
            byteBuffer.get(bArr);
            c0512.f437 = bArr;
            Log.e("H2BT","M605 ... 1");
            return c0512;
        } catch (Exception e) {
            return null;
        }

    }
*/
    //public final C0553 mo727(byte[] bArr) {
    public static void mo727(byte[] bArr, Context context) {
        Log.e("H2BT","MO727 ... LEN = " + bArr.length);
        if (bArr == null || bArr.length < 14) {
            //return null;
            Log.e("H2BT","MO727 ... 1");
            return;
        }
        ByteBuffer wrap;
        if (bArr != null) {
            wrap = ByteBuffer.wrap(bArr);
            wrap.order(ByteOrder.BIG_ENDIAN);
            Log.e("H2BT","MO727 ... 2");
        } else {
            wrap = null;
            Log.e("H2BT","MO727 ... 3");
        }
        if ((wrap.getShort() & 65535) != 61444 || (wrap.getShort() & 65535) != 1 || (wrap.getShort() & 65535) != 6) {
            //return null;
            Log.e("H2BT","MO727 ... 4 == " + wrap.getShort());
            //return;
        }
        m605(wrap, bArr, context);

        //C0512 ˊ = C0647.m605(wrap);
        //return new C0610(ˊ.f436, ˊ.f437);
        Log.e("H2BT","MO727 ... ByteBuffer == ");
        ByteBuffer byteBuffer;
        byte [] xbArr = {1, 2, 3, 4};
        ByteBuffer xwrap = ByteBuffer.wrap(xbArr);
        xwrap.order(ByteOrder.BIG_ENDIAN);
        byteBuffer = xwrap;

        ByteArrayOutputStream f713 = new ByteArrayOutputStream();
        if (f713 != null) {
            f713.write(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
        }

        try {
            f713.flush();
            byte[] toByteArray = f713.toByteArray();
            //if (this.f712 != null) {
            //    this.f712.mo691(toByteArray, true);
            //}
            for (int m=0; m<toByteArray.length; m++) {
                Log.e("H2BT","MO727 ... ByteBuffer == " + m + " " + toByteArray[m]);
            }
        } catch (IOException e2) {
            e2.getMessage();
        }


    }

    public static C0553 c0664mo727(byte[] bArr, Context context) {
    //public final C0553 c0664mo727(byte[] bArr) {
        Log.e("H2BT","C0664MO727 ... 1");
        //return null;
        if (bArr == null) {
            return null;
        }
        ByteBuffer wrap = null;
        if (bArr != null) {
            Log.e("H2BT","C0664MO727 ... 2");
            try {
                Log.e("H2BT","C0664MO727 ... 3");
                //ByteBuffer
                wrap = ByteBuffer.wrap(bArr);
                wrap.order(ByteOrder.BIG_ENDIAN);
            } catch (Exception e) {
                return null;
            }
        }


        if ((wrap.getShort() & 65535) == 61443) { // FF03
            Log.e("H2BT","C0664MO727 ... ??? == FF03");
            //    return null;
        }

        if ((wrap.getShort() & 65535) == 1) {
            Log.e("H2BT","C0664MO727 ... ??? == 1");
            //    return null;
        }
        wrap.getShort();


        C0512 c0512 = ifm453(wrap);

        Log.e("H2BT","C0664_MO727 ... " + c0512.f436);
        Log.e("H2BT","C0664_MO727 ... " + wrap.remaining());
        Log.e("H2BT","C0664_MO727 ... " + C0636.rocheCrcCalculate(bArr, bArr.length - 2));

        Log.e("H2BT","C0664_MO727 ... D_LEN = " + (bArr.length - 2));
        //Log.e("H2BT","C0664MO727 ... " + (wrap.getShort() & 65535));

        C0553 xfin = new C0622(c0512.f437);

        Log.e("H2BT","C0664_MO727 ... f528 = ?? " + xfin.f528);
        Log.e("H2BT","C0664_MO727 ... f528 = ?? " + xfin.f527);
        //Log.e("H2BT","C0664_MO727 ... f528 = ?? " + xfin.f527.get(0));

        C0512 endx = xfin.f527.get(0);
        Log.e("H2BT","C0664_MO727 ... f528 = ?? " + endx.f436);
        //for (int dx=0; dx<endx.f437.length; dx++) {
        //    Log.e("H2BT","C0664_MO727 ... C0512 = ?? "+ dx + " == " + endx.f437[dx]);
        //}

        //for (int j=0; j<c0512.f437.length; j++) {
        //    Log.e("H2BT","C0664_MO727 ... JJ = ?? " + j + " GOOD = " + c0512.f437[j]);
        //}

        C0622 xy = new C0622(c0512.f437);

        for (int j=0; j<c0512.f437.length; j++) {
            Log.e("H2BT","C0664_MO727 ... JJ = ?? " + j +  ""+ xy.f580.f437[j] + " GOOD = " + c0512.f437[j]);
        }

        // To Do ...
        mo680(c0512.f437, context);

        return (new C0622(c0512.f437));
        //return null;
        //return (ˊ.f436 == 17 && wrap.remaining() == 2 && C0636.m572(bArr, bArr.length - 2) == (wrap.getShort() & 65535)) ? new C0622(ˊ.f437) : null;
    }


    // 收資料
    static private ByteArrayOutputStream f713;
    static private int f714 = 1;

    public static void Testm573(byte[] bArr, Context ctx) {
        Log.e("H2BT","TestM573 ... ");
        ByteBuffer byteBuffer;
        if (bArr != null) {
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            wrap.order(ByteOrder.BIG_ENDIAN);
            byteBuffer = wrap;
        } else {
            byteBuffer = null;
        }
        if (byteBuffer != null && bArr.length > 2) {
            byte b = byteBuffer.get();
            byte b2 = byteBuffer.get();
            if (f714 == b) {
                f714++;
                if (b == (byte) 1) {
                    if (f713 != null) {
                        try {
                            f713.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    f713 = new ByteArrayOutputStream();
                }
                if (f713 != null) {
                    f713.write(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
                }
                if (b == b2 && f713 != null) {
                    try {
                        f713.flush();
                        byte[] toByteArray = f713.toByteArray();
                        Log.e("H2BT","TestM573 ... " + toByteArray.length);
                        //for(int k=0; k<toByteArray.length; k++) {
                        //    Log.e("H2BT","TestM573 ... SRC " +" " +k +" "+ toByteArray[k]);
                        //}
                        // 有三種處理方式
                        // 1.
                        //c0647mo727(toByteArray);
                        c0664mo727(toByteArray, ctx);

                        //if (this.f712 != null) {
                        //    this.f712.mo691(toByteArray, true);
                        //}
                    } catch (IOException e2) {
                        e2.getMessage();
                    }
                }
            //} else if (this.f712 != null) {
            //    this.f712.mo691(null, false);
            }
        //} else if (this.f712 != null) {
        //    this.f712.mo691(null, false);
        }
    }

    // C0647
    private static C0512 c0647m605(ByteBuffer byteBuffer) {
        C0512 c0512 = new C0512();
        try {
            c0512.f436 = byteBuffer.getShort() & 65535;
            byte[] bArr = new byte[(byteBuffer.getShort() & 65535)];
            byteBuffer.get(bArr);
            c0512.f437 = bArr;
            return c0512;
        } catch (Exception e) {
            return null;
        }
    }

    // C0647
    /* renamed from: ˊ */
    public static C0553 c0647mo727(byte[] bArr) {
        Log.e("H2BT","c0647mo727 ... BEGIN ");

        if (bArr == null || bArr.length < 14) {
            return null;
        }
        ByteBuffer wrap;
        if (bArr != null) {
            wrap = ByteBuffer.wrap(bArr);
            wrap.order(ByteOrder.BIG_ENDIAN);
        } else {
            Log.e("H2BT","c0647mo727 ... null ");
            wrap = null;
        }

        if ((wrap.getShort() & 65535) == 61444) { // FF04
            Log.e("H2BT","c0647mo727 ... FF04 ");
            //    return null;
        }
        if ((wrap.getShort() & 65535) == 1 ) {
            Log.e("H2BT","c0647mo727 ... 1 ");
            //    return null;

        }
        if ((wrap.getShort() & 65535) == 6) {
            Log.e("H2BT","c0647mo727 ... 6 ");
            //    return null;
        }

        Log.e("H2BT","c0647mo727 ... END ");


        C0512 xxd = c0647m605(wrap);
        return new C0610(xxd.f436, xxd.f437);
        //return null;
        //C0512 ˊ = c0647m605(wrap);
        //return new C0610(ˊ.f436, ˊ.f437);
    }

    public static C0512 ifm453(ByteBuffer byteBuffer) {
        C0512 c0512 = new C0512();
        try {
            c0512.f436 = byteBuffer.getShort() & 65535;
            byte[] bArr = new byte[(byteBuffer.getShort() & 65535)];
            byteBuffer.get(bArr);
            c0512.f437 = bArr;
            Log.e("H2BT","IFM453 ... ??? == "+ c0512.f436 );
            Log.e("H2BT","IFM453 ... LEN == "+ c0512.f437.length );
            return c0512;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}


/*
    public static boolean Encode() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] key = {1, 2, 3, 4, 5, 6};
        //SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
        SecretKeyFactory kf = SecretKeyFactory.getInstance("AES/ECB/PKCS5Padding");


        try {
            DESKeySpec keySpec = new DESKeySpec(key); // byte[] key
            try {
                SecretKey skey = kf.generateSecret(keySpec);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null, "ksPassword".toCharArray());
        } catch (IOException e) {
            return false;
        }


        PasswordProtection pass = new KeyStore.PasswordProtection(
                "entryPassword".toCharArray());
        KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(skey);
        ks.setEntry("keyAlias", skEntry, pass);

        FileOutputStream fos = ctx.getApplicationContext().openFileOutput("bs.keystore",
                Context.MODE_PRIVATE);
        ks.store(fos, ksPassword);
        fos.close();
        return true;
    }

    public static Key Decode(Context ctx) {

        try {
            Key localKey = null;
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream fis = ctx.getApplicationContext().openFileInput("bs.keystore");
            ks.load(fis, ksPassword);
        //} catch (FileNotFoundException e) {
        //    ks.load(null, ksPassword);
        //}
            return localKey;
        } catch (Exception e4) {
            //e32 = e4;
            //throw new RuntimeException(e32);
            throw new RuntimeException(e4);
        }
    }
    */