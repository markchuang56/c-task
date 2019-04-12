package org.bluetooth.time;

import android.os.SystemClock;
import java.io.Serializable;

public class NowTime extends GmtTimeReference implements Serializable {
    public static final String EXTRA_KEY = "now_time";
    private static final long serialVersionUID = 4126936539011230335L;
    protected long mPhoneDelta;
    protected boolean mValidTimeSource;

    public NowTime(boolean z, long j, long j2, long j3) {
        super(Long.valueOf(j), Long.valueOf(j2));
        this.mValidTimeSource = z;
        this.mPhoneDelta = j3;
    }

    public long calcNow() {
        return getGmtTime().longValue() + (SystemClock.elapsedRealtime() - getET().longValue());
    }

    public long getPhoneDelta() {
        return this.mPhoneDelta;
    }

    public boolean isValidTimeSource() {
        return this.mValidTimeSource;
    }

    public String toString() {
        return "NowTime [mValidTimeSource=" + this.mValidTimeSource + ", mPhoneDelta=" + this.mPhoneDelta + ", getGmtTime()=" + getGmtTime() + ", getET()=" + getET() + "]";
    }
}
