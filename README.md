🚗 Gjun Rent 線上租車系統
<p align="center"> <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk"/> <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> <img src="https://img.shields.io/badge/Hibernate-JPA-59666C?style=for-the-badge&logo=hibernate"/> <img src="https://img.shields.io/badge/Thymeleaf-Template-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"/> </p>

使用 Spring Boot + Thymeleaf 打造會員線上租車網站。
我負責 前台會員預約租車流程 Step1 ～ Step4 全部功能與串接。

📌 目錄

專案簡介

技術架構

專案結構

功能流程（我負責的部分）

會員登入（JWT + Session）

專案啟動方式

我負責的項目總覽

📖 專案簡介

Gjun Rent 是一套完整的 會員制線上租車系統，包含：

預約租車 (Step1~Step4)

車輛篩選與展示

加購項目計算

訂單產生並寫入資料庫

會員登入（JWT 驗證）

會員訂單查詢

我負責前台會員整個預約租車流程 + Session 與資料庫串接。

🏗 技術架構
🔧 後端

Spring Boot 3.x

Spring MVC

JWT 驗證

JPA / Hibernate

MySQL

🎨 前端

Thymeleaf 模板引擎

HTML / CSS

JavaScript

jQuery

AJAX

daterangepicker

SweetAlert2

📂 專案結構
src/main/java/com.demo
│
├── controller
│   ├── PageController.java
│   ├── ReserveController.java
│   ├── OrderController.java
│   └── MemberController.java
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
│   └── MemberService.java
│
└── util
    └── JwtUtil.java

📁 前端模板（templates）
templates/
│ reserve.html
│ reserve-step2.html
│ reserve-step3.html
│ reserve-step4.html
│ my-orders.html
│ my-order-detail.html
│ fragments/header.html

🎨 靜態資源（static）
static/css/
│ reserve.css
│ reserve-step2.css
│ reserve-step3.css
│ reserve-step4.css

static/js/
│ member.js
│ order.js

static/images/
│（車輛圖片）

🔵 功能流程（我負責的部分）
Step1 — 填寫預約資訊

取／還車據點

日期範圍 (daterangepicker)

時間自動生成 00:00–23:30

車款分類（五人座／七九人座）

全部資料寫入 Session

session.setAttribute("pickupLocation", pickupLocation);
session.setAttribute("dateRange", dateRange);
session.setAttribute("pickupTime", pickupTime);
session.setAttribute("carType", carType);

Step2 — 選擇車款 + 加購配備

顯示符合條件的車輛

點擊「確定選擇」→ radio 會被設定 checked

加購項目（保險、兒童座椅）

送出後寫入 Session

session.setAttribute("carId", carId);
session.setAttribute("insurance", insurance);
session.setAttribute("childSeatQty", childSeatQty);

Step3 — 訂單確認頁

讀取全部 Session

計算天數：

long rentalDays = ChronoUnit.DAYS.between(start, end);


計算總金額：

int totalAmount = carTotal + seatCost + insuranceCost;


顯示確認頁

Step4 — 寫入資料庫，顯示成功頁面

產生訂單編號：

order.setOrderNo("OD" + System.currentTimeMillis());


寫入 MySQL：

Order savedOrder = orderRepo.save(order);
session.setAttribute("latestOrder", savedOrder);


顯示完成頁面

🔐 會員登入（JWT + Session）
1️⃣ 前端登入 → 接收 Token
sessionStorage.setItem("jwtToken", data.token);

2️⃣ 會員帶 Token 打開 /reserve
/reserve?token=xxxxx

3️⃣ 後端驗證 Token → 查會員 → 寫入 Session
session.setAttribute("loginUserId", member.getMemberId());
session.setAttribute("loginUserName", member.getName());

🚀 專案啟動方式

建立 MySQL 資料庫

修改 application.properties

啟動 Spring Boot

前端用 VS Code Live Server 執行（127.0.0.1:5500）

開始預約租車流程

⭐ 我負責的項目總覽

預約租車 Step1 ~ Step4

前端 UI / CSS / 按鈕操作

Session 跨頁資料保存

JWT 驗證串接

訂單計算與寫入

訂單成功頁面

我的訂單列表 + 詳細頁
