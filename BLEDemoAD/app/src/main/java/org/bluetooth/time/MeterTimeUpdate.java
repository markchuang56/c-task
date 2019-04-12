package org.bluetooth.time;

import java.io.Serializable;

public class MeterTimeUpdate extends TimeSourceUpdate implements Serializable {
    private static final long serialVersionUID = -309831016644923067L;

    public MeterTimeUpdate(long j, long j2, String str) {
        super(Long.valueOf(j), Long.valueOf(j2), str);
    }

    public String toString() {
        return "MeterTime [toString()=" + super.toString() + "]";
    }
}
