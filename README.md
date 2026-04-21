# RB Automation Framework

![CI Automation](https://github.com/sooryathomas2k11-wq/Automation-Framework/actions/workflows/maven.yml/badge.svg)

A Java-based UI automation framework built with Selenium, TestNG, and Maven. The framework uses the Page Object Model for maintainability, Dockerized Selenium Grid for consistent execution, and GitHub Actions for CI automation.

## Project Overview

This project demonstrates how to design a maintainable UI automation framework and run it in a reproducible containerized environment. It focuses on clean test structure, parallel execution, reporting, and CI-driven execution.

## Tech Stack

- Java 17
- Selenium WebDriver
- TestNG
- Maven
- Docker Compose
- Selenium Grid
- GitHub Actions
- Extent Reports

## Key Features

- Page Object Model for reusable and maintainable test code
- Parallel test execution using TestNG
- Docker Compose setup for Selenium Grid
- GitHub Actions workflow for automated CI execution
- Extent report generation for execution results
- Video capture support for test execution debugging
- CSV-based test data support

## Framework Design

The framework is organized to separate concerns clearly:

- `src/main/java/org/example/base` for base classes and driver setup
- `src/main/java/org/example/pages` for page object classes
- `src/main/java/org/example/reporting` for listeners, screenshots, and reports
- `src/main/java/org/example/utils` for utilities such as config and CSV readers
- `src/test/java/tests` for test classes
- `src/test/java/verifications` for assertion and verification logic
- `src/test/resources` for test data files

## CI Workflow

The GitHub Actions pipeline performs the following steps:

- Checks out the repository
- Sets up JDK 17
- Starts Selenium Grid with Docker Compose
- Waits for the Grid to become available
- Runs the Maven TestNG suite
- Uploads reports and execution videos as workflow artifacts

This helps ensure that tests run in a stable and repeatable environment on every push and pull request.

## Running Locally

### Prerequisites

- Java 17
- Maven
- Docker Desktop

### Steps

```bash
git clone https://github.com/sooryathomas2k11-wq/Automation-Framework.git
cd Automation-Framework
docker compose up -d
mvn clean test -DsuiteXmlFile=testng.xml -DhubUrl=http://localhost:4444/wd/hub
docker compose down

