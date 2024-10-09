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
            row.createCell(16).setCellValue(projectData.getProjectDescription() != null ? projectData.getProjectDescription() : "");
            row.createCell(17).setCellValue(projectData.getProjectOwner() != null ? projectData.getProjectOwner() : "");
            row.createCell(18).setCellValue(projectData.getProjectManager() != null ? projectData.getProjectManager() : "");
            row.createCell(19).setCellValue(projectData.getBudget() != null ? projectData.getBudget() : 0.0);
            row.createCell(20).setCellValue(projectData.getActualCost() != null ? projectData.getActualCost() : 0.0);
            row.createCell(21).setCellValue(projectData.getEstimatedCost() != null ? projectData.getEstimatedCost() : 0.0);
            row.createCell(22).setCellValue(projectData.getCurrency() != null ? projectData.getCurrency() : "");
            row.createCell(23).setCellValue(projectData.getFundingSource() != null ? projectData.getFundingSource() : "");
            row.createCell(24).setCellValue(projectData.getProjectPhase() != null ? projectData.getProjectPhase() : "");
            row.createCell(25).setCellValue(projectData.getPriorityLevel() != null ? projectData.getPriorityLevel() : "");
            row.createCell(26).setCellValue(projectData.getLocation() != null ? projectData.getLocation() : "");
            row.createCell(27).setCellValue(projectData.getClientName() != null ? projectData.getClientName() : "");
            row.createCell(28).setCellValue(projectData.getClientContact() != null ? projectData.getClientContact() : "");
            row.createCell(29).setCellValue(projectData.getRiskLevel() != null ? projectData.getRiskLevel() : "");
            row.createCell(30).setCellValue(projectData.getRiskDescription() != null ? projectData.getRiskDescription() : "");
            row.createCell(31).setCellValue(projectData.getRiskMitigation() != null ? projectData.getRiskMitigation() : "");
            row.createCell(32).setCellValue(projectData.getStakeholderList() != null ? projectData.getStakeholderList() : "");
            row.createCell(33).setCellValue(projectData.getMilestoneList() != null ? projectData.getMilestoneList() : "");
            row.createCell(34).setCellValue(projectData.getTaskList() != null ? projectData.getTaskList() : "");
            row.createCell(35).setCellValue(projectData.getIssueLog() != null ? projectData.getIssueLog() : "");
            row.createCell(36).setCellValue(projectData.getApprovalStatus() != null ? projectData.getApprovalStatus() : "");
            row.createCell(37).setCellValue(projectData.getApprovalDate() != null ? projectData.getApprovalDate() : "");
            row.createCell(38).setCellValue(projectData.getNotes() != null ? projectData.getNotes() : "");
            row.createCell(39).setCellValue(projectData.getProjectType() != null ? projectData.getProjectType() : "");
            row.createCell(40).setCellValue(projectData.getResourceAllocation() != null ? projectData.getResourceAllocation() : "");
            row.createCell(41).setCellValue(projectData.getProgressPercentage() != null ? projectData.getProgressPercentage() : 0.0);
            row.createCell(42).setCellValue(projectData.getComments() != null ? projectData.getComments() : "");
            row.createCell(43).setCellValue(projectData.getEstimatedDuration() != null ? projectData.getEstimatedDuration() : "");
            row.createCell(44).setCellValue(projectData.getActualDuration() != null ? projectData.getActualDuration() : "");
            row.createCell(45).setCellValue(projectData.getProjectGoals() != null ? projectData.getProjectGoals() : "");
            row.createCell(46).setCellValue(projectData.getProjectScope() != null ? projectData.getProjectScope() : "");
            row.createCell(47).setCellValue(projectData.getProjectConstraints() != null ? projectData.getProjectConstraints() : "");
            row.createCell(48).setCellValue(projectData.getProjectAssumptions() != null ? projectData.getProjectAssumptions() : "");
            row.createCell(49).setCellValue(projectData.getProjectBenefits() != null ? projectData.getProjectBenefits() : "");
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
