🚗 Gjun Rent 線上租車系統 | 前台預約與下單流程
<p align="center"> <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge"> <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge"> <img src="https://img.shields.io/badge/MySQL-8.0-orange?style=for-the-badge"> <img src="https://img.shields.io/badge/JPA-Hibernate-red?style=for-the-badge"> <img src="https://img.shields.io/badge/Template-Thymeleaf-009900?style=for-the-badge"> </p> <p align="center"> <b>使用 Spring Boot + Thymeleaf 打造的線上租車預約系統。<br> 我負責「前台會員預約流程 Step1~Step4」全功能實作。</b> </p>
📑 目錄

📌 專案簡介

🏗️ 技術架構

📂 專案結構

🚦 功能流程（我負責的部分）

Step1 — 填寫預約資訊

Step2 — 選擇車款與加購

Step3 — 訂單確認

Step4 — 寫入資料庫並完成

🔐 會員登入機制（JWT + Session）

🛠️ 專案啟動方式

🙋 我負責的項目（面試可直接講）

📌 專案簡介

Gjun Rent 線上租車系統是一套會員制租車服務網站。
我負責：

前台預約租車 Step1～Step4 全流程

前端 UI + 後端 Controller / Service / Session 整合

會員登入 Token 驗證（JWT → HttpSession）

訂單寫入資料庫與完成頁面顯示

此流程完整涵蓋使用者由輸入資料 → 選車 → 確認 → 下單成功。

🏗️ 技術架構
後端
技術	說明
Spring Boot 3.x	MVC + DI + JPA 基礎架構
Spring MVC	Controller & Routing
Spring Data JPA	資料存取（Hibernate）
MySQL	資料庫
JWT	登入驗證，讓後端辨識會員
Thymeleaf	動態頁面模板
前端
技術	說明
HTML / CSS / JavaScript	頁面實作
jQuery	DOM 操作、AJAX
SweetAlert2	提示訊息
Moment.js	日期處理
Daterangepicker	取還車日期區間選擇
📂 專案結構（主要部分）
src/main/java/com.demo
 ├── controller
 │    ├── PageController.java          // /reserve 驗證 Token → 開啟 Session
 │    ├── ReserveController.java       // Step1~Step4 租車流程核心
 │
 ├── model
 │    ├── Car.java                     // 車輛資料
 │    ├── Order.java                   // 訂單資料
 │    ├── Member.java                  // 會員資料
 │
 ├── repository
 │    ├── CarRepository.java
 │    ├── OrderRepository.java
 │    ├── MemberRepository.java
 │
 ├── service
 │    ├── CarService.java              // 車款查詢、計算租期
 │    ├── BranchService.java           // 分店資料
 │
resources/
 ├── templates
 │    ├── reserve.html                 // Step1
 │    ├── reserve-step2.html           // Step2
 │    ├── reserve-step3.html           // Step3
 │    ├── reserve-step4.html           // Step4
 │    └── fragments/header.html
 │
 ├── static/css                        // 各 step CSS
 ├── static/images
 ├── static/js

🚦 功能流程（我負責的部分）
🟦 Step1 — 填寫預約資訊

使用者輸入：

取 / 還車據點

取還車日期（Daterangepicker）

自動生成之時間選單（00:00 ~ 23:30）

車款（五人座、七九人座）

資料存入 Session：

session.setAttribute("pickupLocation", pickupLocation);
session.setAttribute("dateRange", dateRange);
session.setAttribute("pickupTime", pickupTime);
session.setAttribute("carType", carType);

🟩 Step2 — 選擇車款與加購

後端依照車款查詢：

List<Car> cars = carService.findCarsByType(carType);


前端顯示：

車款規格、租金、總金額

隱藏 radio → 點按鈕選擇車輛

加購項目（保險、兒童座椅）

送出後：

session.setAttribute("carId", carId);
session.setAttribute("insurance", insurance);
session.setAttribute("childSeatQty", childSeatQty);

🟧 Step3 — 訂單確認頁

從 Session 讀取全部資料：

車輛資訊

取還車日期/時間

加購項目

計算金額

計算租期：

long rentalDays = ChronoUnit.DAYS.between(start, end);


計算總金額：

int totalAmount = carTotal + seatCost + insuranceCost;


顯示最終畫面讓使用者確認。

🟥 Step4 — 寫入資料庫，顯示成功頁

產生訂單編號：

order.setOrderNo("OD" + System.currentTimeMillis());


寫入資料庫：

Order savedOrder = orderRepo.save(order);
session.setAttribute("latestOrder", savedOrder);


成功頁顯示：

訂單資訊

車輛資訊

加購明細

總金額

按鈕：回首頁 / 查看我的訂單

🔐 會員登入機制（JWT → 後端 Session）

1️⃣ 前端登入 → 儲存 Token

sessionStorage.setItem("jwtToken", data.token);


2️⃣ 使用 Token 打開預約頁

/reserve?token=xxxxx


3️⃣ 後端：

驗證 JWT

取出身分證字號

查詢會員

寫入 HttpSession（loginUserId）

4️⃣ 租車流程中後端都可辨識使用者身份。

🛠️ 專案啟動方式

匯入為 Maven 專案

建立 MySQL 資料庫

修改 application.properties

啟動 Spring Boot 主程式

前端使用 VS Code Live Server 執行首頁（127.0.0.1:5500）

開始預約租車流程

🙋 我負責的項目

🟨 前台預約流程 Step1~Step4 全部實作
🟨 日期選擇器、時間自動生成
🟨 車款查詢 + 加購項目計算
🟨 Session 儲存跨頁資料
🟨 JWT 驗證 → 後端 Session 建立
🟨 訂單資料寫入 MySQL
🟨 成功頁面呈現與導頁
