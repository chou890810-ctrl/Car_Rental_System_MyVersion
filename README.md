# 🚗 Gjun Rent 線上租車系統（前台租車流程）

---

## 📌 專案簡介

Gjun Rent 是一款會員制線上租車平台，我主要負責會員登入後的預約租車流程（Step1～Step4），包含資料保存、車款篩選與訂單寫入等功能。

---

## 🎯 我的負責範圍

- 會員登入後預約流程完整設計（Step1 ～ Step4）
- Session 跨頁資料保存
- 車款動態載入與篩選
- JWT 登入驗證
- 訂單寫入 MySQL

---

## 🛠️ 技術亮點

| 技術               | 用途                           |
|--------------------|--------------------------------|
| Spring Boot 3.x    | MVC Controller、DI、Routing     |
| JPA + Hibernate    | 資料存取（車輛 / 會員 / 訂單）  |
| MySQL              | 資料庫管理                     |
| Thymeleaf          | HTML 模板引擎                  |
| jQuery + Ajax      | DOM 操作、前端互動             |
| JWT                | 登入驗證                       |
| Session            | 跨頁資料保存                   |

---

## 📂 專案結構（簡化重點）

```
src/main/java/com.demo
 ├── controller
 │    ├── PageController.java
 │    ├── ReserveController.java   ← Step1～Step4 (我負責)
 │    └── MemberController.java
 ├── model
 │    ├── Car.java
 │    ├── Branch.java
 │    └── Order.java
 ├── repository
 │    ├── CarRepository.java
 │    ├── MemberRepository.java
 │    └── OrderRepository.java
 ├── service
 │    ├── CarService.java
 │    └── BranchService.java
 └── util
      └── JwtUtil.java
```

---

## ⭐ 會員租車完整流程（Step1～Step4）
🏠 首頁（Home）
<img src="images/IMAGE_01" width="900"> <img src="IMAGE_02" width="900">
### 🟦 Step1 — 預約資訊填寫
<img src="IMAGE_03" width="900">
- 使用者填寫：取／還車據點、日期區間（daterangepicker）、時間（自動時段）、車款類型
- Controller 接收資料 → 存入 Session
- 跨步驟保存資料

---

### 🟩 Step2 — 車款選擇 & 加購
<img src="IMAGE_04" width="900">
- 根據座位數由資料庫撈出車輛：

    ```java
    private static final Map<String, List<Integer>> CAR_TYPE_MAP = Map.of(
        "五人座", List.of(5),
        "七九人座", List.of(7, 8, 9)
    );
    ```
Step2-1 加購保險 / 兒童座椅
<img src="IMAGE_05" width="900">
- 功能：查看車款卡片（含圖片、租金、排氣量）、選擇加購（保險 / 兒童安全座椅）、選擇結果寫回 Session

---

### 🟨 Step3 — 訂單確認頁
<img src="IMAGE_06" width="900">
- 從 Session 讀取資訊，呈現車款、日期/時間、加購項目、計算租期和總金額

    ```java
    long rentalDays = ChronoUnit.DAYS.between(start, end);
    // 總金額：carTotal + seatCost + insuranceCost
    ```

---

### 🟥 Step4 — 寫入資料庫 & 完成頁
<img src="IMAGE_07" width="900">
- 訂單存入 MySQL：

    ```java
    order.setOrderNo("OD" + System.currentTimeMillis());
    Order saved = orderRepo.save(order);
    session.setAttribute("latestOrder", saved);
    ```

- 顯示預約成功、車款資訊、加購項目、總金額
- 提供「查看我的訂單」/「回首頁」選項

---
📄 會員訂單列表
<img src="IMAGE_08" width="900">
---
📑 訂單詳細資料
<img src="IMAGE_09" width="900">
## 🔐 會員登入機制（JWT + Session）

1. 前端登入，儲存 Token
    ```js
    sessionStorage.setItem("jwtToken", data.token);
    ```
2. 導向租車頁 `/reserve?token=xxxxx`
3. 後端驗證 JWT，解析身分證字號、查詢會員、存入 HttpSession
4. Step1~Step4 皆可取得登入會員資訊

---

## 🚀 專案快速啟動

1. 匯入 Maven 專案
2. 建立 MySQL 資料庫
3. 修改 `application.properties`
4. 執行 Spring Boot
5. 前端用 VS Code Live Server 開啟 `index.html`
6. 開始租車流程！

---

## ✔ 主要功能總覽

- 租車流程 Step1～Step4
- 日期選擇器
- 車款篩選
- 加購項目
- Session 跨頁保存
- 訂單寫入 MySQL
- JWT 會員驗證

---

歡迎交流！有問題歡迎 issue 或聯絡我 :smile:
