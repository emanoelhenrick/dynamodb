package br.com.dynamodb.repository;

import br.com.dynamodb.model.SwapiCharacter;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterDynamoDbRepository {

    @Autowired
    DynamoDbTemplate dynamoDbTemplate;

    public SwapiCharacter saveCharacter(SwapiCharacter character) {
        return dynamoDbTemplate.save(character);
    }

    public Optional<SwapiCharacter> findByName(String name) {
        var key = Key.builder().partitionValue(name).build();
        var queryEnhancedRequest = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .build();

        PageIterable<SwapiCharacter> characters = dynamoDbTemplate.query(
                queryEnhancedRequest,
                SwapiCharacter.class,
                "xName"
        );

        return characters.items().stream().findFirst();
    }

    public List<SwapiCharacter> findByGender(String gender) {
        var key = Key.builder().partitionValue(gender).build();
        var queryEnhancedRequest = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .build();

        PageIterable<SwapiCharacter> characters = dynamoDbTemplate.query(
                queryEnhancedRequest,
                SwapiCharacter.class,
                "xGender"
        );

        return characters.items().stream().toList();
    }

    public List<SwapiCharacter> findAllCharacters() {
        var characters = dynamoDbTemplate.scanAll(SwapiCharacter.class);
        return characters.items().stream().toList();
    }
}
