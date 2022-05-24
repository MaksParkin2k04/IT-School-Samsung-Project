package com.example.studentdatabaseroom.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "groups")
public class Group {

    public Group(long id, int number, String facultyName) {
        this.id = id;
        this.number = number;
        this.facultyName = facultyName;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private final long id;

    @ColumnInfo(name = "number")
    private int number;

    @ColumnInfo(name = "faculty_name")
    private String facultyName;

    public long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return facultyName + " " + String.valueOf(number);
    }
}
