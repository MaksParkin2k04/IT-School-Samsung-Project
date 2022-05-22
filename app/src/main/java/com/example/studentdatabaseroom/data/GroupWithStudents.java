package com.example.studentdatabaseroom.data;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

public class GroupWithStudents {


    public GroupWithStudents(int id, int number, String facultyName, List<Student> students) {
        this.id = id;
        this.number = number;
        this.facultyName = facultyName;
        this.students = students;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private final int id;

    @ColumnInfo(name = "number")
    private final int number;

    @ColumnInfo(name = "faculty_name")
    private final String facultyName;

    @Relation(parentColumn = "id", entityColumn = "group_id")
    private final List<Student> students;

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public String getFullName(){
        return facultyName + " " + String.valueOf(number);
    }
}
