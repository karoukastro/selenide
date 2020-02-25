package common;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.MoviePage;
import pages.SideBar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.screenshot;
import static io.qameta.allure.Allure.addAttachment;

public class BaseTest {

    protected static LoginPage login;
    protected static SideBar side;
    protected static MoviePage movie;

    @BeforeMethod
    public void start() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://ninjaplus-web:5000";
        Configuration.timeout = 30000;

        login = new LoginPage();
        side = new SideBar();
        movie = new MoviePage();

    }

    @AfterMethod
    public void finish() { //cleanup
        //Tiramos um screenshot pelo Selenide
        String tempShot = screenshot("temp_shot");

        //Para anexar em binario no report do Allure
        try {
            BufferedImage bimage = ImageIO.read(new File(tempShot));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bimage, "png", baos);
            byte[] finalShot = baos.toByteArray();
            addAttachment("Evidencia", new ByteArrayInputStream(finalShot));

        } catch (IOException ex) {
            System.out.println("Deu erro ao anexar o Screenshot :( Trace => " + ex.getMessage());
        }

        login.clearSession();
    }
}
