package com.aws.lambda.user.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.aws.lambda.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository implementation for User data access operations using DynamoDB.
 * Handles all database interactions for user-related operations.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Override
    public User save(User user) {
        dynamoDBMapper.save(user);
        return user;
    }

    @Override
    public User findById(String uuid) {
        return dynamoDBMapper.load(User.class, uuid);
    }

    @Override
    public List<User> findAll() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return dynamoDBMapper.scan(User.class, scanExpression);
    }

    @Override
    public void delete(String uuid) {
        User user = new User();
        user.setUuid(uuid);
        dynamoDBMapper.delete(user);
    }

    @Override
    public List<User> findByDepartment(String department) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":department", new AttributeValue().withS(department));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("department = :department")
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDBMapper.scan(User.class, scanExpression);
    }

    @Override
    public List<User> findByRole(String role) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":role", new AttributeValue().withS(role));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("role = :role")
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDBMapper.scan(User.class, scanExpression);
    }

    @Override
    public User findByEmail(String email) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":email", new AttributeValue().withS(email));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("email = :email")
                .withExpressionAttributeValues(expressionAttributeValues);

        List<User> users = dynamoDBMapper.scan(User.class, scanExpression);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> findByNameContaining(String name) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":name", new AttributeValue().withS(name.toLowerCase()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(lower(name), :name)")
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDBMapper.scan(User.class, scanExpression);
    }

    @Override
    public boolean existsById(String uuid) {
        User user = findById(uuid);
        return user != null;
    }

    @Override
    public long count() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return dynamoDBMapper.count(User.class, scanExpression);
    }

}
