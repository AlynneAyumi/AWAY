package com.example.away.service;

import java.sql.Date;
import java.util.Calendar;

public class UtilService {
    public static Date getDataAtual() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return new java.sql.Date(cal.getTimeInMillis());
}
}
