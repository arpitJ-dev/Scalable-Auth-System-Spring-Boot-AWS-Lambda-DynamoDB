# Enterprise User Management System - AWS Serverless Architecture

[![AWS](https://img.shields.io/badge/AWS-Serverless-orange?logo=amazon-aws)](https://aws.amazon.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.3.0-green?logo=spring)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-8-blue?logo=openjdk)](https://openjdk.java.net/)
[![DynamoDB](https://img.shields.io/badge/Database-DynamoDB-yellow?logo=amazon-dynamodb)](https://aws.amazon.com/dynamodb/)
[![CloudFormation](https://img.shields.io/badge/Infrastructure-CloudFormation-red?logo=amazon-aws)](https://aws.amazon.com/cloudformation/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A comprehensive serverless application built with Spring Boot, AWS Lambda, DynamoDB, and API Gateway. This project demonstrates modern cloud-native architecture patterns and infrastructure-as-code practices using AWS CloudFormation.

![AWS Serverless Architecture](doc/img/serverless-aws.png)

## 🏗️ Architecture Overview

This enterprise-grade user management system leverages AWS serverless services to provide a scalable, cost-effective, and highly available solution for user data management. The architecture follows best practices for microservices and event-driven design.

### System Architecture Diagram



## 🚀 Key Features

- **RESTful API**: Complete CRUD operations for user management
- **Enhanced User Model**: Comprehensive user entity with validation and audit fields
- **Auto-scaling**: Handles traffic spikes automatically without manual intervention
- **Global Availability**: Multi-region deployment capabilities
- **Cost Optimization**: Pay-per-use pricing model
- **Security**: IAM-based access control and encryption at rest
- **Monitoring**: CloudWatch integration for logging and metrics
- **Soft Delete**: User activation/deactivation instead of hard deletion
- **Advanced Search**: Department and role-based filtering
- **Health Monitoring**: Built-in health check endpoints
- **Comprehensive Testing**: Unit and integration test suite

## 🛠️ Technology Stack

- **Backend**: Spring Boot 2.3.0 with Java 8
- **Database**: AWS DynamoDB with optimized data modeling
- **Infrastructure**: AWS CloudFormation with nested stacks
- **API**: RESTful endpoints with JSON serialization
- **Deployment**: Serverless architecture with zero server management

## 📁 Project Structure

```
user-management-system/
├── Customer/                          # Main Spring Boot application
│   ├── src/main/java/com/aws/lambda/user/
│   │   ├── entities/
│   │   │   └── User.java              # Enhanced User entity with validation
│   │   ├── controllers/
│   │   │   └── UserController.java    # REST API controller with CRUD operations
│   │   ├── services/
│   │   │   ├── UserService.java       # Service interface
│   │   │   └── UserServiceImpl.java   # Service implementation with business logic
│   │   ├── repositories/
│   │   │   ├── UserRepository.java    # Repository interface
│   │   │   └── UserRepositoryImpl.java # DynamoDB repository implementation
│   │   ├── configurations/
│   │   │   └── DynamoDBConfig.java    # DynamoDB configuration
│   │   ├── UserManagementApplication.java # Main Spring Boot application
│   │   └── StreamLambdaHandler.java   # AWS Lambda handler
│   ├── src/test/java/com/aws/lambda/user/
│   │   ├── controllers/
│   │   │   └── UserControllerTest.java # Controller unit tests
│   │   ├── services/
│   │   │   └── UserServiceTest.java    # Service unit tests
│   │   └── UserManagementApplicationTests.java # Integration tests
│   ├── src/main/resources/
│   │   └── application.properties     # Application configuration
│   ├── src/test/resources/
│   │   └── application-test.properties # Test configuration
│   ├── build.gradle                   # Build configuration with validation
│   ├── settings.gradle               # Gradle settings
│   ├── gradlew                        # Gradle wrapper (Unix)
│   └── gradlew.bat                    # Gradle wrapper (Windows)
├── CloudFormation Templates/
│   ├── master.yaml                    # Main deployment stack
│   ├── dynamodb.yaml                  # DynamoDB table configuration
│   ├── lambda.yaml                    # Lambda function setup
│   └── apigateway.yaml                # API Gateway configuration
├── Documentation/
│   ├── README.md                      # This comprehensive guide
│   ├── API_DOCUMENTATION.md           # Complete API reference
│   ├── DEPLOYMENT_GUIDE.md            # Step-by-step deployment guide
│   └── PROJECT_SUMMARY.md             # Technical achievements summary
├── doc/img/                           # Screenshots and diagrams
│   ├── serverless-aws.png             # Architecture overview
│   ├── dynamodb-mind-map.jpg          # DynamoDB structure
│   ├── api-gateway-mind-map.jpg       # API Gateway configuration
│   ├── master-mind-map.jpg            # CloudFormation stack structure
│   └── [other deployment screenshots]
├── conf/
│   └── buildspec.yml                  # CI/CD build specification
└── [Root Level Files]
    ├── master.yaml                    # CloudFormation master template
    ├── dynamodb.yaml                  # DynamoDB CloudFormation template
    ├── lambda.yaml                    # Lambda CloudFormation template
    └── apigateway.yaml                # API Gateway CloudFormation template
```

### Project Architecture Overview


### Key Components

#### 🏗️ **Application Layer (`Customer/src/main/java/com/aws/lambda/user/`)**
- **`UserManagementApplication.java`**: Main Spring Boot application entry point
- **`StreamLambdaHandler.java`**: AWS Lambda handler for serverless deployment
- **`entities/User.java`**: Enhanced User entity with validation and DynamoDB mapping
- **`controllers/UserController.java`**: REST API controller with comprehensive CRUD operations
- **`services/`**: Business logic layer with validation and error handling
- **`repositories/`**: Data access layer with DynamoDB integration
- **`configurations/DynamoDBConfig.java`**: AWS DynamoDB configuration

#### 🧪 **Testing Layer (`Customer/src/test/java/com/aws/lambda/user/`)**
- **`UserControllerTest.java`**: Unit tests for API endpoints
- **`UserServiceTest.java`**: Unit tests for business logic
- **`UserManagementApplicationTests.java`**: Integration tests for Spring context

#### ☁️ **Infrastructure Layer (CloudFormation Templates)**
- **`master.yaml`**: Orchestrates nested stack deployment
- **`dynamodb.yaml`**: Creates DynamoDB table with proper configuration
- **`lambda.yaml`**: Deploys Lambda function with IAM roles
- **`apigateway.yaml`**: Configures REST API with Lambda integration

#### 📚 **Documentation Layer**
- **`README.md`**: Comprehensive project guide
- **`API_DOCUMENTATION.md`**: Complete API reference
- **`DEPLOYMENT_GUIDE.md`**: Step-by-step deployment instructions
- **`PROJECT_SUMMARY.md`**: Technical achievements and features

### 🔄 **Development Workflow**
Refer to the Quick Start and Deployment Guide sections below for local development, build, and deployment steps.

## 📋 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/user` | Create a new user |
| GET | `/user/{uuid}` | Retrieve user by UUID |
| PUT | `/user` | Update existing user |
| DELETE | `/user/{uuid}` | Delete user by UUID |
| GET | `/user/all` | Retrieve all users |
| GET | `/user/department/{department}` | Get users by department |
| GET | `/user/health` | Health check endpoint |

## 🏛️ Architecture

- Serverless design using AWS Lambda, API Gateway, and DynamoDB
- Infrastructure as Code via CloudFormation templates
- IAM for access control and security

## 🔧 Deployment Guide

### Prerequisites
- AWS CLI configured with appropriate permissions
- Java 8+ development environment
- Gradle build tool

### Step-by-Step Deployment

1. **Prepare Application Package**
   ```bash
   cd Customer
   ./gradlew clean build
   ```

2. **Create S3 Bucket and Upload Files**
   ```bash
   # Create S3 bucket
   aws s3 mb s3://your-user-management-bucket --region us-east-1
   
   # Upload CloudFormation templates
   aws s3 cp ../master.yaml s3://your-user-management-bucket/
   aws s3 cp ../dynamodb.yaml s3://your-user-management-bucket/
   aws s3 cp ../lambda.yaml s3://your-user-management-bucket/
   aws s3 cp ../apigateway.yaml s3://your-user-management-bucket/
   
   # Upload Lambda deployment package
   aws s3 cp build/distributions/UserManagement-1.0.0.zip s3://your-user-management-bucket/
   ```

3. **Deploy Infrastructure**
   ```bash
   aws cloudformation create-stack \
     --stack-name user-management-system \
     --template-url https://s3.amazonaws.com/your-user-management-bucket/master.yaml \
     --parameters ParameterKey=LambdaCodeS3Bucket,ParameterValue=your-user-management-bucket \
                  ParameterKey=LambdaCodeS3Key,ParameterValue=UserManagement-1.0.0.zip \
                  ParameterKey=TemplateURLDynamoDB,ParameterValue=https://s3.amazonaws.com/your-user-management-bucket/dynamodb.yaml \
                  ParameterKey=TemplateURLambda,ParameterValue=https://s3.amazonaws.com/your-user-management-bucket/lambda.yaml \
                  ParameterKey=TemplateURLApiGateway,ParameterValue=https://s3.amazonaws.com/your-user-management-bucket/apigateway.yaml \
     --capabilities CAPABILITY_IAM
   ```

4. **Verify Deployment**
   - Check CloudFormation console for successful stack creation
   - Test API endpoints using the provided API Gateway URL
   - Monitor DynamoDB table for data persistence

### Deployment Screenshots

#### CloudFormation Stack Creation
![CloudFormation Stack Creation](doc/img/cloudformation-create-stack.png)
*Creating the CloudFormation stack with nested templates*

#### Stack Parameters Configuration
![CloudFormation Parameters](doc/img/cloudformation-stack-parameters.png)
*Configuring stack parameters for deployment*

#### Stack Events Monitoring
![CloudFormation Events](doc/img/cloudformation-stack-events.png)
*Monitoring stack creation progress and events*

#### API Gateway Deployment
![API Gateway Deployment](doc/img/apigateway-deploy.png)
*API Gateway service configuration and deployment*

#### DynamoDB Table Creation
![DynamoDB Table](doc/img/dynamodb-table-customer.png)
*DynamoDB User table with enhanced data structure after deployment*

## 📊 Performance Characteristics

Designed for low-latency operations and automatic scaling using managed AWS services.

## 🔒 Security

IAM-based access control; encryption in transit by default via API Gateway HTTPS.

## 📈 Monitoring

CloudWatch Logs and Metrics are used for observability.

## 🚀 Future Work

Planned items may include multi-tenancy and extended analytics.

## 📚 Documentation

- DynamoDB Table Structure: see `doc/img/dynamodb-mind-map.jpg`
- API Gateway Configuration: see `doc/img/api-gateway-mind-map.jpg`
- CloudFormation Stack Architecture: see `doc/img/master-mind-map.jpg`

## 🔍 Testing

Run unit and integration tests with `./gradlew test`.
## ⚡ Quick Start

### Prerequisites
- AWS CLI configured
- Java 8+
- Gradle

### Local Development
```bash
# Clone the repository
git clone <your-repository-url>
cd user-management-system

# Build the application
cd Customer
./gradlew clean build

# Run tests
./gradlew test

# Build deployment package
./gradlew buildZip
```

### Deploy to AWS
```bash
# Upload CloudFormation templates to S3
aws s3 cp master.yaml s3://your-bucket/
aws s3 cp dynamodb.yaml s3://your-bucket/
aws s3 cp lambda.yaml s3://your-bucket/
aws s3 cp apigateway.yaml s3://your-bucket/

# Upload Lambda deployment package
aws s3 cp build/distributions/UserManagement-1.0.0.zip s3://your-bucket/

# Deploy infrastructure
aws cloudformation create-stack \
  --stack-name user-management-system \
  --template-url https://s3.amazonaws.com/your-bucket/master.yaml \
  --parameters ParameterKey=LambdaCodeS3Bucket,ParameterValue=your-bucket \
               ParameterKey=LambdaCodeS3Key,ParameterValue=UserManagement-1.0.0.zip \
               ParameterKey=TemplateURLDynamoDB,ParameterValue=https://s3.amazonaws.com/your-bucket/dynamodb.yaml \
               ParameterKey=TemplateURLambda,ParameterValue=https://s3.amazonaws.com/your-bucket/lambda.yaml \
               ParameterKey=TemplateURLApiGateway,ParameterValue=https://s3.amazonaws.com/your-bucket/apigateway.yaml \
  --capabilities CAPABILITY_IAM
```

### Test the Deployment
```bash
# Get API Gateway URL
aws cloudformation describe-stacks \
  --stack-name user-management-system \
  --query 'Stacks[0].Outputs[?OutputKey==`ApiGatewayUrl`].OutputValue' \
  --output text

# Test health endpoint
curl https://your-api-gateway-url.amazonaws.com/user/health

# Create a test user
curl -X POST https://your-api-gateway-url.amazonaws.com/user \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "age": 30,
    "department": "Engineering",
    "role": "Software Engineer",
    "phoneNumber": "+1234567890"
  }'
```

## 📊 Metrics

Metrics depend on workload and AWS configuration; monitor via CloudWatch.

## 🎯 Use Cases

Enterprise user management with CRUD operations and search endpoints.

## 🔧 Troubleshooting

### Common Issues

**Build Failures**
```bash
# Clean and rebuild
./gradlew clean build

# Check Java version
java -version  # Should be Java 8+
```

**Deployment Issues**
- Ensure AWS CLI is configured with proper permissions
- Verify S3 bucket exists and is accessible
- Check CloudFormation stack events for specific errors

**API Testing**
```bash
# Test with verbose output
curl -v https://your-api-gateway-url.amazonaws.com/user/health

# Check Lambda logs
aws logs tail /aws/lambda/UserManagementSystem --follow
```

**DynamoDB Issues**
- Verify table exists: `aws dynamodb describe-table --table-name User`
- Check IAM permissions for Lambda function
- Ensure table has proper read/write capacity

## 🤝 Contributing

This project serves as a reference implementation for enterprise serverless architectures. Feel free to adapt and extend the codebase for your specific requirements.

### Development Guidelines
- Follow Spring Boot best practices
- Maintain comprehensive test coverage
- Document all API endpoints
- Use semantic versioning for releases

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- AWS for providing excellent serverless services
- Spring Boot team for the robust framework
- Open source community for continuous improvements

---

**Built with ❤️ using AWS Serverless Technologies**
