package org.bluetooth.bledemo;

/* CRC */
public final class C0636 {

    private static final int[] f710 = new int[]{33800, 16900, 8450, 4225, 35912, 17956, 8978, 4489};

    //public static int m572(byte[] bArr, int i);
    // CRC Method
    public static int rocheCrcCalculate(byte[] bArr, int i) {
        if (bArr == null || i == 0 || i > bArr.length) {
            return 0;
        }
        int i2 = 0;
        int i3 = 65535;
        while (i2 < i) {
            byte b = bArr[i2];
            int i4 = i3 >> 8;
            int i5 = 128;
            for (int i6 = 0; i6 < 8; i6 = (short) (i6 + 1)) {
                if ((((b ^ i3) & 65535) & i5) != 0) {
                    i4 ^= f710[i6];
                }
                i5 = (short) (i5 >> 1);
            }
            i2++;
            i3 = i4;
        }
        return i3;
    }
}
