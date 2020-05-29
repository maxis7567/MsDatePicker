package com.maxis7567.msdatepicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JalaliCalendar {



    /** Gregorian & Jalali (Hijri_Shamsi,Solar) Date Converter Functions
     Author: JDF.SCR.IR =>> Download Full Version : http://jdf.scr.ir/jdf
     License: GNU/LGPL _ Open Source & Free _ Version: 2.72 : [2017=1396]
     --------------------------------------------------------------------
     1461 = 365*4 + 4/4   &  146097 = 365*400 + 400/4 - 400/100 + 400/400
     12053 = 365*33 + 32/4    &    36524 = 365*100 + 100/4 - 100/100   */

    public static int TYPE_BOTH=1;
    public static int TYPE_DATE=2;
    public static int TYPE_TIME=3;
    public static String gregorian_to_jalali(String unixDate,int type){
        int gy,gm,gd;

        Date date = new Date(Long.valueOf(unixDate)*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3:30"));
        String formattedDate = sdf.format(date);
        gy=Integer.valueOf(formattedDate.substring(0,4));
        gm=Integer.valueOf(formattedDate.substring(4,6));
        gd=Integer.valueOf(formattedDate.substring(6,8));

        int[] g_d_m = {0,31,59,90,120,151,181,212,243,273,304,334};
        int jy;
        if(gy>1600){
            jy=979;
            gy-=1600;
        }else{
            jy=0;
            gy-=621;
        }
        int gy2 = (gm > 2)?(gy + 1):gy;
        int days = (365 * gy) + ((int)((gy2 + 3) / 4)) - ((int)((gy2 + 99) / 100)) + ((int)((gy2 + 399) / 400)) - 80 + gd + g_d_m[gm - 1];
        jy += 33 * ((int)(days / 12053));
        days %= 12053;
        jy += 4 * ((int)(days / 1461));
        days %= 1461;
        if(days > 365){
            jy+=(int)((days-1)/365);
            days=(days-1)%365;
        }
        int jm = (days < 186)?1 + (int)(days / 31):7 + (int)((days - 186) / 30);
        int jd = 1 + ((days < 186)?(days % 31):((days - 186) % 30));
        String y,m,d;
        y=String.valueOf(jy);
        m=String.valueOf(jm);
        d=String.valueOf(jd);
        if(m.length()<2){
            m="0"+m;
        }
        if (d.length()<2){
            d="0"+d;
        }
        switch (type){
            case 1:

                String tmp=formattedDate.substring(9);
                formattedDate=y+"/"+m+"/"+d+" ";
                formattedDate=formattedDate.concat(tmp);
                return formattedDate;
            case 2:
                formattedDate=y+"-"+m+"-"+d;
                return formattedDate;
            case 3:
                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                sdf1.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3:30"));
                return sdf1.format(date);


        }
return null;
    }


    public static int[] jalali_to_gregorian(int jy, int jm, int jd){
        int gy;
        if(jy>979){
            gy=1600;
            jy-=979;
        }else{
            gy=621;
        }
        int days = (365 * jy) + (((int)(jy / 33)) * 8) + ((int)(((jy % 33) + 3) / 4)) + 78 + jd + ((jm < 7)?(jm - 1) * 31:((jm - 7) * 30) + 186);
        gy += 400 * ((int)(days / 146097));
        days %= 146097;
        if(days > 36524){
            gy += 100 * ((int)(--days / 36524));
            days %= 36524;
            if (days >= 365)days++;
        }
        gy += 4 * ((int)(days / 1461));
        days %= 1461;
        if(days > 365){
            gy += (int)((days - 1) / 365);
            days = (days - 1) % 365;
        }
        int gd = days + 1;
        int[] sal_a = {0,31,((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0))?29:28,31,30,31,30,31,31,30,31,30,31};
        int gm;
        for(gm = 0;gm < 13;gm++){
            int v = sal_a[gm];
            if(gd <= v)break;
            gd -= v;
        }
        int[] out = {gy,gm,gd};

        return out;
    }
    public static Date jalali_to_unixDate(int jy, int jm, int jd){
        int gy;
        if(jy>979){
            gy=1600;
            jy-=979;
        }else{
            gy=621;
        }
        int days = (365 * jy) + (((int)(jy / 33)) * 8) + ((int)(((jy % 33) + 3) / 4)) + 78 + jd + ((jm < 7)?(jm - 1) * 31:((jm - 7) * 30) + 186);
        gy += 400 * ((int)(days / 146097));
        days %= 146097;
        if(days > 36524){
            gy += 100 * ((int)(--days / 36524));
            days %= 36524;
            if (days >= 365)days++;
        }
        gy += 4 * ((int)(days / 1461));
        days %= 1461;
        if(days > 365){
            gy += (int)((days - 1) / 365);
            days = (days - 1) % 365;
        }
        int gd = days + 1;
        int[] sal_a = {0,31,((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0))?29:28,31,30,31,30,31,31,30,31,30,31};
        int gm;
        for(gm = 0;gm < 13;gm++){
            int v = sal_a[gm];
            if(gd <= v)break;
            gd -= v;
        }
        return new Date(gy,gm,gd);
    }



}

