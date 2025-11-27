🚗 Gjun Rent 線上租車系統｜會員預約租車流程
<p align="center"> <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk"/> <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> <img src="https://img.shields.io/badge/JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate"/> <img src="https://img.shields.io/badge/Template-Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"/> </p>

使用 Spring Boot + Thymeleaf 打造的會員預約租車系統。
我負責前台會員 整個租車流程 Step1 → Step4 全功能開發與整合。

📚 目錄

專案簡介

技術架構

專案結構（重要）

功能流程（我負責的部分）

會員登入機制（JWT + Session）

專案啟動方式

我負責的項目總覽

📝 專案簡介

Gjun Rent 線上租車系統是一套會員制租車網站。

我負責：

✔ 完整預約租車流程 (Step1 → Step4)
✔ 前端頁面 UI 製作 (HTML / CSS / JS)
✔ 後端 Controller / Service / Repository
✔ Session 保存預約資料
✔ JWT 驗證 + 前後端登入整合
✔ 訂單寫入資料庫、成功頁面顯示

🏗 技術架構
🔧 後端

Spring Boot 3.x

Spring MVC

Spring Data JPA

MySQL

Hibernate

JWT Token 驗證

🎨 前端

Thymeleaf 模板引擎

HTML / CSS

JavaScript

jQuery

AJAX

daterangepicker (日期選擇器)

SweetAlert2 (提示訊息)

📂 專案結構（重要）
src/main/java/com.demo
│
├── controller
│   ├── PageController.java         // /reserve (Step1) 頁面 + Token 驗證
│   ├── ReserveController.java      // Step2~Step4 控制器
│   └── OrderController.java        // 我的訂單 API
│
├── model
│   ├── Car.java
│   ├── Order.java
│   ├── Member.java
│   └── ...
│
├── repository
│   ├── CarRepository.java
│   ├── OrderRepository.java
│   └── MemberRepository.java
│
├── service
│   ├── CarService.java
│   ├── OrderService.java
│   └── ...
│
└── util
    └── JwtUtil.java               // JWT 產生與解析

🖼 前端資源（templates / static）
templates/
│ reserve.html          // Step1
│ reserve-step2.html    // Step2
│ reserve-step3.html    // Step3
│ reserve-step4.html    // Step4
│ my-orders.html
│ my-order-detail.html
│ fragments/header.html

static/css/
│ reserve.css
│ reserve-step2.css
│ reserve-step3.css
│ reserve-step4.css

static/js/
│ member.js
│ order.js

🔵 功能流程（我負責的部分）
Step1 — 輸入預約資訊

✔ 取還車據點
✔ 日期範圍 DateRangePicker
✔ 自動生成時間
✔ 車款分類 (五人座 / 七九人座)
✔ 全部資料寫入 Session

session.setAttribute("pickupLocation", pickupLocation);
session.setAttribute("dateRange", dateRange);
session.setAttribute("pickupTime", pickupTime);
session.setAttribute("carType", carType);

Step2 — 選擇車款與加購

✔ 從資料庫顯示可選車款
✔ 點擊「確定選擇」 → 設定隱藏 radio
✔ 加購（保險、兒童座椅）
✔ 表單送出寫入 Session

session.setAttribute("carId", carId);
session.setAttribute("insurance", insurance);
session.setAttribute("childSeatQty", childSeatQty);

Step3 — 訂單確認頁

✔ 自動計算租期
✔ 計算總金額

long rentalDays = ChronoUnit.DAYS.between(start, end);
int totalAmount = carTotal + seatCost + insuranceCost;


✔ 回傳前端顯示確認頁內容

Step4 — 寫入資料庫並完成頁面

✔ 產生訂單編號
✔ 寫入 MySQL
✔ 顯示訂單成功頁面

order.setOrderNo("OD" + System.currentTimeMillis());
Order savedOrder = orderRepo.save(order);
session.setAttribute("latestOrder", savedOrder);

🔐 會員登入機制（JWT + Session）
1️⃣ 前端會員登入 → 接收 Token
sessionStorage.setItem("jwtToken", data.token);

2️⃣ 會員進入 /reserve 時附帶 Token
/reserve?token=xxxxx

3️⃣ 後端處理

✔ 驗證 JWT
✔ 解析身分證字號
✔ 查詢會員資料
✔ 寫入 Session → loginUserId

🚀 專案啟動方式

建立 MySQL 資料庫

修改 application.properties

使用 Spring Boot 啟動後端

VS Code 使用 Live Server 執行前端 (127.0.0.1:5500)

開始預約租車流程

⭐ 我負責的項目總覽

預約租車 Step1 ~ Step4 (全部功能)

日期選擇器 + 時間自動生成

車款篩選與金額計算

Session 跨頁資料保存

JWT 驗證與會員登入整合

訂單資料寫入 MySQL

成功頁面設計與顯示

我的訂單 API 與前端串接
