# API Contract — Autoflex Inventory API

## Auth

### Login
POST /auth/login

**Request**
```json
{
  "email": "admin@admin.com",
  "password": "123456"
}
```
**Response**

```json
{
  "token": "jwt",
  "user": {
    "id": 1,
    "name": "admin",
    "email": "admin@admin.com",
    "role": "ADMIN"
  }
}
```

## Product

Endpoints:  
POST   /products  
GET    /products  
GET    /products/{id}  
PUT    /products/{id}  
PUT    /products/{id}/materials
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
**ProductMaterialsRequest**
```json
[
  { "rawMaterialId": 1, "quantity": 2.5 },
  { "rawMaterialId": 3, "quantity": 1.0 }
]
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

GET /production/capacity  
“Não considera conflito/competição de matérias-primas entre produtos.”

**ProductMaterialResponse**
````json
[
  {
    "productId": 1,
    "productCode": "P-001",
    "productName": "Product A",
    "unitPrice": 50.0,
    "maxQuantity": 16,
    "totalValue": 800.0,
    "materials": [
      {
        "rawMaterialId": 10,
        "rawMaterialCode": "RM-010",
        "rawMaterialName": "Steel",
        "unit": "KG",
        "requiredPerUnit": 2.5,
        "stockQuantity": 100
      },
      {
        "rawMaterialId": 11,
        "rawMaterialCode": "RM-011",
        "rawMaterialName": "Plastic",
        "unit": "UNIT",
        "requiredPerUnit": 3,
        "stockQuantity": 50
      }
    ]
  },
  {
    "productId": 2,
    "productCode": "P-002",
    "productName": "Product B",
    "unitPrice": 20.0,
    "maxQuantity": 0,
    "totalValue": 0.0,
    "materials": [
      {
        "rawMaterialId": 10,
        "rawMaterialCode": "RM-010",
        "rawMaterialName": "Steel",
        "unit": "KG",
        "requiredPerUnit": 1.0,
        "stockQuantity": 0
      }
    ]
  }
]


````