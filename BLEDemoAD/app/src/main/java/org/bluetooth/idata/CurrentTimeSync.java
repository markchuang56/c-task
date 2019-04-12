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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.GregorianCalendar;

public class CurrentTimeSync {
    //public int f436 = 0;
    //public byte[] f437 = null;

    static private ByteArrayOutputStream rocheVendorData;
    static private int packetTag = 1;

    private static byte[] authInfo = null;

    //private List<byte[]> writeBuffer;
    private static ArrayList<byte[]> writeBuffer;

    /* creates  object */
    public CurrentTimeSync() {
        //writeBuffer = new ArrayList<>();
    }
/*
    public static boolean rocheCurrentTimeInput(byte[] bArr) {
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
            byte bPkCurrent = byteBuffer.get();
            byte bPkTotal = byteBuffer.get();
            if (packetTag == bPkCurrent) {
                packetTag++;
                if (bPkCurrent == (byte) 1) {
                    if (rocheVendorData != null) {
                        try {
                            rocheVendorData.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    rocheVendorData = new ByteArrayOutputStream();
                }
                if (rocheVendorData != null) {
                    rocheVendorData.write(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
                }
                if (bPkCurrent == bPkTotal && rocheVendorData != null) {
                    try {
                        rocheVendorData.flush();
                        byte[] toByteArray = rocheVendorData.toByteArray();
                        Log.e("H2BT","Roche V-Data ... " + toByteArray.length);

                        packetEnd(toByteArray, bPkCurrent);
                        return true;
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
        switch (bPk) {
            case 1:
                break;

            case 2:
                c0662mo727(data);
                break;

            case 3:
                //rocheAuthorizeData(data);
                break;
                default:
                    break;
        }
    }
*/
    //public static C0553 rocheAuthorizeData(byte[] bArr) {
    public static C0553 c0662mo727(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteBuffer wrap = null;
        if (bArr != null) {
            try {
                Log.e("H2BT", "Current Time Come In ... ");
                wrap = ByteBuffer.wrap(bArr);
                wrap.order(ByteOrder.BIG_ENDIAN);
            } catch (Exception e) {
                return null;
            }
        }

        //0xF0, 0x03 , 0x00, 0x03, 0x00, 0x1A
        if ((wrap.getShort() & 65535) != 61443) { // FF03
            Log.e("H2BT", "C0662MO727 ... ??? == FF03");
            return null;
        }
        if ((wrap.getShort() & 65535) != 3) {
            Log.e("H2BT", "C0662MO727 ... ??? == 3");
            return null;
        }

        if ((wrap.getShort() & 65535) != 26) {
            Log.e("H2BT", "C0662MO727 ... ??? == 26");
            return null;
        }

        // 0x09, 0x90, 0x00, 0x08 ,
        // 0x20, 0x19, 0x02, 0x20 , 0x18, 0x22, 0x05, 0x00
        C0512 timebArr = rocheReadCTWrap(wrap);
        if (timebArr == null) {
            return null;
        }
        if (timebArr.f436 != 0x0990) {
            Log.e("H2BT", "C0662MO727 ... ??? == 0x0990");
            return null;
        }
        if (timebArr.f437 == null) {
            Log.e("H2BT", "C0662MO727 ... ??? == f437 == NULL");
            return null;
        }
        if (timebArr.f437.length != 8) {
            Log.e("H2BT", "C0662MO727 ... ??? == c0512.f437.length != 8");
            return null;
        }

        // 0x00, 0x1C , 0x00, 0x04,
        // 0xFF, 0xFF , 0xFF, 0xFC,
        C0512 timeMiddlebArr = rocheReadCTWrap(wrap);
        if (timeMiddlebArr == null) {
            return null;
        }

        if (timeMiddlebArr.f436 != 0x001C) {
            Log.e("H2BT", "C0662MO727 ... ??? == timeMiddlebArr.f436 != 0x001C");
            return null;
        }

        if (timeMiddlebArr.f437 == null) {
            Log.e("H2BT", "C0662MO727 ... ??? == timeMiddlebArr.f437 == null");
            return null;
        }
        if (timeMiddlebArr.f437.length != 4) {
            Log.e("H2BT", "C0662MO727 ... ??? == timeMiddlebArr.f437.length != 4");
            return null;
        }

        // 0x00, 0x10 , 0x00, 0x02,
        // 0x00, 0x01
        C0512 timeEndbArr = rocheReadCTWrap(wrap);
        if (timeEndbArr == null) {
            return null;
        }

        if (timeEndbArr.f436 != 0x0010) {
            Log.e("H2BT", "C0662MO727 ... ??? == timeEndbArr.f436 != 0x001C");
            return null;
        }

        if (timeEndbArr.f437 == null) {
            Log.e("H2BT", "C0662MO727 ... ??? == timeEndbArr.f437 == null");
            return null;
        }
        if (timeEndbArr.f437.length != 2) {
            Log.e("H2BT", "C0662MO727 ... ??? == timeEndbArr.f437.length != 4");
            return null;
        }

        // 0x91, 0xA6

        Log.e("H2BT", "CT_INFO ... " + timebArr.f436);
        Log.e("H2BT", "CT_INFO ... " + timeMiddlebArr.f436);
        Log.e("H2BT", "CT_INFO ... " + timeEndbArr.f436);

        Log.e("H2BT", "CT_INFO ... " + wrap.remaining());

        int crcCalc = C0636.rocheCrcCalculate(bArr, bArr.length - 2);
        int crcRaw = wrap.getShort();
        Log.e("H2BT", "CT_INFO ... CRC " + crcCalc);

        Log.e("H2BT", "CT_INFO ... D_LEN = " + (bArr.length - 2));
        Log.e("H2BT", "CT_INFO ... CRC = " + crcRaw);
        if (crcCalc != (crcRaw & 0xFFFF)) {
            Log.e("H2BT", "CT_ CRC ERROR ");
        }

        //C0634 c0634 = new C0634();
        //C0634(int i, byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3)
        //c0634.C0634(timebArr.f436, timebArr.f437, timeMiddlebArr.f436, timeMiddlebArr.f437, timeEndbArr.f436, timeEndbArr.f437);

        C0634 c0634 = new C0634(timebArr.f436, timebArr.f437, timeMiddlebArr.f436, timeMiddlebArr.f437, timeEndbArr.f436, timeEndbArr.f437);

        buildCurrentTime(c0634);
        return c0634;


/*
        C0553 xfin = new C0622(c0512.f437);

        Log.e("H2BT", "C0662MO727 ... f528 = ?? " + xfin.f528);
        Log.e("H2BT", "C0662MO727 ... f527 = ?? " + xfin.f527);
        //Log.e("H2BT","C0664_MO727 ... f528 = ?? " + xfin.f527.get(0));

        C0512 endx = xfin.f527.get(0);
        Log.e("H2BT", "C0664_MO727 ... f528 = ?? " + endx.f436);
*/

        //C0622 xy = new C0622(timebArr.f437);

        //for (int j = 0; j < c0512.f437.length; j++) {
        //    Log.e("H2BT", "C0664_MO727 ... JJ = ?? " + j + "" + xy.f580.f437[j] + " GOOD = " + c0512.f437[j]);
        //}

        // To Do ...
        //mo680(c0512.f437, context);
        //authInfo = xy.f580.f437;

        //return (new C0622(timebArr.f437));
    }

    private static void buildCurrentTime(C0553 bArr) {
        Log.e("H2BT", "CT_ BUILD ... = ?? ");
        if (bArr == null) return;

        C0634 c0634 = (C0634)bArr;

        // First
        C0512 ct = c0634.f701;
        Log.e("H2BT", "CT_ f701.f437 CT LEN = ?? " + ct.f437.length);

        if (ct == null) return;

        if (ct.f437 == null) return;

        //if (ct.f437.length != 2) return;

        //r3 = r0.f437;
        //if (r3 == 0) goto L_0x0184;
        //C0512 c0512_cx = c0634.f701;
        //byte [] time_c = c0512_cx.f437;
        //if (time_c == null) {
            //return;
        //}

        //Log.e("H2BT", "CT_ f701.f437 LEN = ?? " + c0512_cx.f437.length);

        ByteBuffer byteBuffer;
        if (ct.f437 != null) {
            ByteBuffer wrap = ByteBuffer.wrap(ct.f437);
            wrap.order(ByteOrder.BIG_ENDIAN);
            byteBuffer = wrap;
        } else {
            byteBuffer = null;
        }

        Log.e("H2BT", "CT_ Get LEN = ?? " + byteBuffer.getShort());

        // Second
        C0512 ct_second = c0634.f700;
        Log.e("H2BT", "CT_ f700.f437 CT LEN = ?? " + ct_second.f437.length);

        if (ct_second == null) return;

        if (ct_second.f437 == null) return;

        //if (ct_second.f437.length != 4) return;

        ByteBuffer byteBufferSecond;
        if (ct_second.f437 != null) {
            ByteBuffer wrap = ByteBuffer.wrap(ct_second.f437);
            wrap.order(ByteOrder.BIG_ENDIAN);
            byteBufferSecond = wrap;
        } else {
            byteBufferSecond = null;
        }

        //Log.e("H2BT", "CT_ Get LEN SECOND = ?? " + byteBufferSecond.getShort());
        Log.e("H2BT", "CT_ Get LEN SECOND = ?? " + byteBufferSecond.getInt());


        // time ..
        //Date currentTime = Calendar.getInstance().getTime();
/*
        //Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        Date currentLocalTime = calendar.getTime();

        DateFormat date = new SimpleDateFormat("ZZZZZ", Locale.getDefault());
        String localTime = date.format(currentLocalTime);
        System.out.println(localTime+ "  TimeZone   " );
        */

        TimeZone xTimeZone = TimeZone.getTimeZone("GMT");
        //GregorianCalendar calendar = GregorianCalendar.getInstance(xTimeZone);
        Calendar calendar = GregorianCalendar.getInstance(xTimeZone);


        //Calendar calendar = Calendar.getInstance();
        //SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        //String strDate = "Current Date : " + mdformat.format(calendar.getTime());
        //display(strDate);

        //Log.e("H2BT", "CT_ D&T = ?? " + strDate);





        // Three
        C0512 ct_st = c0634.f699;
        Log.e("H2BT", "CT_ f699.f437 CT LEN = ?? " + ct_st.f437.length);

        if (ct_st == null) return;

        if (ct_st.f437 == null) return;


        for (int m=0; m<ct_st.f437.length; m++) {
            Log.e("H2BT", "CT_ VAL = ?? " + m + " - " + ct_st.f437[m]);
        }

        //if (ct_st.f437.length != 8) return;


        byte year_hi = ct_st.f437[0];
        byte year_lo = ct_st.f437[1];
        //int aYear = (((year_hi[0] & 0xF0) >> 4 ) * 10 + (year_hi[0] & 0x0F)) * 100;

        int aHiYear = ((year_hi & 0xF0)>>4) * 10 + (year_hi & 0x0F);
        int aLoYear = ((year_lo & 0xF0)>>4) * 10 + (year_lo & 0x0F);
        aHiYear = aHiYear * 100 + aLoYear;
        Log.e("H2BT", "CT_ YEAR = ?? " + aHiYear);

        byte month = ct_st.f437[2];
        int aMonth = ((month & 0xF0)>>4) * 10 + (month & 0x0F);
        Log.e("H2BT", "CT_ MONTH = ?? " + aMonth);

        byte day = ct_st.f437[3];
        int aDay = ((day & 0xF0)>>4) * 10 + (day & 0x0F);
        Log.e("H2BT", "CT_ DAY = ?? " + aDay);

        byte hour = ct_st.f437[4];
        int aHour = ((hour & 0xF0)>>4) * 10 + (hour & 0x0F);
        Log.e("H2BT", "CT_ HOUR = ?? " + aHour);

        byte minute = ct_st.f437[5];
        int aMinute = ((minute & 0xF0)>>4) * 10 + (minute & 0x0F);
        Log.e("H2BT", "CT_ MINUTE = ?? " + aMinute);

        calendar.set(Calendar.YEAR, aMinute);
        calendar.set(Calendar.MONTH, aMinute);
        calendar.set(Calendar.DATE, aMinute);

        calendar.set(Calendar.HOUR, aMinute);
        calendar.set(Calendar.MINUTE, aMinute);

        byte second = ct_st.f437[6];
        int aSecond = ((second & 0xF0)>>4) * 10 + (second & 0x0F);
        Log.e("H2BT", "CT_ SECOND = ?? " + aSecond);

        byte miniSecond = ct_st.f437[7];
        int aMiniSecond = ((miniSecond & 0xF0)>>4) * 10 + (miniSecond & 0x0F);
        Log.e("H2BT", "CT_ T_END = ?? " + aMiniSecond);

        calendar.set(Calendar.SECOND, aSecond);
        calendar.set(Calendar.MILLISECOND, aMiniSecond);

        Date pct =  calendar.getTime();

        XIF xif = new XIF(pct, 0x7FFFFFFF, true);
        Log.e("H2BT", "CT_ DATE = ?? " + xif.xf280);

    }

    // C0564.If.m453
    private static C0512 rocheReadCTWrap(ByteBuffer byteBuffer) {
        C0512 c0512 = new C0512();
        try {
            c0512.f436 = byteBuffer.getShort() & 65535;
            byte[] bArr = new byte[(byteBuffer.getShort() & 65535)];
            byteBuffer.get(bArr);
            c0512.f437 = bArr;
            Log.e("H2BT","CT_INFO_WRAP ... ??? == "+ c0512.f436 );
            Log.e("H2BT","CT_INFO_WRAP ... LEN == "+ c0512.f437.length );
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
            Log.e("H2BT","MO680 ... TRY ..." );
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

}