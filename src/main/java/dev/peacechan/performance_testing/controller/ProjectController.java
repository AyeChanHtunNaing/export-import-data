package dev.peacechan.performance_testing.controller;

import dev.peacechan.performance_testing.service.CSVImportService;
import dev.peacechan.performance_testing.service.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ExcelExportService excelExportService;
    private final CSVImportService csvImportService;

    @Autowired
    public ProjectController(ExcelExportService excelExportService, CSVImportService csvImportService) {
        this.excelExportService = excelExportService;
        this.csvImportService = csvImportService;
    }

    // Endpoint for exporting project data to Excel
    @GetMapping("/export")
    public ResponseEntity<String> exportProjects() {
        // Create the tmp/export directory if it doesn't exist
        File exportDir = new File("./tmp/export");
        if (!exportDir.exists()) {
            exportDir.mkdirs(); // Create directories if they do not exist
        }

        // Generate filename with current timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = "./tmp/export/project_data_" + timestamp + ".xlsx";

        try {
            excelExportService.exportDataToExcel(filePath);
            return ResponseEntity.ok("Data exported successfully to " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error exporting data: " + e.getMessage());
        }
    }

    // Endpoint for importing project data from CSV
    @PostMapping("/import")
    public ResponseEntity<String> importProjects(@RequestParam("file") MultipartFile file) {
        try {
            // Save the uploaded file temporarily
            Path tempFile = Files.createTempFile("import-", ".csv");
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Call the import service
            csvImportService.importCSVToDynamoDB(tempFile.toString());

            // Optionally, delete the temporary file after processing
            Files.delete(tempFile);

            return ResponseEntity.ok("Data imported successfully from " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error importing data: " + e.getMessage());
        }
    }
}
