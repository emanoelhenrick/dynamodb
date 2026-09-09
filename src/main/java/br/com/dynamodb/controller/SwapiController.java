package br.com.dynamodb.controller;

import br.com.dynamodb.dto.SwapiCharacterDTO;
import br.com.dynamodb.service.SwapiCharacterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swapi")
public class SwapiController {

    private final SwapiCharacterService swapiCharacterService;

    public SwapiController(SwapiCharacterService swapiCharacterService) {
        this.swapiCharacterService = swapiCharacterService;
    }

    @GetMapping("/characters/{id}")
    public SwapiCharacterDTO getCharacter(@PathVariable Integer id) {
        return swapiCharacterService.findCharacterById(id);
    }

    @PostMapping("/characters/{id}")
    public SwapiCharacterDTO createCharacter(@PathVariable Integer id) {
        return swapiCharacterService.saveCharacter(id);
    }

}
