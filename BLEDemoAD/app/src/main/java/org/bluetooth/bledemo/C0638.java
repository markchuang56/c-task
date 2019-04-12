package org.bluetooth.bledemo;

//import com.roche.ac360mobile.sharedlibrary.ble.rpc.packet.RpcPacketListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: o.ᵧ */
public final class C0638 {
    /* renamed from: ˊ */
    //public RpcPacketListener f712 = this.f452;
    /* renamed from: ˋ */
    private ByteArrayOutputStream f713;
    /* renamed from: ˎ */
    private int f714 = 1;

    /* renamed from: ˊ */
    public final void m573(byte[] bArr) {
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
            if (this.f714 == b) {
                this.f714++;
                if (b == (byte) 1) {
                    if (this.f713 != null) {
                        try {
                            this.f713.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    this.f713 = new ByteArrayOutputStream();
                }
                if (this.f713 != null) {
                    this.f713.write(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
                }
                if (b == b2 && this.f713 != null) {
                    try {
                        this.f713.flush();
                        byte[] toByteArray = this.f713.toByteArray();
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
         //   this.f712.mo691(null, false);
        }
    }
}
