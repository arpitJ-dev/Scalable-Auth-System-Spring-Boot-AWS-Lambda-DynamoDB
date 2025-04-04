# User Management System - Deployment Guide

## Prerequisites

Before deploying the User Management System, ensure you have the following:

### Required Tools
- **AWS CLI** (v2.0+) configured with appropriate permissions
- **Java 8** or higher
- **Gradle** (included with project)
- **Git** for version control

### AWS Permissions
Your AWS credentials need the following permissions:
- CloudFormation (create/update/delete stacks)
- Lambda (create/update/delete functions)
- API Gateway (create/update/delete APIs)
- DynamoDB (create/update/delete tables)
- IAM (create/update/delete roles and policies)
- S3 (upload deployment packages)

### AWS Resources
- S3 bucket for storing deployment artifacts
- AWS region (default: us-east-1)

## Step-by-Step Deployment

### 1. Prepare the Environment

Clone and navigate to the project directory:
```bash
git clone <your-repository-url>
cd user-management-system
```

### 2. Build the Application

Build the Java application and create the deployment package:
```bash
cd Customer
./gradlew clean build
```

This will create `UserManagement-1.0.0.zip` in the `build/distributions/` directory.

### 3. Prepare S3 Bucket

Create an S3 bucket for storing deployment artifacts:
```bash
aws s3 mb s3://your-user-management-deployment-bucket --region us-east-1
```

Upload the CloudFormation templates:
```bash
aws s3 cp ../master.yaml s3://your-user-management-deployment-bucket/
aws s3 cp ../dynamodb.yaml s3://your-user-management-deployment-bucket/
aws s3 cp ../lambda.yaml s3://your-user-management-deployment-bucket/
aws s3 cp ../apigateway.yaml s3://your-user-management-deployment-bucket/
```

Upload the Lambda deployment package:
```bash
aws s3 cp build/distributions/UserManagement-1.0.0.zip s3://your-user-management-deployment-bucket/
```

### 4. Deploy Infrastructure

Deploy the CloudFormation stack:
```bash
aws cloudformation create-stack \
  --stack-name user-management-system \
  --template-url https://s3.amazonaws.com/your-user-management-deployment-bucket/master.yaml \
  --parameters \
    ParameterKey=LambdaCodeS3Bucket,ParameterValue=your-user-management-deployment-bucket \
    ParameterKey=LambdaCodeS3Key,ParameterValue=UserManagement-1.0.0.zip \
    ParameterKey=TemplateURLDynamoDB,ParameterValue=https://s3.amazonaws.com/your-user-management-deployment-bucket/dynamodb.yaml \
    ParameterKey=TemplateURLambda,ParameterValue=https://s3.amazonaws.com/your-user-management-deployment-bucket/lambda.yaml \
    ParameterKey=TemplateURLApiGateway,ParameterValue=https://s3.amazonaws.com/your-user-management-deployment-bucket/apigateway.yaml \
    ParameterKey=DynamoDBTableName,ParameterValue=User \
    ParameterKey=LambdaFunctionName,ParameterValue=UserManagementSystem \
    ParameterKey=LambdaRuntime,ParameterValue=java8 \
  --capabilities CAPABILITY_IAM \
  --region us-east-1
```

### 5. Monitor Deployment

Check the CloudFormation stack status:
```bash
aws cloudformation describe-stacks \
  --stack-name user-management-system \
  --region us-east-1
```

View stack events for detailed progress:
```bash
aws cloudformation describe-stack-events \
  --stack-name user-management-system \
  --region us-east-1
```

### 6. Get API Gateway URL

Once deployment is complete, retrieve the API Gateway URL:
```bash
aws cloudformation describe-stacks \
  --stack-name user-management-system \
  --query 'Stacks[0].Outputs[?OutputKey==`ApiGatewayUrl`].OutputValue' \
  --output text \
  --region us-east-1
```

### 7. Test the Deployment

Test the API endpoints using the retrieved URL:

**Health Check:**
```bash
curl https://your-api-gateway-url.amazonaws.com/user/health
```

**Create a Test User:**
```bash
curl -X POST https://your-api-gateway-url.amazonaws.com/user \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "age": 25,
    "department": "Testing",
    "role": "Test Engineer"
  }'
```

## Environment-Specific Configurations

### Development Environment
```bash
aws cloudformation create-stack \
  --stack-name user-management-dev \
  --template-url https://s3.amazonaws.com/your-bucket/master.yaml \
  --parameters \
    ParameterKey=DynamoDBTableName,ParameterValue=UserDev \
    ParameterKey=LambdaFunctionName,ParameterValue=UserManagementDev \
  --capabilities CAPABILITY_IAM
```

### Production Environment
```bash
aws cloudformation create-stack \
  --stack-name user-management-prod \
  --template-url https://s3.amazonaws.com/your-bucket/master.yaml \
  --parameters \
    ParameterKey=DynamoDBTableName,ParameterValue=UserProd \
    ParameterKey=LambdaFunctionName,ParameterValue=UserManagementProd \
  --capabilities CAPABILITY_IAM
```

## Updating the Application

### Code Updates
1. Make your code changes
2. Rebuild the application:
   ```bash
   ./gradlew clean build
   ```
3. Upload the new deployment package:
   ```bash
   aws s3 cp build/distributions/UserManagement-1.0.0.zip s3://your-bucket/
   ```
4. Update the Lambda function:
   ```bash
   aws lambda update-function-code \
     --function-name UserManagementSystem \
     --s3-bucket your-bucket \
     --s3-key UserManagement-1.0.0.zip
   ```

### Infrastructure Updates
1. Update CloudFormation templates
2. Upload updated templates to S3
3. Update the CloudFormation stack:
   ```bash
   aws cloudformation update-stack \
     --stack-name user-management-system \
     --template-url https://s3.amazonaws.com/your-bucket/master.yaml \
     --parameters file://parameters.json
   ```

## Troubleshooting

### Common Issues

**1. Stack Creation Fails**
- Check IAM permissions
- Verify S3 bucket accessibility
- Review CloudFormation events for specific errors

**2. Lambda Function Errors**
- Check CloudWatch logs
- Verify DynamoDB table permissions
- Ensure correct Lambda handler configuration

**3. API Gateway Issues**
- Verify Lambda function integration
- Check CORS configuration
- Review API Gateway logs

### Useful Commands

**View Lambda Logs:**
```bash
aws logs tail /aws/lambda/UserManagementSystem --follow
```

**Test Lambda Function:**
```bash
aws lambda invoke \
  --function-name UserManagementSystem \
  --payload '{"httpMethod":"GET","path":"/user/health"}' \
  response.json
```

**Check DynamoDB Table:**
```bash
aws dynamodb scan --table-name User --region us-east-1
```

## Cleanup

To remove all resources:

```bash
aws cloudformation delete-stack --stack-name user-management-system
```

**Note:** This will delete all data in the DynamoDB table. Ensure you have backups if needed.

## Security Considerations

### Production Deployment
1. **Enable encryption** for DynamoDB table
2. **Configure VPC** for Lambda function if required
3. **Implement API Gateway authentication** (Cognito, API Keys)
4. **Set up CloudTrail** for audit logging
5. **Configure backup** for DynamoDB
6. **Enable AWS Config** for compliance monitoring

### IAM Best Practices
- Use least privilege principle
- Rotate access keys regularly
- Enable MFA for root account
- Use IAM roles instead of access keys where possible

## Monitoring and Alerting

### CloudWatch Metrics
Set up alerts for:
- Lambda function errors
- API Gateway 4xx/5xx errors
- DynamoDB throttling
- High latency

### Example CloudWatch Alarm:
```bash
aws cloudwatch put-metric-alarm \
  --alarm-name "UserManagement-Lambda-Errors" \
  --alarm-description "Lambda function errors" \
  --metric-name Errors \
  --namespace AWS/Lambda \
  --statistic Sum \
  --period 300 \
  --threshold 5 \
  --comparison-operator GreaterThanThreshold \
  --dimensions Name=FunctionName,Value=UserManagementSystem
```

## Cost Optimization

### Development Environment
- Use on-demand DynamoDB billing
- Set shorter Lambda timeout
- Use smaller Lambda memory allocation

### Production Environment
- Use provisioned DynamoDB capacity
- Optimize Lambda memory allocation
- Enable DynamoDB auto-scaling
- Use API Gateway caching
