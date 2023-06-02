package br.com.hungry.app.dtos;

public record RestValidationError(String field, String message) {}