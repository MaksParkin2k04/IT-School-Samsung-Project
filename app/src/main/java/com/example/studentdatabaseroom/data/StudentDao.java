package com.example.studentdatabaseroom.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM student WHERE group_id=:studentId")
    List<Student> selectStudents(long studentId);

    @Query("SELECT * FROM student WHERE id=:studentId")
    Student selectStudent(long studentId);

    @Query("SELECT * FROM student WHERE surname LIKE :surname")
    List<Student> selectStudents(String surname);

    @Query("SELECT * FROM student WHERE group_id=:groupId AND surname LIKE :surname")
    List<Student> selectStudents(String surname, long groupId);

    @Insert
    long insert(Student student);

    @Update
    int update(Student student);

    @Delete
    int delete(Student student);

}
