package com.springboot.schoolassistantapi;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium {

	public String extractTextFromPaper() {
		// Initialize the WebDriver
        WebDriver driver = new FirefoxDriver();

        // Open the Pen To Print website
        driver.get("https://www.pen-to-print.com/handwriting-to-text-online-ocr/");
        
        // Input the file path of the image we want to OCR
        String filePath = System.getProperty("user.home") + "\\Downloads\\School Assistant Paper.jpg";
        WebElement fileInput = new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        fileInput.sendKeys(filePath);
        
        // Click the convert button
        WebElement convertButton = new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".convert-button")));
        convertButton.click();
        
        // Grab the extracted text and log it out in the console
        WebElement contentElement = new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".scanline-cell-content")));
        String content = contentElement.getAttribute("innerHTML");
        
        // Go to Google Translate for translation
        driver.get("https://translate.google.com/");
        
        // Accept the terms and conditions
        WebElement acceptButton = new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Accept all')]")));
        acceptButton.click();
        
        // Paste the content for translation
        WebElement originalTextArea = new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("textarea[aria-label='Source text']")));
        originalTextArea.sendKeys(content);
        
        // Retrieve the translation
        WebElement translatedTextArea = new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span[lang='en']")));
        content = translatedTextArea.getAttribute("innerText");
        
        // Close the browser after completion
        driver.quit();
        return content;
	}

}
