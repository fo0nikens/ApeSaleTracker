package com.wrapper.stk;

import android.os.SystemProperties;

public class WrapperManager {
    public static final int PLATFORM_TYPE_MEDIATEK = 2;
    public static final int PLATFORM_TYPE_QUALCOMM = 1;
    public static final int PLATFORM_TYPE_UNKNOWN = 0;
    private static AbsWrapper absWrapper;

    private WrapperManager() {
    }

    public static AbsWrapper getInstance() {
        if (absWrapper == null) {
            switch (getPlatformType()) {
                case 1:
                    absWrapper = new QcomWrapperEncapsulation();
                    break;
                case 2:
                    absWrapper = new MtkWrapperEncapsulation();
                    break;
            }
        }
        return absWrapper;
    }

    public static int getPlatformType() {
        String info = SystemProperties.get("gsm.version.ril-impl");
        if (info != null) {
            if (info.toLowerCase().contains("qualcomm")) {
                return 1;
            }
            if (info.toLowerCase().contains("mtk")) {
                return 2;
            }
        }
        return 0;
    }

    public static boolean isPlatformMediatek() {
        return getPlatformType() == 2;
    }

    public static boolean isPlatformQualcomm() {
        return getPlatformType() == 1;
    }
}
