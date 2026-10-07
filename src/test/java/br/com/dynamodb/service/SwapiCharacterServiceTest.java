package br.com.dynamodb.service;

import br.com.dynamodb.exceptions.ResourceNotFoundException;
import br.com.dynamodb.exceptions.UnprocessableEntityException;
import br.com.dynamodb.repository.CharacterDynamoDbRepository;
import br.com.dynamodb.service.impl.SwapiCharacterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SwapiCharacterServiceTest {

    @InjectMocks
    private SwapiCharacterServiceImpl service;

    @Mock
    private RestClient restClient;

    @Mock
    private CharacterDynamoDbRepository characterDynamoDbRepository;

    @Test
    void findCharacterById_WithNullId_ThrowsUnprocessableEntityException() {
        assertThatThrownBy(() -> service.findCharacterById(null))
                .isInstanceOf(UnprocessableEntityException.class)
                .hasMessage("The character id must be greater than zero");
    }

    @Test
    void findCharacterById_WithNonPositiveId_ThrowsUnprocessableEntityException() {
        assertThatThrownBy(() -> service.findCharacterById(0))
                .isInstanceOf(UnprocessableEntityException.class)
                .hasMessage("The character id must be greater than zero");
    }

    @Test
    void findCharacterById_WhenSwapiReturnsNotFound_ThrowsResourceNotFoundException() {
        var exception = HttpClientErrorException.create(
                HttpStatus.NOT_FOUND,
                "Not Found",
                HttpHeaders.EMPTY,
                null,
                StandardCharsets.UTF_8);
        when(restClient.get()).thenThrow(exception);

        assertThatThrownBy(() -> service.findCharacterById(999))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("There is not character with this id");
    }
}