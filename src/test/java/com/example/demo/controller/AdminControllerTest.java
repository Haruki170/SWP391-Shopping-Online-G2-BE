package com.example.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.edge.driver", "path/to/edgedriver");
        driver = new EdgeDriver();
        driver.get("http://localhost:8080");
    }

    @Test
    void getAll() {
        driver.findElement(By.id("getAllButton")).click();
        String result = driver.findElement(By.id("result")).getText();
        assertNotNull(result);
    }

    @Test
    void insertAdmin() {
        driver.findElement(By.id("insertAdminButton")).click();
        driver.findElement(By.id("adminName")).sendKeys("Test Admin");
        driver.findElement(By.id("adminEmail")).sendKeys("test@admin.com");
        driver.findElement(By.id("submitButton")).click();
        String result = driver.findElement(By.id("result")).getText();
        assertEquals("Admin inserted successfully", result);
    }

    @Test
    void updateStatus() {
        driver.findElement(By.id("updateStatusButton")).click();
        driver.findElement(By.id("adminId")).sendKeys("1");
        driver.findElement(By.id("newStatus")).sendKeys("Active");
        driver.findElement(By.id("submitButton")).click();
        String result = driver.findElement(By.id("result")).getText();
        assertEquals("Status updated successfully", result);
    }

    @Test
    void deleteAdmin() {
        driver.findElement(By.id("deleteAdminButton")).click();
        driver.findElement(By.id("adminId")).sendKeys("1");
        driver.findElement(By.id("submitButton")).click();
        String result = driver.findElement(By.id("result")).getText();
        assertEquals("Admin deleted successfully", result);
    }

    @Test
    void updateAdmin() {
        driver.findElement(By.id("updateAdminButton")).click();
        driver.findElement(By.id("adminId")).sendKeys("1");
        driver.findElement(By.id("adminName")).sendKeys("Updated Admin");
        driver.findElement(By.id("adminEmail")).sendKeys("updated@admin.com");
        driver.findElement(By.id("submitButton")).click();
        String result = driver.findElement(By.id("result")).getText();
        assertEquals("Admin updated successfully", result);
    }
}