package br.com.dynamodb.service;

import br.com.dynamodb.dto.SwapiCharacterDTO;

public interface SwapiCharacterService {

    SwapiCharacterDTO saveCharacter(Integer charId);
    SwapiCharacterDTO findCharacterById(Integer id);

}
