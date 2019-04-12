package org.bluetooth.bledemo;

import android.support.v4.view.MotionEventCompat;

/* renamed from: o.ᕽ */
public final class C0610 extends C0569 {
    public C0610(int i, byte[] bArr) {
        this.f580.f436 = i;
        this.f580.f437 = bArr;
    }

    /* renamed from: ˊ */
    public final int m525() {
        byte[] bArr = this.f580.f437;
        return (bArr == null || bArr.length != 2) ? -1 : ((bArr[0] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 8) | (bArr[1] & MotionEventCompat.ACTION_MASK);
    }
}
