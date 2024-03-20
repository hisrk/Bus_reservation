package com.reservation.util;

import com.reservation.model.Passenger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelGenerator {

    public byte[] generateExcel(List<Passenger> passengers) throws IOException {
        // Create a workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Passengers");

        // Create headers
        String[] headers = {"ID", "First Name", "Last Name", "Email", "Mobile", "Bus ID", "Route ID"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill data
        for (int i = 0; i < passengers.size(); i++) {
            Passenger passenger = passengers.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(passenger.getId());
            row.createCell(1).setCellValue(passenger.getFirstName());
            row.createCell(2).setCellValue(passenger.getLastName());
            row.createCell(3).setCellValue(passenger.getEmail());
            row.createCell(4).setCellValue(passenger.getMobile());
            row.createCell(5).setCellValue(passenger.getBusId());
            row.createCell(6).setCellValue(passenger.getRouteId());
        }

        // Write the workbook to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        // Close the workbook
        workbook.close();

        return outputStream.toByteArray();
    }
}
