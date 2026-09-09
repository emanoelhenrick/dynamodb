package br.com.dynamodb.dto;

public record SwapiCharacterDTO(
    String name,
    String height,
    String mass,
    String gender
) {
}
