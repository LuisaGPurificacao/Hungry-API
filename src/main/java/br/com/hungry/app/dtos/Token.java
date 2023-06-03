package br.com.hungry.app.dtos;

public record Token(
    String token,
    String type,
    String prefix
) {}