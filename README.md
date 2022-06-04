# Fridge Manager

A simple back-end REST API application made while I was learning Spring Framework. 
It helps you to manage the contents of your fridge and also checks which food expired.

## Configuration

If you want to configure the application, go to `docker-compose.yml` and change environment variables you want.

| Variable | Description |
| -------- | ----------- |
| `DB_URL`   | URL of the database |
| `DB_USERNAME`   | database username (should be compatible with POSTGRES_USER) |
| `DB_PASSWORD`   | database user's password (should be compatible with POSTGRES_PASSWORD) |
| `HIBERNATE_DDL_AUTO`   | database initialization behavior [possible: none, validate, update, create-drop] |
| `JWT_SECRET`   | your password hashing custom secret key |
| `POSTGRES_USER`   | database username |
| `POSTGRES_PASSWORD`   | database password |

## Building

```
./mvnw package
cp target/fridgemanager-0.0.1-SNAPSHOT.jar build
```

## Running

```
docker-compose up
```

## Endpoints

### Authentication

---

### `POST /api/v1/auth/login`

**Request**
```json
{
  "username": "username",
  "password": "password"
}
```

**Response**

Returns a JWT token.

### `POST /api/v1/auth/register`

**Request**
```json
{
  "username": "username",
  "password": "password"
}
```

**Response**

Returns a created account's `id` and `username`.

### Food

---

### `GET /api/v1/foods`

**Response**

Returns a list of all the food.

### `GET /api/v1/foods/{id}`

**Response**

Returns food with the specific `id`.

### `GET /api/v1/foods/expired`

**Response**

Returns a list of expired food.

### `POST /api/v1/foods`

**Request**
```json
{
  "name": "name",
  "expirationDate": "yyyy-MM-dd"
}
```

**Response**

Returns a created food.

### `PUT /api/v1/foods/{id}`

**Request**
```json
{
  "name": "name",
  "expirationDate": "yyyy-MM-dd"
}
```

**Response**

Returns updated food with the specific `id`.

### `DELETE /api/v1/foods/{id}`

**Response**

Returns `ok` status.