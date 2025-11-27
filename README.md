🚗 Gjun Rent 線上租車系統（前台租車流程）

🎯 我負責會員登入後的 預約租車 Step1～Step4 完整流程
🛠️ 技術亮點：Session 跨頁資料、JPA、Thymeleaf UI、JWT、動態車款載入

<p align="center"> <img src="https://img.shields.io/badge/SpringBoot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> <img src="https://img.shields.io/badge/Hibernate-JPA-59666C?style=for-the-badge&logo=hibernate&logoColor=white"/> <img src="https://img.shields.io/badge/Thymeleaf-Template-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"/> </p>
📌 專案介紹

Gjun Rent 是一套 會員制線上租車平台。

我負責前台登入後的預約流程，由四個步驟組成：

➡️ Step1：選擇據點 / 日期 / 時間 / 車款
➡️ Step2：選擇車款 + 加購
➡️ Step3：訂單確認
➡️ Step4：寫入資料庫，完成預約
🧰 使用技術
技術	用途
Spring Boot 3	MVC Controller、DI、Routing
JPA + Hibernate	車輛 / 訂單 / 會員資料存取
MySQL	資料庫
Thymeleaf	HTML 模板引擎
jQuery + Ajax	DOM 操作、登入後導向
JWT	前端登入驗證
Session	跨頁保存租車資料
📂 專案結構（簡化版）
src/main/java/com.demo
 ├── controller
 │    ├── PageController.java
 │    ├── ReserveController.java     ← Step1～Step4 主流程（我負責）
 │    └── MemberController.java
 │
 ├── model
 │    ├── Car.java
 │    ├── Branch.java
 │    └── Order.java
 │
 ├── repository
 │    ├── CarRepository.java
 │    ├── MemberRepository.java
 │    ├── OrderRepository.java
 │
 ├── service
 │    ├── CarService.java
 │    └── BranchService.java
 │
 └── util
      └── JwtUtil.java

⭐ 我負責的功能流程（Step1～Step4）
🟦 Step1 — 讀取預約資訊

內容來源：使用者填寫

取車據點

還車據點

日期區間（使用 daterangepicker）

取還車時間（00:00～23:30 自動生成）

車款類型（五人座 / 七九人座）

🔸 技術

Controller 接收資料

進行 Session.setAttribute()

資料跨 Step2/Step3/Step4 使用

🟩 Step2 — 車款選擇 + 加購

根據 Step1 選的座位數，從資料庫撈出車輛：

private static final Map<String, List<Integer>> CAR_TYPE_MAP = Map.of(
    "五人座", List.of(5),
    "七九人座", List.of(7, 8, 9)
);


用戶可：

查看車款卡片（圖片、每日租金、排氣量）

選擇加購（保險 / 兒童安全座椅）

選擇後寫回 Session。

🟨 Step3 — 訂單確認頁

從 Session 讀取資料：

車款資訊

日期 / 時間

加購項目

計算總金額

計算租期：

long rentalDays = ChronoUnit.DAYS.between(start, end);


計算總金額： carTotal + seatCost + insuranceCost

🟥 Step4 — 寫入資料庫 + 完成頁

寫入 MySQL：

order.setOrderNo("OD" + System.currentTimeMillis());
Order saved = orderRepo.save(order);
session.setAttribute("latestOrder", saved);


顯示成功頁：

車款資訊

加購項目

總金額

查看我的訂單 / 回首頁

🔐 會員登入機制（JWT + Session）
1️⃣ 前端登入 → 儲存 Token
sessionStorage.setItem("jwtToken", data.token);

2️⃣ 導向租車頁
/reserve?token=xxxxx

3️⃣ 後端驗證 JWT

解析身分證字號

查詢會員

放入 HttpSession（loginUserId）

讓後續 Step1～Step4 能讀取登入資訊。

🚀 專案啟動方式

匯入 Maven 專案

建立 MySQL 資料庫

修改 application.properties

執行 Spring Boot

前端用 VS Code Live Server 開啟 index.html

開始預約租車流程！

🎯 我負責的功能總覽

✔ 前台租車流程 Step1～Step4 全流程

✔ 日期選擇器

✔ 車款篩選

✔ 加購項目

✔ Session 跨頁資料

✔ 訂單寫入 MySQL

✔ JWT 驗證
