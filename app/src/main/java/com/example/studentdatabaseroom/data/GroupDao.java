package com.example.studentdatabaseroom.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM groups ORDER BY faculty_name, number")
    List<Group> selectGroups();

    @Query("SELECT * FROM groups WHERE id=:groupId")
    Group selectGroup(long groupId);

    @Query("SELECT * FROM groups WHERE id=:groupId")
    GroupWithStudents selectGroupWithStudents(long groupId);

    @Insert
    long insert(Group group);

    @Update
    int update(Group group);

    @Delete
    int delete(Group group);

}
