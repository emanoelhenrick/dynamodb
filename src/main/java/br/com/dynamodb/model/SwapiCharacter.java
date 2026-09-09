package br.com.dynamodb.model;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class SwapiCharacter {

    private Integer id;
    private String name;
    private Long height;
    private Long mass;
    private String gender;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public Integer getId() {
        return id;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "xName")
    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("height")
    public Long getHeight() {
        return height;
    }

    @DynamoDbAttribute("mass")
    public Long getMass() {
        return mass;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "xGender")
    @DynamoDbAttribute("gender")
    public String getGender() {
        return gender;
    }

}