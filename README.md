# SkillBridge — Skill Swap Platform

A full-stack web app where students can list skills they can **teach** and skills they want to **learn**, search for other students, and send/accept **skill swap requests**.

- **Frontend:** React (Vite) + React Router + Axios
- **Backend:** Spring Boot (Java) + Spring Data JPA
- **Database:** MySQL

---

## 1. Folder Structure

```
skillbridge/
├── backend/            → Spring Boot project (Maven)
│   └── src/main/java/com/skillbridge/
│       ├── entity/      → User, Skill, SwapRequest (database tables)
│       ├── dto/         → Data shapes for requests/responses
│       ├── repository/  → Spring Data JPA interfaces (auto DB queries)
│       ├── service/     → Business logic
│       ├── controller/  → REST API endpoints
│       ├── config/      → CORS configuration
│       └── exception/   → Custom errors + global error handler
├── frontend/            → React project (Vite)
│   └── src/
│       ├── components/  → Reusable UI pieces (Navbar, Footer, cards, loader)
│       ├── pages/        → One file per page/route
│       ├── services/     → Axios calls to the backend, grouped by feature
│       └── styles/       → Plain CSS files, one per component/page
└── database/
    ├── schema.sql        → Table definitions (for reference/manual setup)
    └── sample_data.sql   → Optional sample users/skills/requests
```

---

## 2. Prerequisites

Install these first:

- **Java 17+** — `java -version`
- **Maven** (or use the `mvnw` wrapper if you add one) — `mvn -version`
- **Node.js 18+** and npm — `node -v`
- **MySQL 8+** running locally — `mysql --version`

---

## 3. Backend Setup (Spring Boot)

1. Open `backend/src/main/resources/application.properties` and update the MySQL username/password to match your local setup:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=root
   ```
   You do **not** need to manually create the database — `createDatabaseIfNotExist=true` in the connection URL handles that, and `spring.jpa.hibernate.ddl-auto=update` auto-creates the tables on first run.

2. From the `backend` folder, run:
   ```bash
   cd backend
   mvn spring-boot:run
   ```
   The API will start at **http://localhost:8080**.

3. (Optional) Load sample data — after the backend has run once (so tables exist), run:
   ```bash
   mysql -u root -p skillbridge_db < ../database/sample_data.sql
   ```
   Sample login: `hema@example.com` / `password123`

---

## 4. Frontend Setup (React + Vite)

1. From the `frontend` folder, install dependencies:
   ```bash
   cd frontend
   npm install
   ```

2. Start the dev server:
   ```bash
   npm run dev
   ```
   The app will open at **http://localhost:5173**.

3. Make sure the backend (step 3) is already running — the frontend calls `http://localhost:8080/api` (see `src/services/api.js`).

---

## 5. How to Use the App

1. Go to **http://localhost:5173** → click **Register** and create an account.
2. **Login** with your new account.
3. On your **Dashboard**, click **+ Add a Skill** to list skills you can teach or want to learn.
4. Go to **Search Skills** to find other students by skill name, college, or department.
5. Click **Send Swap Request** on a matching student.
6. Go to **Swap Requests** to accept/reject requests you've received, or track requests you've sent.

---

## 6. REST API Reference

| Method | Endpoint                     | Description                    |
|--------|-------------------------------|---------------------------------|
| POST   | `/api/auth/register`          | Register a new user             |
| POST   | `/api/auth/login`              | Login                           |
| GET    | `/api/users`                   | List all users                  |
| GET    | `/api/users/{id}`               | Get one user's profile          |
| PUT    | `/api/users/{id}`               | Update a profile                |
| GET    | `/api/users/search`             | Search by skill/college/dept    |
| GET    | `/api/skills?userId=1`          | Get skills (optionally by user) |
| POST   | `/api/skills`                   | Add a skill                     |
| PUT    | `/api/skills/{id}`               | Edit a skill                    |
| DELETE | `/api/skills/{id}`               | Delete a skill                  |
| POST   | `/api/requests`                  | Send a swap request             |
| GET    | `/api/requests?sentBy=1`         | Requests sent by a user         |
| GET    | `/api/requests?receivedBy=1`     | Requests received by a user     |
| PUT    | `/api/requests/{id}/accept`      | Accept a request                |
| PUT    | `/api/requests/{id}/reject`      | Reject a request                |

---

## 7. Why Each Major File Exists

**Backend**
- `SkillBridgeApplication.java` — the entry point that boots the Spring app.
- `entity/*.java` — map directly to MySQL tables via JPA annotations (`@Entity`, `@Id`, etc). Hibernate uses these to auto-generate the schema.
- `dto/*.java` — separate "shape" classes for data coming in from / going out to the frontend, so we never accidentally expose the password field or require the client to send more than necessary.
- `repository/*.java` — just interfaces; Spring Data JPA auto-implements basic CRUD + our custom finder methods (like `findByEmail`).
- `service/*.java` — where the actual business rules live (e.g. "you can't swap-request yourself", "email must be unique").
- `controller/*.java` — thin layer that just maps HTTP requests to service calls.
- `config/CorsConfig.java` — without this, the browser would block the React app (port 5173) from calling the API (port 8080).
- `exception/GlobalExceptionHandler.java` — catches errors anywhere in the app and turns them into clean JSON instead of stack traces.

**Frontend**
- `main.jsx` — mounts the React app and wraps it in `BrowserRouter` for routing.
- `App.jsx` — defines every page route, and a `ProtectedRoute` wrapper that redirects to `/login` if nobody is logged in.
- `services/api.js` — one shared Axios instance so the base URL only needs to be set in one place.
- `services/*Service.js` — one file per feature (auth, users, skills, requests) so components never call Axios directly.
- `pages/*.jsx` — one component per route/page, matching the Pages list in the requirements.
- `components/*.jsx` — small reusable pieces (Navbar, Footer, SkillCard, RequestCard, Loader) used across multiple pages.
- `styles/*.css` — plain CSS, one file per component/page, using only Flexbox/Grid (no Tailwind or Material UI).

---

## 8. Notes & Limitations (v1)

- **No JWT / Spring Security yet** — as requested, login is a simple email+password check. Passwords are stored in plain text for simplicity; this is **not** production-safe and should be replaced with hashed passwords (e.g. BCrypt) before any real deployment.
- Logged-in state on the frontend is kept in `localStorage` (not cookies/JWT), which is fine for a learning project but should be upgraded later.
- Search is done in-memory on the backend for simplicity; for a larger dataset you'd want proper SQL `WHERE`/`JOIN` queries or a search index.
