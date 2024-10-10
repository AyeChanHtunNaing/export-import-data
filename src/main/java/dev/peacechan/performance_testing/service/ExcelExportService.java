package dev.peacechan.performance_testing.service;

import dev.peacechan.performance_testing.entity.ProjectData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelExportService {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<ProjectData> projectTable;

    // List to store performance data (time and memory usage)
    private final List<Long> exportTimes = new ArrayList<>();
    private final List<Long> memoryUsages = new ArrayList<>();

    public ExcelExportService(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
        this.projectTable = enhancedClient.table("local_ProjectTestingData", TableSchema.fromBean(ProjectData.class));
    }

    public void exportDataToExcel(String excelFilePath) throws IOException {
        // Measure start time
        long startTime = System.currentTimeMillis();
        // Measure memory usage before export
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        // Create a new Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Project Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {
                "Project ID", "Project Category", "Project PN2 ID", "Name", "Log In Company Number",
                "Created By", "Created At", "Last Updated By", "Last Updated At", "Start Date To Apply",
                "End Date To Apply", "Status", "Notification Email Address 1", "Notification Email Address 2",
                "Notification Email Address 3", "Notification Email Address 4", "Project Description",
                "Project Owner", "Project Manager", "Budget", "Actual Cost", "Estimated Cost", "Currency",
                "Funding Source", "Project Phase", "Priority Level", "Location", "Client Name", "Client Contact",
                "Risk Level", "Risk Description", "Risk Mitigation", "Stakeholder List", "Milestone List",
                "Task List", "Issue Log", "Approval Status", "Approval Date", "Notes", "Project Type",
                "Resource Allocation", "Progress Percentage", "Comments", "Estimated Duration", "Actual Duration",
                "Project Goals", "Project Scope", "Project Constraints", "Project Assumptions", "Project Benefits"
        };

        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // Fetch data from DynamoDB
        Iterator<ProjectData> projectIterator = projectTable.scan().items().iterator();

        // Write data to Excel
        int rowNum = 1;
        while (projectIterator.hasNext()) {
            ProjectData projectData = projectIterator.next();
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(projectData.getProjectId() != null ? projectData.getProjectId() : "");
            row.createCell(1).setCellValue(projectData.getProjectCategory() != null ? projectData.getProjectCategory() : "");
            row.createCell(2).setCellValue(projectData.getProjectPn2Id() != null ? projectData.getProjectPn2Id() : "");
            row.createCell(3).setCellValue(projectData.getName() != null ? projectData.getName() : "");
            row.createCell(4).setCellValue(projectData.getLogInCompanyNumber() != null ? projectData.getLogInCompanyNumber() : "");
            row.createCell(5).setCellValue(projectData.getCreatedBy() != null ? projectData.getCreatedBy() : "");
            row.createCell(6).setCellValue(projectData.getCreatedAt() != null ? projectData.getCreatedAt() : "");
            row.createCell(7).setCellValue(projectData.getLastUpdatedBy() != null ? projectData.getLastUpdatedBy() : "");
            row.createCell(8).setCellValue(projectData.getLastUpdatedAt() != null ? projectData.getLastUpdatedAt() : "");
            row.createCell(9).setCellValue(projectData.getStartDateToApply() != null ? projectData.getStartDateToApply() : "");
            row.createCell(10).setCellValue(projectData.getEndDateToApply() != null ? projectData.getEndDateToApply() : "");
            row.createCell(11).setCellValue(projectData.getStatus() != null ? projectData.getStatus() : "");
            row.createCell(12).setCellValue(projectData.getNotificationEmailAddress1() != null ? projectData.getNotificationEmailAddress1() : "");
            row.createCell(13).setCellValue(projectData.getNotificationEmailAddress2() != null ? projectData.getNotificationEmailAddress2() : "");
            row.createCell(14).setCellValue(projectData.getNotificationEmailAddress3() != null ? projectData.getNotificationEmailAddress3() : "");
            row.createCell(15).setCellValue(projectData.getNotificationEmailAddress4() != null ? projectData.getNotificationEmailAddress4() : "");
            row.createCell(16).setCellValue(projectData.getProjectdescription() != null ? projectData.getProjectdescription() : "");
            row.createCell(17).setCellValue(projectData.getProjectowner() != null ? projectData.getProjectowner() : "");
            row.createCell(18).setCellValue(projectData.getProjectmanager() != null ? projectData.getProjectmanager() : "");
            row.createCell(19).setCellValue(projectData.getBudget() != null ? projectData.getBudget() : "");
            row.createCell(20).setCellValue(projectData.getActualcost() != null ? projectData.getActualcost() : "");
            row.createCell(21).setCellValue(projectData.getEstimatedcost() != null ? projectData.getEstimatedcost() : "");
            row.createCell(22).setCellValue(projectData.getCurrency() != null ? projectData.getCurrency() : "");
            row.createCell(23).setCellValue(projectData.getFundingsource() != null ? projectData.getFundingsource() : "");
            row.createCell(24).setCellValue(projectData.getProjectphase() != null ? projectData.getProjectphase() : "");
            row.createCell(25).setCellValue(projectData.getPrioritylevel() != null ? projectData.getPrioritylevel() : "");
            row.createCell(26).setCellValue(projectData.getLocation() != null ? projectData.getLocation() : "");
            row.createCell(27).setCellValue(projectData.getClientname() != null ? projectData.getClientname() : "");
            row.createCell(28).setCellValue(projectData.getClientcontact() != null ? projectData.getClientcontact() : "");
            row.createCell(29).setCellValue(projectData.getRisklevel() != null ? projectData.getRisklevel() : "");
            row.createCell(30).setCellValue(projectData.getRiskdescription() != null ? projectData.getRiskdescription() : "");
            row.createCell(31).setCellValue(projectData.getRiskmitigation() != null ? projectData.getRiskmitigation() : "");
            row.createCell(32).setCellValue(projectData.getStakeholderlist() != null ? projectData.getStakeholderlist() : "");
            row.createCell(33).setCellValue(projectData.getMilestonelist() != null ? projectData.getMilestonelist() : "");
            row.createCell(34).setCellValue(projectData.getTasklist() != null ? projectData.getTasklist() : "");
            row.createCell(35).setCellValue(projectData.getIssuelog() != null ? projectData.getIssuelog() : "");
            row.createCell(36).setCellValue(projectData.getApprovalstatus() != null ? projectData.getApprovalstatus() : "");
            row.createCell(37).setCellValue(projectData.getApprovaldate() != null ? projectData.getApprovaldate() : "");
            row.createCell(38).setCellValue(projectData.getNotes() != null ? projectData.getNotes() : "");
            row.createCell(39).setCellValue(projectData.getProjecttype() != null ? projectData.getProjecttype() : "");
            row.createCell(40).setCellValue(projectData.getResourceallocation() != null ? projectData.getResourceallocation() : "");
            row.createCell(41).setCellValue(projectData.getProgresspercentage() != null ? projectData.getProgresspercentage() : "");
            row.createCell(42).setCellValue(projectData.getComments() != null ? projectData.getComments() : "");
            row.createCell(43).setCellValue(projectData.getEstimatedduration() != null ? projectData.getEstimatedduration() : "");
            row.createCell(44).setCellValue(projectData.getActualduration() != null ? projectData.getActualduration() : "");
            row.createCell(45).setCellValue(projectData.getProjectgoals() != null ? projectData.getProjectgoals() : "");
            row.createCell(46).setCellValue(projectData.getProjectscope() != null ? projectData.getProjectscope() : "");
            row.createCell(47).setCellValue(projectData.getProjectconstraints() != null ? projectData.getProjectconstraints() : "");
            row.createCell(48).setCellValue(projectData.getProjectassumptions() != null ? projectData.getProjectassumptions() : "");
            row.createCell(49).setCellValue(projectData.getProjectbenefits() != null ? projectData.getProjectbenefits() : "");
        }

        // Write the output to the file
        try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }
        // Measure memory usage after export
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = memoryAfter - memoryBefore;

        // Measure end time
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Store performance data
        exportTimes.add(timeTaken);
        memoryUsages.add(memoryUsed);

        System.out.println("Export completed. Time taken: " + timeTaken + " ms, Memory used: " + memoryUsed + " bytes");
    }


}
