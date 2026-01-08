# Task API

A comprehensive REST API for managing tasks and projects with user authentication, role-based access control, and extensive features for task management.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Testing Guide](#testing-guide)
- [Security Details](#security-details)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [License](#license)

## Features

### Core Functionality
- ✅ **Task Management** - Create, read, update, and delete tasks
- ✅ **Project Organization** - Organize tasks into projects
- ✅ **User Authentication** - Secure user registration and login with JWT tokens
- ✅ **Role-Based Access Control (RBAC)** - Fine-grained permissions management
- ✅ **Task Status Tracking** - Track task progress with customizable statuses
- ✅ **Priority Levels** - Set task priorities (Low, Medium, High, Critical)
- ✅ **Due Dates** - Assign and track task deadlines
- ✅ **Task Assignment** - Assign tasks to team members
- ✅ **Collaborative Features** - Team collaboration and task delegation
- ✅ **Activity Logging** - Track all user actions and changes
- ✅ **Error Handling** - Comprehensive error handling with meaningful error messages

### Advanced Features
- 📊 **Task Filtering & Sorting** - Filter tasks by status, priority, assignee, and date range
- 🔔 **Notifications** - Task update notifications (future enhancement)
- 📱 **RESTful API** - Clean and intuitive REST API design
- 🔐 **Data Validation** - Input validation on all endpoints
- 📝 **Audit Trails** - Complete audit trail of all changes

## Tech Stack

### Backend
- **Node.js** - JavaScript runtime environment
- **Express.js** - Web framework for building REST APIs
- **MongoDB** - NoSQL database for data persistence
- **Mongoose** - ODM (Object Data Modeling) library for MongoDB
- **JWT (jsonwebtoken)** - Token-based authentication
- **bcryptjs** - Password hashing and security

### Development Tools
- **dotenv** - Environment variable management
- **Nodemon** - Development server auto-reload
- **Jest** - Testing framework
- **Supertest** - HTTP assertion library for testing

### Deployment
- **Docker** - Containerization (optional)
- **PM2** - Process manager for production

## Project Structure

```
Task-api/
├── src/
│   ├── models/
│   │   ├── User.js                 # User schema and model
│   │   ├── Task.js                 # Task schema and model
│   │   ├── Project.js              # Project schema and model
│   │   └── ActivityLog.js           # Activity logging model
│   ├── routes/
│   │   ├── auth.js                 # Authentication routes
│   │   ├── tasks.js                # Task management routes
│   │   ├── projects.js             # Project management routes
│   │   └── users.js                # User management routes
│   ├── controllers/
│   │   ├── authController.js       # Authentication logic
│   │   ├── taskController.js       # Task management logic
│   │   ├── projectController.js    # Project management logic
│   │   └── userController.js       # User management logic
│   ├── middleware/
│   │   ├── auth.js                 # JWT authentication middleware
│   │   ├── authorization.js        # Role-based access control
│   │   ├── errorHandler.js         # Global error handling
│   │   └── validation.js           # Input validation middleware
│   ├── utils/
│   │   ├── logger.js               # Logging utilities
│   │   ├── validators.js           # Validation helper functions
│   │   └── constants.js            # Application constants
│   ├── config/
│   │   ├── database.js             # MongoDB connection
│   │   └── env.js                  # Environment configuration
│   └── app.js                      # Express application setup
├── tests/
│   ├── auth.test.js                # Authentication tests
│   ├── tasks.test.js               # Task management tests
│   ├── projects.test.js            # Project management tests
│   └── setup.js                    # Test setup and utilities
├── .env.example                    # Example environment variables
├── .gitignore                      # Git ignore rules
├── package.json                    # Project dependencies
├── package-lock.json               # Dependency lock file
├── jest.config.js                  # Jest configuration
├── docker-compose.yml              # Docker compose (optional)
├── Dockerfile                      # Docker configuration (optional)
└── README.md                       # This file
```

## Setup Instructions

### Prerequisites
- Node.js (v14.0.0 or higher)
- npm or yarn package manager
- MongoDB (local or cloud instance like MongoDB Atlas)
- Git

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Shavez90/Task-api.git
   cd Task-api
   ```

2. **Install Dependencies**
   ```bash
   npm install
   ```

3. **Environment Configuration**
   - Copy the `.env.example` file to `.env`
   ```bash
   cp .env.example .env
   ```
   - Update the `.env` file with your configuration:
   ```env
   # Server Configuration
   PORT=5000
   NODE_ENV=development

   # Database Configuration
   MONGODB_URI=mongodb://localhost:27017/task-api
   # For MongoDB Atlas: mongodb+srv://username:password@cluster.mongodb.net/task-api

   # JWT Configuration
   JWT_SECRET=your_super_secret_jwt_key_here
   JWT_EXPIRY=7d

   # CORS Configuration
   CORS_ORIGIN=http://localhost:3000

   # Logging
   LOG_LEVEL=debug
   ```

4. **Setup MongoDB**
   - **Local MongoDB**: Ensure MongoDB is running on your machine
   ```bash
   mongod
   ```
   - **MongoDB Atlas** (Cloud): 
     - Create an account at https://www.mongodb.com/cloud/atlas
     - Create a cluster and get your connection string
     - Update `MONGODB_URI` in `.env`

5. **Start the Development Server**
   ```bash
   npm run dev
   ```
   The API will be available at `http://localhost:5000`

6. **Verify Installation**
   ```bash
   curl http://localhost:5000/api/health
   ```

### Running with Docker

```bash
docker-compose up -d
```

## API Endpoints

### Authentication Endpoints

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe"
}

Response (201):
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "id": "user_id",
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "user"
  }
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123"
}

Response (200):
{
  "success": true,
  "message": "Login successful",
  "data": {
    "user": {
      "id": "user_id",
      "email": "user@example.com",
      "role": "user"
    },
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

#### Refresh Token
```http
POST /api/auth/refresh
Headers:
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "data": {
    "token": "new_jwt_token"
  }
}
```

#### Logout
```http
POST /api/auth/logout
Headers:
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Logout successful"
}
```

### Task Endpoints

#### Get All Tasks
```http
GET /api/tasks?status=active&priority=high&assignee=user_id&page=1&limit=10
Headers:
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "data": {
    "tasks": [...],
    "pagination": {
      "total": 50,
      "page": 1,
      "limit": 10,
      "pages": 5
    }
  }
}
```

#### Get Task by ID
```http
GET /api/tasks/:taskId
Headers:
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "data": {
    "id": "task_id",
    "title": "Complete project documentation",
    "description": "Write comprehensive documentation",
    "status": "in_progress",
    "priority": "high",
    "dueDate": "2026-01-20T00:00:00Z",
    "assignee": "user_id",
    "project": "project_id",
    "createdAt": "2026-01-08T06:11:45Z",
    "updatedAt": "2026-01-08T06:11:45Z"
  }
}
```

#### Create Task
```http
POST /api/tasks
Headers:
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Complete project documentation",
  "description": "Write comprehensive documentation",
  "priority": "high",
  "status": "pending",
  "dueDate": "2026-01-20",
  "assignee": "user_id",
  "project": "project_id"
}

Response (201):
{
  "success": true,
  "message": "Task created successfully",
  "data": { ... }
}
```

#### Update Task
```http
PUT /api/tasks/:taskId
Headers:
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Updated title",
  "status": "completed",
  "priority": "medium"
}

Response (200):
{
  "success": true,
  "message": "Task updated successfully",
  "data": { ... }
}
```

#### Delete Task
```http
DELETE /api/tasks/:taskId
Headers:
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Task deleted successfully"
}
```

### Project Endpoints

#### Get All Projects
```http
GET /api/projects?page=1&limit=10
Headers:
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "data": {
    "projects": [...],
    "pagination": { ... }
  }
}
```

#### Create Project
```http
POST /api/projects
Headers:
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Q1 Development",
  "description": "First quarter development tasks",
  "dueDate": "2026-03-31"
}

Response (201):
{
  "success": true,
  "message": "Project created successfully",
  "data": { ... }
}
```

#### Update Project
```http
PUT /api/projects/:projectId
Headers:
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Updated Project Name",
  "description": "Updated description"
}

Response (200):
{
  "success": true,
  "message": "Project updated successfully",
  "data": { ... }
}
```

#### Delete Project
```http
DELETE /api/projects/:projectId
Headers:
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "message": "Project deleted successfully"
}
```

### User Endpoints

#### Get User Profile
```http
GET /api/users/profile
Headers:
Authorization: Bearer <token>

Response (200):
{
  "success": true,
  "data": {
    "id": "user_id",
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "user",
    "createdAt": "2026-01-08T06:11:45Z"
  }
}
```

#### Update User Profile
```http
PUT /api/users/profile
Headers:
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Smith"
}

Response (200):
{
  "success": true,
  "message": "Profile updated successfully",
  "data": { ... }
}
```

#### Change Password
```http
POST /api/users/change-password
Headers:
Authorization: Bearer <token>
Content-Type: application/json

{
  "currentPassword": "oldPassword123",
  "newPassword": "newPassword123"
}

Response (200):
{
  "success": true,
  "message": "Password changed successfully"
}
```

## Testing Guide

### Running Tests

#### Run All Tests
```bash
npm test
```

#### Run Tests in Watch Mode
```bash
npm run test:watch
```

#### Run Tests with Coverage
```bash
npm run test:coverage
```

#### Run Specific Test Suite
```bash
npm test -- auth.test.js
npm test -- tasks.test.js
```

### Test Structure

Tests are organized by feature in the `tests/` directory:

- **auth.test.js** - Tests for user authentication (registration, login, logout)
- **tasks.test.js** - Tests for task CRUD operations and filtering
- **projects.test.js** - Tests for project management
- **setup.js** - Test utilities and database setup/teardown

### Writing Tests

Example test structure:
```javascript
describe('Task API', () => {
  beforeAll(async () => {
    // Setup test database
  });

  afterAll(async () => {
    // Cleanup test database
  });

  describe('POST /api/tasks', () => {
    it('should create a new task', async () => {
      const res = await request(app)
        .post('/api/tasks')
        .set('Authorization', `Bearer ${token}`)
        .send({
          title: 'Test Task',
          priority: 'high'
        });

      expect(res.statusCode).toBe(201);
      expect(res.body.data.title).toBe('Test Task');
    });
  });
});
```

### Test Coverage Goals
- ✅ Unit tests for utility functions
- ✅ Integration tests for API endpoints
- ✅ Authentication and authorization tests
- ✅ Error handling tests
- ✅ Data validation tests
- **Target Coverage**: >80%

## Security Details

### Authentication & Authorization

#### JWT Implementation
- **Token Type**: JSON Web Tokens (JWT)
- **Algorithm**: HS256 (HMAC with SHA-256)
- **Expiration**: 7 days (configurable)
- **Refresh Token**: Implement refresh token rotation
- **Storage**: Tokens are validated on each request via the `Authorization` header

#### Password Security
```javascript
// Passwords are hashed using bcryptjs with salt rounds
const saltRounds = 10;
const hashedPassword = await bcrypt.hash(password, saltRounds);
```

#### Role-Based Access Control (RBAC)
```javascript
// Roles: admin, project_manager, user
// Middleware enforces role-based access:
- Admin: Full access to all resources
- Project Manager: Can create projects and assign tasks
- User: Can view/edit own tasks
```

### API Security Best Practices

#### Input Validation
- All inputs are validated using a validation middleware
- SQL injection prevention through MongoDB parameterized queries
- XSS protection via input sanitization

#### CORS Configuration
```javascript
// Only allows requests from configured origins
const corsOptions = {
  origin: process.env.CORS_ORIGIN,
  credentials: true,
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH']
};
```

#### Rate Limiting
```javascript
// Implement rate limiting to prevent abuse
const rateLimit = require('express-rate-limit');
const limiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minutes
  max: 100 // limit each IP to 100 requests per windowMs
});
app.use('/api/', limiter);
```

#### HTTPS
- Always use HTTPS in production
- Set secure cookies with `httpOnly` and `secure` flags
- Implement HSTS (HTTP Strict Transport Security)

#### Environment Variables
- Never commit `.env` file
- Use `.env.example` as template
- Rotate secrets regularly
- Use strong, unique JWT secrets

### Data Protection

#### Encryption
- Sensitive data (passwords) are hashed with bcryptjs
- Consider encrypting sensitive fields in MongoDB

#### Audit Logging
- All changes are logged to `ActivityLog` collection
- Includes user ID, action, timestamp, and affected resources
- Enables security audits and compliance tracking

### Common Vulnerabilities Prevention

| Vulnerability | Prevention Method |
|---------------|-------------------|
| SQL Injection | MongoDB parameterized queries |
| XSS | Input sanitization, output encoding |
| CSRF | CSRF tokens on state-changing operations |
| Weak Auth | JWT with strong secrets, bcrypt hashing |
| Exposed Secrets | Environment variables, .env in .gitignore |
| Rate Limiting | Express rate limit middleware |
| CORS Issues | Configured CORS whitelist |

## Future Enhancements

### Planned Features

#### Phase 1: Enhanced Task Management
- [ ] **Task Templates** - Pre-defined task templates for common workflows
- [ ] **Task Dependencies** - Set task dependencies and manage task order
- [ ] **Time Tracking** - Track time spent on tasks and generate reports
- [ ] **Task Attachments** - Allow file uploads to tasks
- [ ] **Task Comments** - Team discussion on tasks with threading

#### Phase 2: Collaboration & Notifications
- [ ] **Team Management** - Create teams and manage team members
- [ ] **Real-time Notifications** - WebSocket-based real-time updates
- [ ] **Email Notifications** - Email alerts for task changes and deadlines
- [ ] **Mentions** - @mention team members in task descriptions
- [ ] **Activity Feed** - Centralized activity feed for all projects

#### Phase 3: Advanced Features
- [ ] **Gantt Charts** - Visual timeline for project tracking
- [ ] **Kanban Boards** - Drag-and-drop task management
- [ ] **Dashboard** - Analytics and insights dashboard
- [ ] **Custom Fields** - Allow custom task attributes
- [ ] **Recurring Tasks** - Create recurring task schedules
- [ ] **Task Templates** - Save and reuse task configurations

#### Phase 4: Integration & Automation
- [ ] **Third-party Integrations** - Slack, GitHub, Jira integration
- [ ] **Webhooks** - Custom webhook events
- [ ] **API Automation** - Zapier/IFTTT integration
- [ ] **Export Features** - CSV, PDF export capabilities
- [ ] **Mobile App** - Native iOS/Android applications

#### Phase 5: Enterprise Features
- [ ] **SSO Integration** - Single Sign-On (OAuth2, SAML)
- [ ] **Advanced Permissions** - Fine-grained permission control
- [ ] **Data Encryption** - At-rest and in-transit encryption
- [ ] **Compliance** - GDPR, SOC2 compliance features
- [ ] **Audit Trails** - Detailed audit logging and reporting

### Performance Optimization
- [ ] Database indexing for frequently queried fields
- [ ] Implement caching (Redis) for frequently accessed data
- [ ] API response pagination optimization
- [ ] Database query optimization

### DevOps & Infrastructure
- [ ] Kubernetes deployment configuration
- [ ] CI/CD pipeline setup (GitHub Actions)
- [ ] Automated testing in CI/CD
- [ ] Docker image optimization
- [ ] Load balancing configuration

## Contributing

We welcome contributions! Please follow these steps:

1. **Fork the Repository**
   ```bash
   git clone https://github.com/Shavez90/Task-api.git
   ```

2. **Create a Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make Your Changes**
   - Follow the existing code style
   - Add tests for new features
   - Update documentation as needed

4. **Commit Your Changes**
   ```bash
   git commit -m "feat: Add your feature description"
   ```

5. **Push to Your Branch**
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request**
   - Provide a clear description of changes
   - Reference related issues
   - Ensure all tests pass

### Code Style Guidelines
- Use ES6+ syntax
- Follow ESLint configuration
- Add JSDoc comments for functions
- Keep functions small and focused
- Use meaningful variable names

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, please:
- Open an issue on GitHub
- Check existing documentation
- Review the FAQ section

## Contact

For inquiries, reach out to:
- **Author**: Shavez90
- **GitHub**: https://github.com/Shavez90/Task-api
- **Email**: Contact through GitHub

---

**Last Updated**: January 8, 2026

**Version**: 1.0.0

Built with ❤️ by Shavez90
