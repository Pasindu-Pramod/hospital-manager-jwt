package com.example.hospitalmanager.dto;

import com.example.hospitalmanager.model.Role;

public record AuthResponse(
    String token,
    String tokenType,
    long expiresInMs,
    String username,
    String fullName,
    Role role
) {}