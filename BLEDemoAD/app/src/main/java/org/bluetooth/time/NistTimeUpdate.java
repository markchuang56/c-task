package org.bluetooth.time;

import java.io.Serializable;

public class NistTimeUpdate extends TimeSourceUpdate implements Serializable {
    public static final String KEY = "nist";
    private static final long serialVersionUID = -5509181264561205252L;

    public NistTimeUpdate(long j, long j2) {
        super(Long.valueOf(j), Long.valueOf(j2), KEY);
    }

    public String toString() {
        return "NistTime [toString()=" + super.toString() + "]";
    }
}
