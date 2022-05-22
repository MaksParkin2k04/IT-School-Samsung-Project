package com.example.studentdatabaseroom.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "student")
public  class Student{

    public Student(long id, String name, String patronymic, String surname, Date dateBirth, long groupId) {
        this.id = id;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.dateBirth = dateBirth;
        this.groupId = groupId;
    }
    @PrimaryKey(autoGenerate = true)
    private final long id;
    private String name;
    private String patronymic;
    private String surname;
    @ColumnInfo(name = "data_birth")
    @TypeConverters({DateConverter.class})
    private Date dateBirth;
    @ColumnInfo(name = "group_id")
    private final long groupId;



    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPatronymic() {
        return patronymic;
    }


    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }


    public String getSurname() {
        return surname;
    }


    public void setSurname(String surname) {
        this.surname = surname;
    }


    public Date getDateBirth() {
        return dateBirth;
    }


    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }


    public long getGroupId() {
        return groupId;
    }
}
