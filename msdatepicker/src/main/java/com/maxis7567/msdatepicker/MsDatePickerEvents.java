package com.maxis7567.msdatepicker;

import java.util.Date;

public interface MsDatePickerEvents {
    void onConfirm(int[] dateInJalali, Date date);
    void onCancel();
}
