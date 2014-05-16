/**
 * Copyright 2014 Fernando Rincon Martin <frm.rincon@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.abstractform.vaadin.itest.test;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

@SuppressWarnings("unused")
public class ITTestAll {

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:9988/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testVaadinAbstractForm() throws Exception {
		driver.get(baseUrl + "/test");
		assertEquals("Sample Name", driver.findElement(By.xpath("(//input[@type='text'])[4]")).getAttribute("value"));
		assertEquals("on", driver.findElement(By.id("gwt-uid-2")).getAttribute("value"));
		driver.findElement(By.cssSelector("span.v-button-caption")).click();
		Thread.sleep(500);
		assertEquals("A", driver.findElement(By.xpath("(//input[@type='text'])[8]")).getAttribute("value"));
		assertEquals("mail@nomail.org", driver.findElement(By.xpath("(//input[@type='text'])[7]")).getAttribute("value"));
		assertEquals("▼Others", driver.findElement(By.cssSelector("span.v-button-caption")).getText());
		driver.findElement(By.xpath("//div[2]/div/div/span/span")).click();
		Thread.sleep(1000);
		assertEquals("Otro nombre", driver.findElement(By.xpath("(//input[@type='text'])[4]")).getAttribute("value"));
		assertEquals("B", driver.findElement(By.xpath("(//input[@type='text'])[8]")).getAttribute("value"));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

}
