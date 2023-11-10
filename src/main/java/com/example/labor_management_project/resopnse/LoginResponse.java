package com.example.labor_management_project.resopnse;

import com.example.labor_management_project.model.User;
import lombok.Builder;

public class LoginResponse {
    public record LoginGenResponse(boolean status, String message, User user) {
        @Builder(toBuilder = true)
        public LoginGenResponse {
        }
    }
}
