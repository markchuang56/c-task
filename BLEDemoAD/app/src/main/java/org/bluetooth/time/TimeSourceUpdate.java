package org.bluetooth.time;

import java.io.Serializable;

public class TimeSourceUpdate extends GmtTimeReference implements Serializable {
    public static final String KEY_REFERENCE_DEVICE_TIME = "reference_device_time";
    private static final long serialVersionUID = 252103695192229910L;
    private String key;

    public TimeSourceUpdate(TimeSourceUpdate timeSourceUpdate) {
        this(timeSourceUpdate.getGmtTime(), timeSourceUpdate.getET(), timeSourceUpdate.getKey());
    }

    public TimeSourceUpdate(Long l, Long l2, String str) {
        super(l, l2);
        this.key = str;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String toString() {
        return "ReferenceDeviceTime [key=" + this.key + ", toString()=" + super.toString() + "]";
    }
}
