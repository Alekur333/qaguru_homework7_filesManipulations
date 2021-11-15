package kur.alexei.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import kur.alexei.pages.BasePage;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilesTest extends BasePage {

    @Test
    @DisplayName("Проверка отображения названия файла после загрузки на странице")
    void fileNameShouldBeDisplayedAfterUploadingTest() {

        open("https://www.file-upload.com/?op=upload_form");
        $("input[type='file']").uploadFromClasspath("sample.txt");
        $(".xfname").shouldHave(text("sample.txt"));
    }

    @Test
    @DisplayName("Проверка содержимого скаченного txt файла")
    void checkDownloadedTextFile() throws IOException {

        open("https://github.com/Alekur333/qaguru_homework7_filesManipulations/blob/main/README.md");
        File downloadedTxtFile = $("#raw-url").download();
        String fileContent = IOUtils.toString(new FileReader(downloadedTxtFile));
        assertTrue(fileContent.contains("Работаем с файлами. Дмитрий Тучс"));
    }

    @Test
    @DisplayName("Проверка содержимого скаченного pdf файла")
    void checkDownloadedPdfFile() throws IOException {

        open("https://www.samsung.com/ru/support/model/SM-R800NZSASER/#downloads");
        File downloadedPdfFile = $("a[data-file-id=\"7173037\"]").download();
        PDF parsedPdf = new PDF(downloadedPdfFile);
        Assertions.assertEquals(154,parsedPdf.numberOfPages);
        assertTrue(parsedPdf.text.contains("SM-R800\nSM-R805F\nSM-R810\nSM-R815F"));
    }

    @Test
    @DisplayName("Проверка содержимого скаченного xls файла")
    void checkDownloadedXlsFile() throws IOException {

        open("http://вся-рыбка.рф/pricelist.html");
        File downloadedXlsFile = $x("//li[contains(text(), 'Тюмень')]").$("a").download();
        XLS parsedXls = new XLS(downloadedXlsFile);
        System.out.println();
        String priceAddress = parsedXls.excel
                .getSheetAt(0)
                .getRow(2)
                .getCell(3).getStringCellValue();
        assertEquals(priceAddress, "г. Тюмень ул Молодежная 80 склад 5А");
        assertTrue(priceAddress.contains("г. Тюмень ул Молодежная 80 склад 5А"));
    }


}
