package br.com.dynamodb.service.impl;

import br.com.dynamodb.dto.SwapiCharacterDTO;
import br.com.dynamodb.exceptions.ResourceNotFoundException;
import br.com.dynamodb.exceptions.UnprocessableEntityException;
import br.com.dynamodb.mapper.Mapper;
import br.com.dynamodb.repository.CharacterDynamoDbRepository;
import br.com.dynamodb.service.SwapiCharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class SwapiCharacterServiceImpl implements SwapiCharacterService {

    private final RestClient restClient;
    private final CharacterDynamoDbRepository characterDynamoDbRepository;
    
    public Mapper mapper = new Mapper();

    public SwapiCharacterServiceImpl(RestClient swapiRestClient, CharacterDynamoDbRepository characterDynamoDbRepository) {
        this.restClient = swapiRestClient;
        this.characterDynamoDbRepository = characterDynamoDbRepository;
    }

    public SwapiCharacterDTO saveCharacter(Integer charId) {
        var swapiCharacterDTO = findCharacterById(charId);
        var swapiCharacter = mapper.toCreateSwapiCharacter(swapiCharacterDTO, charId);
        characterDynamoDbRepository.saveCharacter(swapiCharacter);
        return swapiCharacterDTO;
    }

    public SwapiCharacterDTO findCharacterById(Integer id) {
        if (id == null || id <= 0) {
            throw new UnprocessableEntityException("The character id must be greater than zero");
        }

        try {
            return restClient.get()
                    .uri("/people/{id}", id)
                    .retrieve()
                    .body(SwapiCharacterDTO.class);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new ResourceNotFoundException("There is not character with this id");
            }

            throw new UnprocessableEntityException("The SWAPI request is invalid");
        }
    }

}
