# RB Automation Framework

A simple, Java-based **Selenium Test Automation Framework** using **TestNG** and **Maven**, built with the **Page Object Model (POM)** pattern.  
It supports running automated UI tests, managing test data, reporting, and browser execution.

---

## 📌 Features

✅ Page Object Model (POM) for structured automation  
✅ Maven project with TestNG for test execution  
✅ Configurable browser and environment settings  
✅ TestNG HTML reports  
✅ Parallel test execution support  
✅ Organized folder structure (src/main, src/test)

---

## 🔧 Prerequisites

Make sure you have:

✔ Java JDK installed (Java 19)  
✔ Maven installed  
✔ An IDE ( IntelliJ)
---

### Setup & Run

1. **Unzip the project**

2. **Import in IDE**
    - Open IDE → Import → **Existing Maven Project** → Select the unzipped folder


3. **Run Tests**
    - Using the **`testng.xml`** suite file in the project, or
     - Using Maven command:

```bash
mvn clean test