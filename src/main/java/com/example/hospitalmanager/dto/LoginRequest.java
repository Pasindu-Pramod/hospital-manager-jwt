package com.example.hospitalmanager.dto;

public record LoginRequest(
    String username,
    String password
) {}