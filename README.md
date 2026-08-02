# 🎓 University Club Management System

A console-based **Java** application for managing university clubs — including student registration, club membership, fee payments, events, and notices. Built using core OOP principles with role-based access for **Admin**, **President**, and **Student** users.

## ✨ Features

### 👑 Admin
- Create and manage clubs
- Assign/remove club presidents
- View all students, clubs, and memberships
- Approve or manage overall system data

### 🏛️ President
- Manage club membership applications (Approve/Reject)
- Create and delete events for their club
- Post and delete notices for club members
- Change password

### 🧑‍🎓 Student
- Register and log in
- View all available clubs
- Apply for club membership
- Pay membership fees
- View joined clubs and their status
- View notices and events (for approved club memberships only)
- Change password

## 🛠️ Tech Stack
- **Language:** Java
- **Data Persistence:** Java Object Serialization (`.ser` file)
- **Interface:** Console-based (CLI)

## 📂 Project Structure
```
ClubManagementSystem.java   # Main application file (all classes included)
system_data.ser             # Auto-generated data file (created on first run)
```

## ▶️ How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/club-management-system.git
   cd club-management-system
   ```

2. **Compile the program**
   ```bash
   javac ClubManagementSystem.java
   ```

3. **Run the program**
   ```bash
   java ClubManagementSystem
   ```

## 🗂️ Data Model Overview
| Class | Description |
|---|---|
| `User` | Abstract base class for all user types |
| `Admin` | System administrator |
| `President` | Manages a specific club |
| `Student` | Applies for and joins clubs |
| `Club` | Represents a university club |
| `Membership` | Links a student to a club with status/payment info |
| `Event` | Club-organized events |
| `Notice` | Club announcements |

## 📌 Notes
- Data is saved automatically after every major action (registration, approval, payment, etc.)
- All data is stored locally in `system_data.ser`

## 🚀 Future Improvements
- Password hashing for better security
- Duplicate membership application prevention
- GUI version (JavaFX/Swing)
- Database integration (MySQL) instead of file serialization

## 📄 License
This project is open-source and available for educational use.
