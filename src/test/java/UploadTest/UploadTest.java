package UploadTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import static UploadTest.Utils.areFilesEqual;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.util.logging.Logger.getLogger;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UploadTest {

    private static Level previousLevel;

    @BeforeAll
    public static void setUp() {
        previousLevel = getLogger("").getLevel();
        getLogger("").setLevel(Level.WARNING);
        Configuration.baseUrl = "https://www.resizepixel.com";
        Configuration.browser = "firefox";
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        Configuration.downloadsFolder = "src/test/resources";
        Configuration.browserCapabilities = new FirefoxOptions().setPageLoadStrategy(PageLoadStrategy.EAGER)
                .addArguments("--window-size=1920,1080", "--disable-notifications", "--disable-dev-tools", "--fastSetValue")
                .addPreference("permissions.default.geo", 2);
        open("");
    }

    @AfterAll
    public static void tearDown() {
        closeWebDriver();
        getLogger("").setLevel(previousLevel);
    }

    @Test
    @DisplayName("Тест загрузки, скачивания и проверка скачанного файла")
    public void uploadTest() throws IOException {
        String filePath = "src/test/resources/photo.png";
        $("#uploadImageBtn").uploadFile(new File(filePath));
        $("#editPanel").shouldBe(visible);
        $("#downloadBtn").click();
        File downloadedFile = $("#downloadBtn").scrollTo().download();
        assertTrue(downloadedFile.exists(), "\nОшибка: не удалось скачать файл.\n Тест провален.");
        assertTrue(areFilesEqual(filePath, downloadedFile.getPath()), "\nОшибка: файлы не совпадают.\n Тест провален.");
        System.out.println("\nТест прошёл успешно.");
    }

}