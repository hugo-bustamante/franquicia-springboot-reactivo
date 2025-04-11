#!/bin/bash

set -e

ECR_REPO_NAME="franquicia-repo"
AWS_REGION="us-east-1"
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
IMAGE_TAG="latest"

cd infra/terraform

echo "🚀 Inicializando Terraform..."
terraform init

echo "🧱 Aplicando solo el repositorio ECR..."
terraform apply -target=aws_ecr_repository.franquicia -auto-approve

REPO_URI="$ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO_NAME"

echo "🔐 Autenticando Docker con ECR..."
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $REPO_URI

echo "🐳 Construyendo imagen Docker..."
cd ../.. 
docker build --no-cache -t $ECR_REPO_NAME .

echo "🏷️ Etiquetando imagen..."
docker tag $ECR_REPO_NAME:latest $REPO_URI:$IMAGE_TAG

echo "📤 Subiendo imagen a ECR..."
docker push $REPO_URI:$IMAGE_TAG

cd infra/terraform
echo "🛠️ Aplicando Terraform completo..."
terraform apply -auto-approve

echo "✅ Despliegue completado."