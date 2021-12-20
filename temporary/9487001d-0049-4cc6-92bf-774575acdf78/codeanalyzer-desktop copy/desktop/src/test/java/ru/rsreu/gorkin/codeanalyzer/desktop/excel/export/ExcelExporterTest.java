package ru.rsreu.gorkin.codeanalyzer.desktop.excel.export;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class ExcelExporterTest {
    @InjectMocks
    @Spy
    ExcelExporter excelExporter;

    @Spy
    HSSFWorkbook workbook;

    @Test
    void test() throws IOException {
        excelExporter.exportSourceFileMetrics(new ArrayList<>(), new File("src/test/resources/testfile.xls"));
        Mockito.verify(workbook, Mockito.times(1))
                .write(Mockito.any(FileOutputStream.class));
    }
}
