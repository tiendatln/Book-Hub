# 📚 BookHub

> A platform for discovering, sharing, and managing your favorite books.

---

## 🖼️ Screenshots

## 🏗️ Architecture
book_hub
├── domain
│   ├── entity
│   └── repository
│   
│
├── application
│   ├── dto
│   │   ├── request
│   │   └── response
│   ├── service
│   ├── mapping
│   └── usecase
│
├── infrastructure
│   ├── persistence
│   │    ├── jpaRepository
│   │    └── repository
│   └── security
│  
│
└── presentation
    ├── controller
    └── exception

## ✨ Features

### 👤 User

- 🔐 Register / Login
- 🔑 JWT Authentication
- 🔄 Refresh Token
- 👤 Manage profile
- 🔒 Change password

### 📚 Book Management

- ➕ Add books
- ✏️ Edit books
- 🗑️ Delete books
- 📖 View book details
- 🖼️ Upload book covers
- 🏷️ Add tags and categories

### 🔎 Discovery

- 🔍 Search books
- 🏷️ Filter by category
- 📊 Sort by popularity
- ⭐ Rating system
- ❤️ Favorite books

### 👥 Social

- 📤 Share books
- 👀 View other users' collections
- 💬 Comments

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| Backend | Java, Spring Boot |
| Security | Spring Security, JWT |
| Database | MySQL |
| ORM | Spring Data JPA, Hibernate |
| Migration | Flyway |
| API | RESTful API |
| Build Tool | Maven |
| Version Control | Git, GitHub |

## 📂 Project Structure

## 🚀 Getting Started

### Prerequisites
### Installation
### Configuration
### Running the Application

## 🔐 Authentication

## 📡 API

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/user/register` | Register user |
| POST | `/user/login` | Login |
| POST | `/user/refresh` | Refresh access token |
| POST | `/user/logout` | Logout |
| PUT | `/user/changePassword` | Logout |

### Books

| Method | Endpoint | Description |
|---|---|---|
| GET | `/book/getAll/{page}` | Get books |
| GET | `/api/books/{id}` | Get book details |
| POST | `/api/books` | Create book |
| PUT | `/api/books/{id}` | Update book |
| DELETE | `/api/books/{id}` | Delete book |

## 🗄️ Database

BookHub uses MySQL as its primary database.

Main entities:

- User
- Book
- Author
- Category
- Tag
- BookTag
- Favorite
- Comment
- RefreshToken
- Rating
- BookContent

## 🧪 Testing

## 🐳 Docker

## 👨‍💻 Development

## 📝 Roadmap

- [x] User authentication
- [x] JWT access token
- [x] Refresh token
- [ ] Book CRUD
- [ ] Category management
- [ ] Book recommendation
- [ ] Image-based book search
- [ ] Real-time notifications
- [ ] Docker deployment

## 🤝 Contributing

## 📄 License

## 👤 Author

[LE NGUYEN TIEN DAT](https://github.com/tiendatln)