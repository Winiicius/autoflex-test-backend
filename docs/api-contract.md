# API Contract — Autoflex Inventory API

## 🔐 Auth

### Login
POST /auth/login


**Request**
```json
{
  "username": "admin",
  "password": "123456"
}
```
**Response**

```json
{
  "token": "jwt",
  
  "user": {
    "id": 1,
    "username": "admin"
  }
}
```

## Product

Endpoints:  
POST   /products  
GET    /products  
GET    /products/{id}  
PUT    /products/{id}  
DELETE /products/{id}  

**ProductRequest**
```json
{
  "code": "P-001",
  "name": "Produto A",
  "price": 50.0,
  "materials": [
    {
      "rawMaterialId": 1,
      "quantity": 2.5
    }
  ]
}
```

**ProductResponse**
```json
{
  "id": 1,
  "code": "P-001",
  "name": "Produto A",
  "price": 50.0,
  "materials": [
    {
      "rawMaterialId": 1,
      "rawMaterialName": "Aço",
      "quantity": 2.5,
      "unit": "KG"
    }
  ]
}
```
## RawMaterial

Endpoints:  
POST   /raw-materials  
GET    /raw-materials  
GET    /raw-materials/{id}  
PUT    /raw-materials/{id}  
DELETE /raw-materials/{id}  

GET /raw-materials?name=&code=

**RawMaterialRequest**
```json
{
  "code": "RM-001",
  "name": "Aço",
  "unit": "KG",
  "stockQuantity": 100
}
```

**RawMaterialResponse**
```json
{
  "id": 1,
  "code": "RM-001",
  "name": "Aço",
  "unit": "KG",
  "stockQuantity": 100
}
```
## ProductMaterial
Entidade de associação entre Product e RawMaterial

Não possui CRUD próprio

É gerenciada dentro do Product

## Production Availability

GET /production

**Response**

```json
[
  {
    "productId": 1,
    "productName": "Produto A",
    "maxQuantity": 40,
    "available": true
  }
]
```