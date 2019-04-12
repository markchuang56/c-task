package org.bluetooth.time;

import java.io.Serializable;

public class GmtTimeReference implements Serializable {
    private static final long serialVersionUID = -4383785893052271470L;
    private Long mET;
    private Long mGmtTime;

    public GmtTimeReference(Long l, Long l2) {
        this.mGmtTime = l;
        this.mET = l2;
    }

    public Long getET() {
        return this.mET;
    }

    public Long getGmtTime() {
        return this.mGmtTime;
    }

    public void setET(Long l) {
        this.mET = l;
    }

    public void setGmtTime(Long l) {
        this.mGmtTime = l;
    }

    public String toString() {
        return "GmtTimeReference [mGmtTime=" + this.mGmtTime + ", mET=" + this.mET + "]";
    }
}
