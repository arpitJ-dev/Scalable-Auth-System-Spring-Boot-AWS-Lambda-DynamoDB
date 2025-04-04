# User Management System API Documentation

## Overview
The User Management System provides a comprehensive REST API for managing user data in a serverless environment. Built with Spring Boot and deployed on AWS Lambda with DynamoDB storage.

## Base URL
```
https://your-api-gateway-url.amazonaws.com/user
```

## Authentication
Currently, the API does not require authentication. For production use, consider implementing AWS Cognito or API Gateway authorizers.

## API Endpoints

### 1. Create User
Creates a new user in the system.

**Endpoint:** `POST /user`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "age": 30,
  "department": "Engineering",
  "role": "Software Engineer",
  "phoneNumber": "+1234567890"
}
```

**Response (201 Created):**
```json
{
  "uuid": "generated-uuid-123",
  "name": "John Doe",
  "email": "john.doe@example.com",
  "age": 30,
  "department": "Engineering",
  "role": "Software Engineer",
  "phoneNumber": "+1234567890",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "isActive": true
}
```

### 2. Get User
Retrieves a user by their unique identifier.

**Endpoint:** `GET /user/{uuid}`

**Parameters:**
- `uuid` (path): User's unique identifier

**Response (200 OK):**
```json
{
  "uuid": "generated-uuid-123",
  "name": "John Doe",
  "email": "john.doe@example.com",
  "age": 30,
  "department": "Engineering",
  "role": "Software Engineer",
  "phoneNumber": "+1234567890",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "isActive": true
}
```

### 3. Update User
Updates an existing user's information.

**Endpoint:** `PUT /user`

**Request Body:**
```json
{
  "uuid": "generated-uuid-123",
  "name": "John Smith",
  "email": "john.smith@example.com",
  "age": 31,
  "department": "Engineering",
  "role": "Senior Software Engineer",
  "phoneNumber": "+1234567890"
}
```

**Response (200 OK):**
```json
{
  "uuid": "generated-uuid-123",
  "name": "John Smith",
  "email": "john.smith@example.com",
  "age": 31,
  "department": "Engineering",
  "role": "Senior Software Engineer",
  "phoneNumber": "+1234567890",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T11:45:00",
  "isActive": true
}
```

### 4. Delete User
Deletes a user from the system.

**Endpoint:** `DELETE /user/{uuid}`

**Parameters:**
- `uuid` (path): User's unique identifier

**Response (200 OK):**
```json
{
  "message": "User successfully deleted",
  "uuid": "generated-uuid-123",
  "timestamp": "2024-01-15T12:00:00"
}
```

### 5. Get All Users
Retrieves all users from the system.

**Endpoint:** `GET /user/all`

**Response (200 OK):**
```json
[
  {
    "uuid": "generated-uuid-123",
    "name": "John Doe",
    "email": "john.doe@example.com",
    "age": 30,
    "department": "Engineering",
    "role": "Software Engineer",
    "phoneNumber": "+1234567890",
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00",
    "isActive": true
  }
]
```

### 6. Get Users by Department
Retrieves users filtered by department.

**Endpoint:** `GET /user/department/{department}`

**Parameters:**
- `department` (path): Department name to filter by

**Response (200 OK):**
```json
[
  {
    "uuid": "generated-uuid-123",
    "name": "John Doe",
    "email": "john.doe@example.com",
    "age": 30,
    "department": "Engineering",
    "role": "Software Engineer",
    "phoneNumber": "+1234567890",
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00",
    "isActive": true
  }
]
```

### 7. Health Check
Returns the health status of the service.

**Endpoint:** `GET /user/health`

**Response (200 OK):**
```json
{
  "status": "UP",
  "service": "User Management System",
  "timestamp": "2024-01-15T12:00:00",
  "version": "1.0.0"
}
```

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-01-15T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "User name is required",
  "path": "/user"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-15T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with ID: non-existent-uuid",
  "path": "/user/non-existent-uuid"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-15T12:00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/user"
}
```

## Data Validation

### User Entity Validation Rules:
- **name**: Required, cannot be blank
- **email**: Required, must be a valid email format
- **age**: Required, must be between 18 and 120
- **uuid**: Auto-generated, cannot be manually set
- **createdAt**: Auto-set on creation
- **updatedAt**: Auto-updated on modification
- **isActive**: Defaults to true

## Rate Limiting
API Gateway provides built-in rate limiting. Default limits:
- 10,000 requests per second per AWS account
- 5,000 requests per second per API key

## CORS
Cross-Origin Resource Sharing is configured to allow requests from any origin. For production, restrict to specific domains.

## Example Usage

### Using cURL

**Create a user:**
```bash
curl -X POST https://your-api-gateway-url.amazonaws.com/user \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "age": 28,
    "department": "Marketing",
    "role": "Marketing Manager",
    "phoneNumber": "+1987654321"
  }'
```

**Get a user:**
```bash
curl -X GET https://your-api-gateway-url.amazonaws.com/user/user-uuid-123
```

**Update a user:**
```bash
curl -X PUT https://your-api-gateway-url.amazonaws.com/user \
  -H "Content-Type: application/json" \
  -d '{
    "uuid": "user-uuid-123",
    "name": "Jane Johnson",
    "email": "jane.johnson@example.com",
    "age": 29,
    "department": "Marketing",
    "role": "Senior Marketing Manager",
    "phoneNumber": "+1987654321"
  }'
```

**Delete a user:**
```bash
curl -X DELETE https://your-api-gateway-url.amazonaws.com/user/user-uuid-123
```

### Using JavaScript (Fetch API)

```javascript
// Create a user
const createUser = async (userData) => {
  const response = await fetch('https://your-api-gateway-url.amazonaws.com/user', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userData)
  });
  return response.json();
};

// Get all users
const getAllUsers = async () => {
  const response = await fetch('https://your-api-gateway-url.amazonaws.com/user/all');
  return response.json();
};

// Usage
const newUser = await createUser({
  name: "Alice Johnson",
  email: "alice.johnson@example.com",
  age: 25,
  department: "HR",
  role: "HR Specialist",
  phoneNumber: "+1555555555"
});
```

## Monitoring and Logging

The system includes comprehensive monitoring through AWS CloudWatch:
- Request/response logging
- Performance metrics
- Error tracking
- Custom business metrics

## Deployment Notes

- The API is deployed using AWS CloudFormation
- Lambda function automatically scales based on demand
- DynamoDB provides automatic backup and point-in-time recovery
- API Gateway provides caching and throttling capabilities
