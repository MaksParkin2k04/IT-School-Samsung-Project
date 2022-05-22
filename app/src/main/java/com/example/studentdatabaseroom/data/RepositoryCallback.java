package com.example.studentdatabaseroom.data;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
