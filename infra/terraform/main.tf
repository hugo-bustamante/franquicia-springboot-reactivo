provider "aws" {
  region     = var.aws_region
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}

# ECR
resource "aws_ecr_repository" "franquicia" {
  name = var.ecr_repo_name
}

# IAM Role para Lambda
resource "aws_iam_role" "lambda_role" {
  name = "lambda_execution_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Effect = "Allow",
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      }
    ]
  })
}

# Permisos para Lambda: ECR, CloudWatch
resource "aws_iam_role_policy_attachment" "lambda_logs" {
  role       = aws_iam_role.lambda_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

resource "aws_iam_role_policy_attachment" "ecr_access" {
  role       = aws_iam_role.lambda_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
}

# Lambda desde imagen en ECR
resource "aws_lambda_function" "franquicia_lambda" {
  function_name = var.lambda_name
  role          = aws_iam_role.lambda_role.arn
  package_type  = "Image"
  image_uri     = "${aws_ecr_repository.franquicia.repository_url}:latest"
  timeout       = 60
  memory_size   = 1024

  environment {
    variables = {
      SPRING_DATA_MONGODB_URI = var.mongodb_uri
    }
  }
}

# API Gateway
resource "aws_apigatewayv2_api" "http_api" {
  name          = "franquicia-http-api"
  protocol_type = "HTTP"
}

resource "aws_apigatewayv2_integration" "lambda_integration" {
  api_id                = aws_apigatewayv2_api.http_api.id
  integration_type      = "AWS_PROXY"
  integration_uri       = aws_lambda_function.franquicia_lambda.invoke_arn
  integration_method    = "POST"
  payload_format_version = "2.0"
}

# Rutas del API Gateway (agregadas según el controlador)
resource "aws_apigatewayv2_route" "create_franquicia" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "POST /api/franquicias"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "agregar_sucursal" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "POST /api/franquicias/{id}/sucursales"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "add_product_to_sucursal" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "POST /api/franquicias/{id}/sucursales/{sucursalId}/products"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "remove_product_from_sucursal" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "DELETE /api/franquicias/{id}/sucursales/{sucursalId}/products/{productId}"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "update_stock" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "PUT /api/franquicias/{id}/sucursales/{sucursalId}/products/{productId}/stock"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "largest_stock" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "GET /api/franquicias/{id}/products-largest-stock"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "update_name_franquicia" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "PUT /api/franquicias/{id}/name"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "update_name_sucursal" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "PUT /api/franquicias/{id}/sucursales/{sucursalId}/name"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

resource "aws_apigatewayv2_route" "update_name_product" {
  api_id    = aws_apigatewayv2_api.http_api.id
  route_key = "PUT /api/franquicias/{id}/sucursales/{sucursalId}/products/{productId}/name"
  target    = "integrations/${aws_apigatewayv2_integration.lambda_integration.id}"
}

# Permiso para que API Gateway invoque la Lambda
resource "aws_lambda_permission" "apigw" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.franquicia_lambda.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_apigatewayv2_api.http_api.execution_arn}/*/*"
}