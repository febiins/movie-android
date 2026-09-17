package com.example.movue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.movue.data.repository.AuthRepository;
import com.example.movue.model.User;
import com.example.movue.utils.Resource;

public class AuthViewModel extends ViewModel {
    private AuthRepository repository;

    public AuthViewModel(AuthRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<User>> login(String email, String password) {
        return repository.login(email, password);
    }

    public LiveData<Resource<User>> register(String username, String email, String password) {
        return repository.register(username, email, password);
    }

    public void logout() {
        repository.logout();
    }
}