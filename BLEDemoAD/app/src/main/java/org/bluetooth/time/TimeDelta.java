package org.bluetooth.time;

import android.content.ContentValues;
import android.database.Cursor;
//import com.roche.acconnect.datadevice.provider.Table_MeterDeltas.MeterDeltasColumns;
//import com.roche.acconnect.datadevice.utils.CursorUtilities;

public class TimeDelta {
    private Long mDelta;
    private int mId;


    private String mKey;

    public TimeDelta(Cursor cursor) {
        /*
        this.mId = CursorUtilities.getInt(cursor, "_id");
        this.mDelta = Long.valueOf(CursorUtilities.getLong(cursor, "delta"));
        this.mKey = CursorUtilities.getString(cursor, MeterDeltasColumns.KEY);
        */
    }

    public TimeDelta(TimeDelta timeDelta) {
        this(timeDelta.getDelta(), timeDelta.getKey());
        setId(timeDelta.getId());
    }

    public TimeDelta(Long l, String str) {
        this.mDelta = l;
        this.mKey = str;
        this.mId = -1;
    }

    public TimeDelta(String str) {
        this.mKey = str;
        this.mDelta = null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TimeDelta)) {
            return false;
        }
        TimeDelta timeDelta = (TimeDelta) obj;
        return this.mKey.equals(timeDelta.mKey) && this.mDelta.equals(timeDelta.mDelta);
    }
/*
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        if (this.mId > 0) {
            contentValues.put("_id", Integer.valueOf(this.mId));
        } else {
            contentValues.putNull("_id");
        }
        contentValues.put("delta", this.mDelta);
        contentValues.put(MeterDeltasColumns.KEY, this.mKey);
        return contentValues;
    }
*/
    public Long getDelta() {
        return this.mDelta;
    }

    public int getId() {
        return this.mId;
    }

    public String getKey() {
        return this.mKey;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.mDelta == null ? 0 : this.mDelta.hashCode();
        int i2 = this.mId;
        if (this.mKey != null) {
            i = this.mKey.hashCode();
        }
        return ((((hashCode + 31) * 31) + i2) * 31) + i;
    }

    public void setDelta(Long l) {
        this.mDelta = l;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public String toString() {
        return "MeterDelta [mId=" + this.mId + ", mDelta=" + this.mDelta + ", mKey=" + this.mKey + "]";
    }
}
