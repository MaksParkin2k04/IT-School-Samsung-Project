package com.example.studentdatabaseroom.data;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Long fromDate(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    public Date toDate(long date) {
        return new Date(date);
    }
}
