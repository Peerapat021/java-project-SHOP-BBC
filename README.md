# 🛒 SHOP-BBC (Bicycle Shop Management System)

ระบบจัดการร้านขายจักรยาน (Bicycle Shop Management System) ที่พัฒนาด้วย Java Spring Boot และ MySQL เพื่อช่วยในการบริหารจัดการสินค้า การขาย และข้อมูลภายในร้านค้าอย่างมีประสิทธิภาพ

---

## 📌 Overview

โปรเจกต์นี้ถูกพัฒนาขึ้นเพื่อจำลองระบบร้านค้าจริง โดยเน้นการจัดการสินค้า (Product Management) และกระบวนการขาย (Sales Management) ผ่าน RESTful API

ระบบถูกออกแบบให้สามารถขยายต่อได้ เช่น การเพิ่มระบบผู้ใช้งาน (Authentication), Dashboard และ Frontend ในอนาคต

---

## 🚀 Features

### 📦 Product Management

* เพิ่มสินค้า (Create Product)
* แก้ไขสินค้า (Update Product)
* ลบสินค้า (Delete Product)
* ดูรายการสินค้า (Get All Products)
* ค้นหาสินค้า

### 🛍️ Sales Management

* บันทึกการขายสินค้า
* คำนวณยอดรวม
* จัดการรายการสินค้าในใบขาย

### 🧾 Invoice System

* ออกใบเสร็จ
* แสดงรายละเอียดการขาย

### 📊 Data Management

* จัดเก็บข้อมูลใน MySQL
* รองรับการขยายระบบในอนาคต

---

## 🛠️ Tech Stack

| Category   | Technology        |
| ---------- | ----------------- |
| Backend    | Java, Spring Boot |
| Database   | MySQL             |
| ORM        | Spring Data JPA   |
| Build Tool | Maven / Gradle    |
| API        | RESTful API       |
| Tools      | VS Code, Postman  |

---

## ⚙️ Installation & Setup

### 1️⃣ Clone Repository

```bash
git clone https://github.com/yourusername/java-project-SHOP-BBC.git
cd java-project-SHOP-BBC
```

---

### 2️⃣ Setup Database (MySQL)

สร้างฐานข้อมูล:

```sql
CREATE DATABASE shop_bbc;
```

ตั้งค่าใน `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shop_bbc
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

### 3️⃣ Run Application

#### Maven

```bash
./mvnw spring-boot:run
```

#### Gradle

```bash
./gradlew bootRun
```

---

## 🔗 API Endpoints

### 📦 Product APIs

| Method | Endpoint           | Description       |
| ------ | ------------------ | ----------------- |
| GET    | /api/products      | Get all products  |
| GET    | /api/products/{id} | Get product by ID |
| POST   | /api/products      | Create product    |
| PUT    | /api/products/{id} | Update product    |
| DELETE | /api/products/{id} | Delete product    |

---

### 🛍️ Sales APIs

| Method | Endpoint   | Description   |
| ------ | ---------- | ------------- |
| POST   | /api/sales | Create sale   |
| GET    | /api/sales | Get all sales |

---

## 🧪 Testing

สามารถทดสอบ API ได้ผ่าน:

* Postman
* Thunder Client (VS Code)

---

## 📷 Screenshots

> (เพิ่มรูปหน้าระบบ / API response จะช่วยเพิ่มความน่าสนใจ)

---

## 👨‍💻 My Responsibilities

* ออกแบบโครงสร้างระบบ (MVC Architecture)
* พัฒนา RESTful API ด้วย Spring Boot
* ออกแบบฐานข้อมูล MySQL
* ใช้ Spring Data JPA ในการจัดการข้อมูล
* ทดสอบ API และแก้ไขข้อผิดพลาด
* วางโครงสร้างโปรเจกต์ให้สามารถขยายต่อได้

---

## 🔮 Future Improvements

* 🔐 เพิ่มระบบ Authentication (JWT / OAuth)
* 📊 Dashboard สำหรับแสดงข้อมูล
* 🌐 เชื่อมต่อ Frontend (React / Next.js)
* 📦 เพิ่มระบบจัดการสต็อกแบบ Real-time

---

## 📬 Contact

* GitHub: https://github.com/yourusername

---

## ⭐ Notes

โปรเจกต์นี้ถูกพัฒนาเพื่อการเรียนรู้และพัฒนาทักษะด้าน Backend Development โดยเน้นการใช้งาน Spring Boot และการออกแบบ REST API
