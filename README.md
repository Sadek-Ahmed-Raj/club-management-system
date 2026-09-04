<div align="center">

# 🎓 University Club Management System

### Object-Oriented Analysis, UML Design & Java Implementation

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/Paradigm-OOP-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen?style=for-the-badge)

*A console-based Java application that streamlines student registration, club membership, event scheduling, and notices — built to demonstrate core Object-Oriented Programming principles through a real university scenario.*

</div>

---

## 📖 Table of Contents

- [Problem Scenario](#-problem-scenario)
- [Team](#-team)
- [Features](#-features)
- [Class Identification](#-class-identification)
- [Attributes & Methods](#️-attributes--methods)
- [OOP Concepts Used](#-oop-concepts-used)
- [OOP Relationships Used](#-oop-relationships-used)
- [Java Coding Highlights](#-java-coding-highlights)
- [Tech Stack](#️-tech-stack)
- [How to Run](#️-how-to-run)
- [Future Improvements](#-future-improvements)
- [License](#-license)

---

## 🎯 Problem Scenario

> Universities manage multiple **clubs, members, and events** that need to be tracked efficiently. This system streamlines student registrations, club details, and event schedules.

**Why OOP fits this problem:**
Real-world entities like `Student` and `Club` have clear attributes and distinct behaviors — a natural fit for object-oriented design. The system accurately records join/leave activities for each club.

---

## 👥 Team

<div align="center">

| Name | Student ID | Assigned Part |
|:---|:---:|:---|
| **Md. Sadek Ahmed Raj** | `251-15-511` | OOP Concepts & Relationships |
| **Toukir Ahmed** | `251-15-216` | Problem Definition |
| **Md Sadhin Foraji** | `251-15-586` | Class Identification, Attributes & Methods |
| **MD. Kaium Hossain Riyad** | `251-15-604` | UML Class Diagram |
| **Md. Saif Uddin Mahmud** | `251-15-689` | Java Coding Solution & Conclusion |

</div>

---

## ✨ Features

<table>
<tr>
<td width="33%" valign="top">

### 👑 Admin
- Create & manage clubs
- Assign/remove presidents
- View all students, clubs & memberships

</td>
<td width="33%" valign="top">

### 🏛️ President
- Approve/reject membership applications
- Create & delete club events
- Post & delete notices

</td>
<td width="33%" valign="top">

### 🧑‍🎓 Student
- Register & log in
- Apply for club membership
- Pay fees & view joined clubs
- View notices & events

</td>
</tr>
</table>

---

## 🧩 Class Identification

| Class | Description |
|:---|:---|
| 🏢 **Club** | Central entity — stores club name, allocated budget, and enrolled members |
| 🧑‍🎓 **Student** | University user — tracks student details and joined clubs |
| 📅 **Event** | Extracurricular event details — date, venue, hosting club |
| 🛡️ **Executive** | Leadership role with administrative controls *(extends Student)* |

---

## 🏗️ Attributes & Methods

| Class | Attributes | Methods | Responsibility |
|:---|:---|:---|:---|
| `Club` | clubId, name, budget | `addMember()` `hostEvent()` | Manages club info and member lists |
| `Student` | studentId, name, email | `joinClub()` `registerEvent()` | Represents university user and actions |
| `Event` | eventId, title, date | `addParticipant()` `displayDetails()` | Handles event scheduling and participation |
| `Executive` | designation, accessLevel | `approveBudget()` `createEvent()` | Provides administrative controls for leaders |

---

## 🧠 OOP Concepts Used

<table>
<tr>
<td width="50%" valign="top">

### 🔒 Encapsulation
- Protects data by keeping fields `private`
- Accessed through public getter/setter methods
- Used for: student info, membership fee

</td>
<td width="50%" valign="top">

### 🎭 Abstraction
- Hides internal implementation
- Shows only required functionality
- Student applies for membership — system handles the rest internally

</td>
</tr>
<tr>
<td width="50%" valign="top">

### 🧬 Inheritance
- Reuses properties and methods
- Avoids code duplication
- `Admin`, `President`, `Student` inherit from `User`

</td>
<td width="50%" valign="top">

### 🔄 Polymorphism
- Same method, different behavior
- `login()` works differently per user role via `@Override`

</td>
</tr>
</table>

---

## 🔗 OOP Relationships Used

| Relationship | Type | Example | Description |
|:---|:---:|:---|:---|
| 🔗 **Association** | Has-A | `Student` ↔ `Club` | Two independent classes interact — a student can join multiple clubs |
| 🧲 **Aggregation** | Weak Has-A | `Club` → `Event` | A club organizes multiple events; both can exist independently |
| 🧱 **Composition** | Strong Has-A | `Club` → `Notice` | If the club is deleted, its notices are deleted too |

---

## 💻 Java Coding Highlights

**Encapsulation & Constructor** — `Student` class:
```java
class Student {
    private String studentId;
    private String name;

    public Student(String id, String name) {
        this.studentId = id;
        this.name = name;
    }

    public void joinClub(Club club) {
        System.out.println(name + " joined " + club.getName());
    }
}
```

**Inheritance & Polymorphism** — `Executive` class:
```java
class Executive extends Student {
    private String designation;

    public Executive(String id, String name, String role) {
        super(id, name);
        this.designation = role;
    }

    @Override
    public void displayRole() {
        System.out.println("Executive Role: " + designation);
    }
}
```

---

## 🛠️ Tech Stack

| Component | Technology |
|:---|:---|
| Language | Java |
| Data Persistence | Java Object Serialization (`.ser` file) |
| Interface | Console-based (CLI) |

---

## ▶️ How to Run

```bash
# 1. Clone the repository
git clone https://github.com/your-username/club-management-system.git
cd club-management-system

# 2. Compile
javac ClubManagementSystem.java

# 3. Run
java ClubManagementSystem
```

---

## 🚀 Future Improvements

- [ ] Password hashing for better security
- [ ] Duplicate membership application prevention
- [ ] GUI version (JavaFX/Swing)
- [ ] Database integration (MySQL) instead of file serialization

---

## 📄 License

This project is developed for academic purposes as part of an Object-Oriented Programming course.

<div align="center">

**Made with ☕ and OOP principles**

</div>
