package ru.rsreu.gorkin.codeanalyzer.desktop.excel.export;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ru.rsreu.gorkin.codeanalyzer.core.metrics.Metric;
import ru.rsreu.gorkin.codeanalyzer.desktop.metrics.SourceFileMetrics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelExporter {
    HSSFWorkbook workbook;

    public ExcelExporter() {
        workbook = new HSSFWorkbook();
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public void exportSourceFileMetrics(List<Metric> metrics, File fileToSave) {
        HSSFSheet sheet = workbook.createSheet("Первый лист");

        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("Метрика");
        rowhead.createCell(1).setCellValue("Полное название");
        rowhead.createCell(2).setCellValue("Значение");

        for (int i = 0, j = 1; i < metrics.size(); i++, j++) {
            Metric metric = metrics.get(i);
            HSSFRow row = sheet.createRow((short) j);
            row.createCell(0).setCellValue(metric.getTitle());
            row.createCell(1).setCellValue(metric.getDescription());
            row.createCell(2).setCellValue(metric.getCount());
        }

        try (
                FileOutputStream fileOut = new FileOutputStream(fileToSave)
        ) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportSourceFileMetrics(Map<String, List<Metric>> elementMetrics, File fileToSave) throws IOException {
        final int ELEMENT_NAME_COLUMN = 0;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Первый лист");

        Map<Class, Metric> metricTypesMap = getMetricTypes(elementMetrics.values());
        int rowIndex = 0;


        List<SourceFileMetrics> metricTypes = Arrays.stream(SourceFileMetrics.values()).sorted(Comparator.comparing(SourceFileMetrics::getShortName))
                .collect(Collectors.toList());

        // сокращенное название метрик
        HSSFRow shortNameRow = sheet.createRow((short) rowIndex++);
        shortNameRow.createCell(ELEMENT_NAME_COLUMN).setCellValue("Сокращенное название");
        for (int i = 0; i < metricTypes.size(); i++) {
            int columnIndex = ELEMENT_NAME_COLUMN + 1 + i;
            shortNameRow.createCell(columnIndex).setCellValue(metricTypes.get(i).getShortName());
        }

        HSSFRow titleMetricRow = sheet.createRow((short) rowIndex++);
        HSSFRow descriptionMetricRow = sheet.createRow((short) rowIndex++);
        descriptionMetricRow.createCell(ELEMENT_NAME_COLUMN).setCellValue("Описание");
        titleMetricRow.createCell(ELEMENT_NAME_COLUMN).setCellValue("Метрика");
        List<Metric> uniqueMetrics = metricTypesMap
                .values()
                .stream()
                .sorted(Comparator.comparing(SourceFileMetrics::getShortName))
                .collect(Collectors.toList());

        for (int i = 0; i < uniqueMetrics.size(); i++) {
            int columnIndex = ELEMENT_NAME_COLUMN + 1 + i;
            titleMetricRow.createCell(columnIndex).setCellValue(uniqueMetrics.get(i).getTitle());
            descriptionMetricRow.createCell(columnIndex).setCellValue(uniqueMetrics.get(i).getDescription());
        }

        Set<String> keys = elementMetrics.keySet();

        for (String key : keys) {
            HSSFRow currentRow = sheet.createRow((short) rowIndex++);
            currentRow.createCell(ELEMENT_NAME_COLUMN).setCellValue(key);
            List<Metric> metrics = elementMetrics.get(key);

            metrics.sort(Comparator.comparing(SourceFileMetrics::getShortName));
            for (int i = 0; i < metrics.size(); i++) {
                int columnIndex = ELEMENT_NAME_COLUMN + 1 + i;
                currentRow.createCell(columnIndex).setCellValue(metrics.get(i).getCount());
            }
        }
        try (
                FileOutputStream fileOut = new FileOutputStream(fileToSave);
        ) {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<Class, Metric> getMetricTypes(Collection<List<Metric>> metrics) {
        Map<Class, Metric> metricTypes = new HashMap<>();
        metrics.forEach(
                metricList -> metricList.forEach(
                        metric -> metricTypes.put(metric.getClass(), metric))
        );
        return metricTypes;
    }
}
