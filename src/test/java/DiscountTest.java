import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DiscountTest {
    WebDriver driver;
    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver= new ChromeDriver();
    }
    @Test
    public void zipCode() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
        driver.findElement(By.name("first_name")).sendKeys("Illia");
        driver.findElement(By.name("last_name")).sendKeys("Abramovich");
        driver.findElement(By.name("email")).sendKeys("i.a6ram@gmail.com");
        driver.findElement(By.name("password1")).sendKeys("pipika");
        driver.findElement(By.name("password2")).sendKeys("pipika");
        driver.findElement(By.cssSelector("[value=Register")).click();
        boolean isDisplayed = driver.findElement(By.className("confirmation_message")).isDisplayed();
        Assert.assertTrue(isDisplayed);
        String login = driver.findElement(By.xpath("//*[contains(text(), 'Email')]/..//b")).getText();
        String password = driver.findElement(By.xpath("//*[contains(text(), 'Password')]/..//td[2]")).getText();
        driver.get("https://sharelane.com/cgi-bin/show_book.py?book_id=2");
        driver.findElement(By.name("email")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.cssSelector("[value=Login]")).click();
        driver.get("https://sharelane.com/cgi-bin/show_book.py?book_id=2");
        driver.findElement(By.cssSelector("[href='./add_to_cart.py?book_id=2']")).click();
        driver.get("https://sharelane.com/cgi-bin/shopping_cart.py");
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys("1");
        driver.findElement(By.cssSelector("[value=Update]")).click();
        String price = driver.findElement(By.xpath("//tr[2]//td[7]")).getText();
        assertEquals(price, "10", "Price is't correct");

    }
    @AfterMethod
    public void tearDoun(){
        driver.quit();
    }
}
