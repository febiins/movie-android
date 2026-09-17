package com.example.movue.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.movue.data.api.ApiService;
import com.example.movue.data.local.PreferencesManager;
import com.example.movue.model.User;
import com.example.movue.utils.Resource;

public class AuthRepository {
    private ApiService apiService;
    private PreferencesManager preferencesManager;

    public AuthRepository(ApiService apiService, PreferencesManager preferencesManager) {
        this.apiService = apiService;
        this.preferencesManager = preferencesManager;
    }

    public LiveData<Resource<User>> login(String email, String password) {
        MutableLiveData<Resource<User>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        // Mock login
        if ("user@example.com".equals(email) && "password".equals(password)) {
            User user = new User(1, "JohnDoe", email);
            preferencesManager.setLoginState(true);
            preferencesManager.saveUsername(user.getUsername());
            result.setValue(Resource.success(user));
        } else {
            result.setValue(Resource.error("Invalid credentials", null));
        }
        return result;
    }

    public LiveData<Resource<User>> register(String username, String email, String password) {
        MutableLiveData<Resource<User>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        // Mock register
        User user = new User(1, username, email);
        preferencesManager.setLoginState(true);
        preferencesManager.saveUsername(username);
        result.setValue(Resource.success(user));
        
        return result;
    }

    public void logout() {
        preferencesManager.clear();
    }
}