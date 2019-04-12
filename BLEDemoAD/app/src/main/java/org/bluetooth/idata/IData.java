package org.bluetooth.idata;


import android.content.Context;
import android.util.Log;

import org.bluetooth.bledemo.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;


public class IData {
    //public int f436 = 0;
    //public byte[] f437 = null;

    static private ByteArrayOutputStream rocheVendorData;
    static private int packetTag = 1;
    static public boolean infoNeed = false;
    static public boolean wrCurrentTime = false;

    private static byte[] authInfo = null;
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

    //private List<byte[]> writeBuffer;
    private static ArrayList<byte[]> writeBuffer;

    /* creates  object */
    public IData() {
        //writeBuffer = new ArrayList<>();
    }

    public static void packetIndexInit() {
        packetTag = 1;
    }
    public static void packetInfoNeed() {infoNeed = true; }
    public static void setCurrentTimeState(boolean state) {wrCurrentTime = state; }



    public static boolean rocheVendorDataInput(byte[] bArr) {
        Log.e("H2BT","TestM573 - ROCHE DATA INPUT ... ");
        if (wrCurrentTime) {
            wrCurrentTime = false;
            for (int k=0; k<14; k++) {
                byte[] wTmp = getAuthCode(k);
                if (wTmp != null) {
                    for (int j=0; j<20; j++) {
                        //Log.e("H2BT","R-WC" + " = " + j + " - " + wTmp[j]);
                    }

                }
            }

            byte[] wxfTmp = getAuthCode(14);
            if (wxfTmp != null) {
                for (int j=0; j<18; j++) {
                    //Log.e("H2BT","R-WC" + " = " + j + " - " + wxfTmp[j]);
                }

            }


        }

        ByteBuffer byteBuffer;
        if (bArr != null) {
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            wrap.order(ByteOrder.BIG_ENDIAN);
            byteBuffer = wrap;
        } else {
            byteBuffer = null;
        }
        if (byteBuffer != null && bArr.length > 2) {
            byte bPkCurrent = byteBuffer.get();
            byte bPkTotal = byteBuffer.get();
            if (packetTag == bPkCurrent) {
                packetTag++;
                if (bPkCurrent == (byte) 1) {
                    if (rocheVendorData != null) {
                        Log.e("H2BT","Roche buf ... XXX");
                        try {
                            Log.e("H2BT","Roche buf ... ? = CLOSE OP STREAM");
                            rocheVendorData.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    rocheVendorData = new ByteArrayOutputStream();
                }
                if (rocheVendorData != null) {
                    Log.e("H2BT","Roche POSITION ... ? = " + byteBuffer.position());
                    Log.e("H2BT","Roche REMAIN ... ? = " + byteBuffer.remaining());
                    rocheVendorData.write(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
                }
                Log.e("H2BT","Roche V-Data ... ? = " + bPkCurrent);
                //Log.e("H2BT","Roche V-Data ... ? = " + bPkTotal);
                //Log.e("H2BT","Roche V-Data ... Data? = " + rocheVendorData);
                if (bPkCurrent == bPkTotal && rocheVendorData != null) {
                    Log.e("H2BT","Roche V-Data ... EQUAL");
                    try {
                        rocheVendorData.flush();
                        byte[] toByteArray = rocheVendorData.toByteArray();
                        Log.e("H2BT","Roche V-Data ... " + toByteArray.length);

                        packetEnd(toByteArray, bPkCurrent);
                        if (bPkCurrent == 3) {
                            if (infoNeed) {
                                infoNeed = false;
                                return true;
                            }
                            return false;
                        } else {
                            return false;
                        }

                        // 有三種處理方式
                        // 1.
                        //c0647mo727(toByteArray);
                        //c0664mo727(toByteArray, ctx);

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
        return false;
    }

    private static void packetEnd(byte[] data, byte bPk) {
        Log.e("H2BT","Roche V-Data ... CT ?? =" + bPk);
        switch (bPk) {
            case 1:
                break;

            case 2:
                CurrentTimeSync.c0662mo727(data);
                break;

            case 3:
                Log.e("H2BT","Roche V-Data ... WHAT ? =" + bPk);
                rocheAuthorizeData(data);
                break;
                default:
                    break;
        }
    }

    public static C0553 rocheAuthorizeData(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteBuffer wrap = null;
        if (bArr != null) {
            try {
                Log.e("H2BT", "Authorize Info Check ... ");
                wrap = ByteBuffer.wrap(bArr);
                wrap.order(ByteOrder.BIG_ENDIAN);
            } catch (Exception e) {
                return null;
            }
        }


        if ((wrap.getShort() & 65535) != 61443) { // FF03
            Log.e("H2BT", "C0664MO727 ... ??? == FF03");
            return null;
        }
        if ((wrap.getShort() & 65535) != 1) {
            Log.e("H2BT", "C0664MO727 ... ??? == 1");
            return null;
        }

        wrap.getShort();

        C0512 c0512 = rocheDataWrap(wrap);

        Log.e("H2BT", "AUTHO_INFO ... " + c0512.f436);
        Log.e("H2BT", "AUTHO_INFO ... " + wrap.remaining());
        Log.e("H2BT", "AUTHO_INFO ... " + C0636.rocheCrcCalculate(bArr, bArr.length - 2));

        Log.e("H2BT", "AUTHO_INFO ... D_LEN = " + (bArr.length - 2));

        C0553 xfin = new C0622(c0512.f437);

        Log.e("H2BT", "C0664_MO727 ... f528 = ?? " + xfin.f528);
        Log.e("H2BT", "C0664_MO727 ... f527 = ?? " + xfin.f527);
        //Log.e("H2BT","C0664_MO727 ... f528 = ?? " + xfin.f527.get(0));

        C0512 endx = xfin.f527.get(0);
        Log.e("H2BT", "C0664_MO727 ... f528 = ?? " + endx.f436);


        C0622 xy = new C0622(c0512.f437);

        //for (int j = 0; j < c0512.f437.length; j++) {
        //    Log.e("H2BT", "C0664_MO727 ... JJ = ?? " + j + "" + xy.f580.f437[j] + " GOOD = " + c0512.f437[j]);
        //}

        // To Do ...
        //mo680(c0512.f437, context);
        authInfo = xy.f580.f437;

        return (new C0622(c0512.f437));
    }

    private static C0512 rocheDataWrap(ByteBuffer byteBuffer) {
        C0512 c0512 = new C0512();
        try {
            c0512.f436 = byteBuffer.getShort() & 65535;
            byte[] bArr = new byte[(byteBuffer.getShort() & 65535)];
            byteBuffer.get(bArr);
            c0512.f437 = bArr;
            Log.e("H2BT","AUTHO_INFO_WRAP ... ??? == "+ c0512.f436 );
            Log.e("H2BT","AUTHO_INFO_WRAP ... LEN == "+ c0512.f437.length );
            return c0512;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    //public final void mo680() {
    public static void rocheCreateAuthCode(Context ctx) {
        ArrayList arrayList = null;
        //if (this.f605 == null) {
        //    m329(new C0486(this.f370, 5, "Invalid challenge buffer"));
        //    return;
        //}
        Log.e("H2BT","MO680 ... CALL ??");
        //PrivateKey xPrivateKey = C0641.m592();
        PrivateKey xPrivateKey = C0641.m593(ctx);
        Log.e("H2BT","PRIVATE KEY ... " + xPrivateKey);
        if (xPrivateKey == null) {
            //m329(new C0486(this.f370, 6, "Invalid Private Key"));
            //return;
        }
        byte[] sign;
        try {
            Log.e("H2BT","MO680 ... TRY ... INFO_LEN = ?? " +  authInfo.length);
            //byte[] bArr = this.f605;
            byte[] bArr = authInfo;
            Signature instance = Signature.getInstance("SHA256withRSA");
            instance.initSign(xPrivateKey);
            instance.update(bArr);

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

        //Object ˊ2 = C0562.m455(new C0587(sign));
        //byte [] xdata2 = m455(new C0587(sign));
        byte [] xdata2 = rocheAuthCode(new C0587(sign));

        if (!(xdata2 == null || xdata2.length == 0)) {
            //List arrayList2 = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
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
                Log.e("H2BT","OAU-ARRAY!! ..." + length + " " + obj[2] + "  " + obj[3] + "  " + obj[4]);

                Log.e("H2BT","OAU-ARRAY!! ..." + length + " " + obj[16] + "  " + obj[17] );
            }

            Log.e("H2BT","OAU MAYBE GOOD!! ..." + " " );
            arrayList = arrayList2;
        }
        //this.f606 = arrayList;
        writeBuffer = arrayList;
        checkWrBufferList();
        //m484();
    }


    /* Roche Authorization Code */
    private static byte[] rocheAuthCode(C0569 c0569) {
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

    private static void checkWrBufferList() {

        int theSize = writeBuffer.size();

        Log.e("H2BT","WR BUFFER SIZE !! ..." + theSize );

        //4.查詢特定元素
        //boolean isIn = myList.contains(s); //若用上面的例子 因為有s字串 所以回傳true

        //5.查詢特定元素位置
        //int idx = myList.indexOf(s); //會回傳0 表第0個位置

        //6.判斷List是否為空

        boolean empty = writeBuffer.isEmpty(); //因為有一個元素 會回傳false

        //Log.e("H2BT","OAU MAYBE GOOD!! ..." + " " );
        if (!empty) {
            for (int k=0; k<theSize-1; k++) {
                byte[] wr = new byte[20];
                wr = writeBuffer.get(k);
                //for (int m=0; m<20; m++) {
                //    Log.e("H2BT","WR-D !! ..."+ k + " = " + m + " -  " + wr[m]);
                //}
            }

            byte[] wr = new byte[20];
            wr = writeBuffer.get(theSize-1);
            for (int m=0; m<18; m++) {
                Log.e("H2BT","WR-D !! ..." + m + " -  " + wr[m]);
            }
        }

        //7.取得特定元素

        //myList.get(idx);


        //8.刪除特定元素

        //writeBuffer.remove(0);
    }

    public static byte[] getAuthCode(int idx) {
        Log.e("H2BT","GET ID !! ..." + idx);
        if (writeBuffer==null) {
            return null;
        }

        boolean empty = writeBuffer.isEmpty(); //因為有一個元素 會回傳false
        if (empty) {
            return null;
        }
        int theSize = writeBuffer.size();
        if (idx >= theSize) {
            return null;
        }
        byte[] wrTmp = writeBuffer.get(idx);

        if (idx >= theSize-1) {
            //writeBuffer.remove(0);
        }
        return wrTmp;
    }

    // Encode Test ...

    /*
    //public final void mo680() {
    public static void rocheEncodeTest(Context ctx) {
        ArrayList arrayList = null;

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
            for (int k=0; k<bArr.length; k++) {
                Log.e("H2BT","PASSWORD  ..." + k + " - " + bArr[k]);
            }

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

        Log.e("H2BT","MO680 ... TRY ... SIGN_LEN = ?? " +  sign.length);
        if (sign.length >= 256) {
            Log.e("H2BT","MO680 ... TRY ... SIGN_D0 = ?? " +  sign[254]);
            Log.e("H2BT","MO680 ... TRY ... SIGN_D1 = ?? " +  sign[255]);
        }



        byte [] xdata2 = rocheAuthCode(new C0587(sign));

        if (!(xdata2 == null || xdata2.length == 0)) {
            //List arrayList2 = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
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
                Log.e("H2BT","OAU-ARRAY!! ..." + length + " " + obj[2] + "  " + obj[3] + "  " + obj[4]);

                Log.e("H2BT","OAU-ARRAY!! ..." + length + " " + obj[16] + "  " + obj[17] );
            }

            Log.e("H2BT","OAU MAYBE GOOD!! ..." + " " );
            arrayList = arrayList2;
        }
        writeBuffer = arrayList;
        checkWrBufferList();
    }
    */

}