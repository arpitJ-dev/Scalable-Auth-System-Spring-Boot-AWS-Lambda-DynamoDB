package com.aws.lambda.user.configurations;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DynamoDB configuration class.
 * Sets up DynamoDB client and mapper beans for the application.
 */
@Configuration
public class DynamoDBConfig {

    @Value("${aws.region:us-east-1}")
    private String awsRegion;

    /**
     * Creates and configures the DynamoDB client.
     * 
     * @return Configured AmazonDynamoDB client
     */
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion(Regions.fromName(awsRegion))
                .build();
    }

    /**
     * Creates and configures the DynamoDB mapper.
     * 
     * @param amazonDynamoDB DynamoDB client instance
     * @return Configured DynamoDBMapper instance
     */
    @Bean
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }

}
