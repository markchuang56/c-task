package org.bluetooth.idata;

import org.bluetooth.bledemo.C0512;
import org.bluetooth.bledemo.C0553;

import java.util.ArrayList;
import java.util.Date;
//import java.util.List;

/* renamed from: o.ᵞ */
public final class C0634 extends C0553 {
    /* renamed from: ˎ */
    public C0512 f699;
    /* renamed from: ˏ */
    public C0512 f700;
    /* renamed from: ᐝ */
    public C0512 f701;


    protected C0634() {
        this.f528 = 61443;
        ArrayList arrayList = new ArrayList();
        this.f699 = new C0512();
        this.f700 = new C0512();
        this.f701 = new C0512();
        arrayList.add(this.f699);
        arrayList.add(this.f700);
        arrayList.add(this.f701);
        this.f527 = arrayList;
    }

    public C0634(int i, byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3) {
        this();
        this.f699.f436 = i;
        this.f699.f437 = bArr;
        this.f700.f436 = i2;
        this.f700.f437 = bArr2;
        this.f701.f436 = i3;
        this.f701.f437 = bArr3;
    }
}

/*
//public final class IF implements IData {
public abstract class IF {

    public Date f280;

    public boolean f281;

    private int f282;

    public IF(Date date, int i, boolean z) {
        this.f280 = date;
        this.f282 = i;
        this.f281 = z;
    }
}
*/
