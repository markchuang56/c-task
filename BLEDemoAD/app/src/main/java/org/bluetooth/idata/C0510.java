package org.bluetooth.idata;

import android.util.Log;

//import com.roche.ac360mobile.sharedlibrary.ble.rpc.parser.IPacketParser;
//import com.roche.ac360mobile.sharedlibrary.ble.rpc.utility.OperationType;

import org.bluetooth.bledemo.C0512;
import org.bluetooth.bledemo.C0553;
import org.bluetooth.bledemo.C0569;
import org.bluetooth.bledemo.C0638;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//import p000o.C0564.C0562;

/* renamed from: o.Ι */
//public abstract class C0510 extends C0485 {
public abstract class C0510 {

    //public OperationType f431;

    //public IPacketParser f432;

    private C0638 f433;

    //private C0514 f434 = new C0514(this);

    //public C0510(C0582 c0582, OperationType operationType, IPacketParser iPacketParser) {
    //    super(c0582);
    //    this.f431 = operationType;
    //    this.f432 = iPacketParser;
    //}


    /*
    public final void mo680() {
        byte[] ˋ = C0562.m456(mo722());
        try {
            this.f433 = new C0638();
            this.f433.f712 = this.f434;
            this.f370.mo719().m120(ˋ);
        } catch (C0452 e) {
            if (e.f297 == 3) {
                m329(new C0518(this.f370));
            } else {
                m329(new C0518(this.f370));
            }
        }
    }
*/
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */

    //public final void m374(C0553 r12) {
    public final void m374(C0553 ca0553) {
        /*
        r11 = this;
        r3 = -1; = 0xFFFF
        r4 = 0; == false
        r10 = 2;
        r1 = 1;
        r2 = 0;
        r11.m377();
        r0 = r11.f370;
        r6 = r0.mo719();
        r7 = r11.f431;
        r0 = r6.f134;
        if (r0 == 0) goto L_0x0173;
        */



        /*
    L_0x0014:
        r0 = com.roche.ac360mobile.sharedlibrary.ble.services.RpcService.C00661.f130;
        r5 = r7.ordinal();
        r0 = r0[r5];
        switch(r0) {
            case 1: goto L_0x002a;
            case 2: goto L_0x0038;
            case 3: goto L_0x0046;
            case 4: goto L_0x0149;
            case 5: goto L_0x014c;
            case 6: goto L_0x014f;
            default: goto L_0x001f;
        };
    L_0x001f:
        r0 = r4;
    L_0x0020:
        r2 = r6.f134;
        if (r2 == 0) goto L_0x0029;
    L_0x0024:
        r2 = r6.f134;
        r2.mo426(r7, r1, r0);
    L_0x0029:
        return;
    L_0x002a:
        if (r12 == 0) goto L_0x0173;
    L_0x002c:
        r0 = new o.iF;
        r12 = (p000o.C0606) r12;
        r2 = r12.m519();
        r0.<init>(r2);
        goto L_0x0020;
    L_0x0038:
        if (r12 == 0) goto L_0x0173;
    L_0x003a:
        r0 = new o.COn;
        r12 = (p000o.C0606) r12;
        r2 = r12.m519();
        r0.<init>(r2);
        goto L_0x0020;
        */
/*

    //L_0x0046:
    if (ca0553 == null) {
        return;
    }
     //   if (r12 == 0) goto L_0x0173;
        // L_0x0048:
        r12 = (p000o.C0634) r12;
    C0634 c0634 = (C0634)ca0553;

        r0 = r12.f701;
        C0512 c0512_c = c0634.f701;

        if (r0 == 0) goto L_0x017e;
        if (c0512_c == null) {
            return;;
        }

    L_0x004e:
        r0 = r12.f701;
        r3 = r0.f437;
        if (r3 == 0) goto L_0x0184;
        C0512 c0512_cx = c0634.f701;
        byte [] time_c = c0512_cx.f437;
        if (time_c == null) {
            return;
        }

    L_0x0054:
        r0 = r0.f437;
        r0 = r0.length;
        byte [] time_cxd = c0512_cx.f437;
        int time_axlen = time_cxd.length;

    L_0x0057:
        if (r0 != r10) goto L_0x017e;
    if (time_axlen != 2) {
        return;
    }

    L_0x0059:
        r0 = r12.f701;
        r0 = r0.f437;
        if (r0 == 0) goto L_0x0181;
        C0512 c0512_bx = c0634.f701;
        byte [] time_bxd = c0512_bx.f437;
        if (time_bxd == null) {
            return;
        }

    L_0x005f:
        r0 = java.nio.ByteBuffer.wrap(r0);
        r3 = java.nio.ByteOrder.BIG_ENDIAN;
        r0.order(r3);
    L_0x0068:
        if (r0 == 0) goto L_0x017e;

        ByteBuffer wrap = null;
        if (time_bxd != null) {
            try {
                Log.e("H2BT", "XXXX ");
                wrap = ByteBuffer.wrap(time_bxd);
                wrap.order(ByteOrder.BIG_ENDIAN);
            } catch (Exception e) {
                return;
            }
        }

    L_0x006a:
        r0 = r0.getShort();
        if (r0 != r1) goto L_0x0146;
    L_0x0070:
        r0 = r1;

    short xd = wrap.getShort();
    if (xd != 1) {
        xd = 0;
    }


    L_0x0071:
        r3 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r5 = r12.f700;
        if (r5 == 0) goto L_0x0099;

    L_0x0078:
        r5 = r12.f700;
        r8 = r5.f437;
        if (r8 == 0) goto L_0x017b;

    L_0x007e:
        r5 = r5.f437;
        r5 = r5.length;
    L_0x0081:
        r8 = 4;
        if (r5 != r8) goto L_0x0099;
    L_0x0084:
        r5 = r12.f700;
        r5 = r5.f437;
        if (r5 == 0) goto L_0x0093;
    L_0x008a:
        r4 = java.nio.ByteBuffer.wrap(r5);
        r5 = java.nio.ByteOrder.BIG_ENDIAN;
        r4.order(r5);
    L_0x0093:
        if (r4 == 0) goto L_0x0099;
    L_0x0095:
        r3 = r4.getInt();

        C0512 c0512_xx = c0634.f700;
        if (c0512_xx == null) {
            goto 99;
        }
        if (c0512_xx != null) {
            byte [] time_bxe = c0512_xx.f437;
            if (time_bxd != null) {
                if (time_bxe.length != 4) {
                    goto 99
                }
                if (time_bxe.length == 4) {

                    ByteBuffer wrap_b = null;
                    if (time_bxd != null) {
                        try {
                            Log.e("H2BT", "XXXX ");
                            wrap_b = ByteBuffer.wrap(time_bxe);
                            wrap_b.order(ByteOrder.BIG_ENDIAN);
                        } catch (Exception e) {
                            return;
                        }
                    }

                }

            }else {

            }
        }


    L_0x0099:
        r4 = "GMT";
        r4 = java.util.TimeZone.getTimeZone(r4);
        r5 = java.util.GregorianCalendar.getInstance(r4);
        r4 = r12.f699;
        if (r4 == 0) goto L_0x013a;
    L_0x00a7:
        r4 = r12.f699;
        r8 = r4.f437;
        if (r8 == 0) goto L_0x0178;
    L_0x00ad:
        r4 = r4.f437;
        r4 = r4.length;
    L_0x00b0:
        r8 = 8;
        if (r4 != r8) goto L_0x013a;
    L_0x00b4:
        r4 = r12.f699;
        r4 = r4.f437;
        r2 = r4[r2];
        r8 = r4[r1];
        r9 = r2 & 240;
        r9 = r9 >> 4;
        r9 = r9 * 10;
        r2 = r2 & 15;
        r2 = r2 + r9;
        r2 = r2 * 100;
        r9 = r8 & 240;
        r9 = r9 >> 4;
        r9 = r9 * 10;
        r8 = r8 & 15;
        r8 = r8 + r9;
        r2 = r2 + r8;
        r5.set(r1, r2);
        r2 = r4[r10];
        r8 = r2 & 240;
        r8 = r8 >> 4;
        r8 = r8 * 10;
        r2 = r2 & 15;
        r2 = r2 + r8;
        r2 = r2 + -1;
        r5.set(r10, r2);
        r2 = 3;
        r2 = r4[r2];
        r8 = 5;
        r9 = r2 & 240;
        r9 = r9 >> 4;
        r9 = r9 * 10;
        r2 = r2 & 15;
        r2 = r2 + r9;
        r5.set(r8, r2);
        r2 = 4;
        r2 = r4[r2];
        r8 = 11;
        r9 = r2 & 240;
        r9 = r9 >> 4;
        r9 = r9 * 10;
        r2 = r2 & 15;
        r2 = r2 + r9;
        r5.set(r8, r2);
        r2 = 5;
        r2 = r4[r2];
        r8 = 12;
        r9 = r2 & 240;
        r9 = r9 >> 4;
        r9 = r9 * 10;
        r2 = r2 & 15;
        r2 = r2 + r9;
        r5.set(r8, r2);
        r2 = 6;
        r2 = r4[r2];
        r8 = 13;
        r9 = r2 & 240;
        r9 = r9 >> 4;
        r9 = r9 * 10;
        r2 = r2 & 15;
        r2 = r2 + r9;
        r5.set(r8, r2);
        r2 = 7;
        r2 = r4[r2];
        r4 = 14;
        r8 = r2 & 240;
        r8 = r8 >> 4;
        r8 = r8 * 10;
        r2 = r2 & 15;
        r2 = r2 + r8;
        r2 = r2 / 10;
        r5.set(r4, r2);
    L_0x013a:
        r2 = new o.IF;
        r4 = r5.getTime();
        r2.<init>(r4, r3, r0);
        r0 = r2;
        goto L_0x0020;
    L_0x0146:
        r0 = r2;
        goto L_0x0071;
    L_0x0149:
        r0 = r4;
        goto L_0x0020;
    L_0x014c:
        r0 = r4;
        goto L_0x0020;
    L_0x014f:
        if (r12 == 0) goto L_0x0173;
    L_0x0151:
        r12 = (p000o.C0613) r12;
        if (r12 == 0) goto L_0x0173;
    L_0x0155:
        r0 = r12.f580;
        r0 = r0.f437;
        if (r0 == 0) goto L_0x0176;
    L_0x015b:
        r5 = r0.length;
        if (r10 != r5) goto L_0x0176;
    L_0x015e:
        r2 = r0[r2];
        r2 = r2 & 255;
        r2 = r2 << 8;
        r0 = r0[r1];
        r0 = r0 & 255;
        r0 = r0 | r2;
        r2 = r0;
    L_0x016a:
        if (r2 == r3) goto L_0x0173;
    L_0x016c:
        r0 = new com.roche.ac360mobile.sharedlibrary.api.GlucoseUnitSettings;
        r0.<init>(r2);
        goto L_0x0020;
    L_0x0173:
        r0 = r4;
        goto L_0x0020;
    L_0x0176:
        r2 = r3;
        goto L_0x016a;
    L_0x0178:
        r4 = r2;
        goto L_0x00b0;
    L_0x017b:
        r5 = r2;
        goto L_0x0081;
    L_0x017e:
        r0 = r2;
        goto L_0x0071;
    L_0x0181:
        r0 = r4;
        goto L_0x0068;
    L_0x0184:
        r0 = r2;
        goto L_0x0057;
        */
        //throw new UnsupportedOperationException("Method not decompiled: o.Ι.ˊ(o.৲):void");
    }

    /* renamed from: ˊ */
    public final void mo681(byte[] bArr) {
        //CoN.m224(bArr);
        this.f433.m573(bArr);
    }

    /* renamed from: ˋ */
    public abstract void mo721(byte[] bArr);

    /* renamed from: ˎ */
    public final void m377() {
        //C0485 c0666 = new C0666(this.f370);
        //if (this.f370.mo720() instanceof C0510) {
        //    m329(c0666);
        //}
    }

    /* renamed from: ˏ */
    public abstract C0569 mo722();
}
