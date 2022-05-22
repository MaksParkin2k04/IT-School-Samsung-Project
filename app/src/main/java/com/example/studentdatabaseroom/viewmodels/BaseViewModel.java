package com.example.studentdatabaseroom.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    protected final MutableLiveData<String> error = new MutableLiveData<>(null);
    protected final MutableLiveData<Boolean> operationCompleted = new MutableLiveData<>(false);

    public LiveData<String> getError() {
        return error;
    }
    public LiveData<Boolean> getOperationCompleted() {
        return operationCompleted;
    }
}
