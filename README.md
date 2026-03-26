# 🚀 RB Automation Framework

![CI Automation](https://github.com/sooryathomas2k11-wq/Automation-Framework/actions/workflows/maven.yml/badge.svg)

A modern, scalable **Selenium Test Automation Framework** built with Java, TestNG, and Maven. This framework is designed for high-performance execution using a **Dockerized Selenium Grid** and is fully integrated with **GitHub Actions CI/CD**.

---

## 🌟 Key Features

* **Infrastructure as Code:** Uses **Docker Compose** to spin up a Selenium Grid (Hub & Chrome Nodes) automatically.
* **CI/CD Integrated:** Automated test execution on every push via **GitHub Actions**.
* **Video Recording:** Captures `.mp4` recordings of every test execution for easy debugging.
* **Live Monitoring:** View real-time browser execution via a built-in [noVNC dashboard](http://localhost:7900).
* **Parallel Execution:** Thread-safe implementation using `ThreadLocal` for high-speed concurrent testing.
* **Page Object Model (POM):** Clean separation of test logic and UI locators.

---

## 🛠️ Prerequisites

To run this project locally, you need:
* **Java JDK 17** (LTS)
* **Maven**
* **Docker Desktop** (Ensures the Selenium Grid runs in containers)

---

## 🚀 Getting Started

### 1. Clone & Setup
```bash
git clone [https://github.com/sooryathomas2k11-wq/Automation-Framework.git](https://github.com/sooryathomas2k11-wq/Automation-Framework.git)
cd Automation-Framework
