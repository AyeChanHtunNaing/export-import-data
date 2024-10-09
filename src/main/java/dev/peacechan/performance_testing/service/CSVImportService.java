package dev.peacechan.performance_testing.service;

import dev.peacechan.performance_testing.entity.ProjectData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class CSVImportService {

    private static final Logger logger = LoggerFactory.getLogger(CSVImportService.class);
    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<ProjectData> projectTable;

    public CSVImportService(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
        this.projectTable = enhancedClient.table("ProjectData", TableSchema.fromBean(ProjectData.class));
    }

    public void importCSVToDynamoDB(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            // Skip the header line
            br.readLine();
            int successCount = 0;
            int failureCount = 0;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length < 50) { // Ensure all columns are present
                    logger.warn("Skipping line due to insufficient columns: {}", line);
                    failureCount++;
                    continue;
                }

                ProjectData projectData = new ProjectData();
                projectData.setProjectId(columns[0]);
                projectData.setProjectCategory(columns[1]);
                projectData.setProjectPn2Id(columns[2]);
                projectData.setName(columns[3]);
                projectData.setLogInCompanyNumber(columns[4]);
                projectData.setCreatedBy(columns[5]);
                projectData.setCreatedAt(columns[6]);
                projectData.setLastUpdatedBy(columns[7]);
                projectData.setLastUpdatedAt(columns[8]);
                projectData.setStartDateToApply(columns[9]);
                projectData.setEndDateToApply(columns[10]);
                projectData.setStatus(columns[11]);
                projectData.setNotificationEmailAddress1(columns[12]);
                projectData.setNotificationEmailAddress2(columns[13]);
                projectData.setNotificationEmailAddress3(columns[14]);
                projectData.setNotificationEmailAddress4(columns[15]);
                projectData.setProjectDescription(columns[16]);
                projectData.setProjectOwner(columns[17]);
                projectData.setProjectManager(columns[18]);
                projectData.setBudget(parseDouble(columns[19]));
                projectData.setActualCost(parseDouble(columns[20]));
                projectData.setEstimatedCost(parseDouble(columns[21]));
                projectData.setCurrency(columns[22]);
                projectData.setFundingSource(columns[23]);
                projectData.setProjectPhase(columns[24]);
                projectData.setPriorityLevel(columns[25]);
                projectData.setLocation(columns[26]);
                projectData.setClientName(columns[27]);
                projectData.setClientContact(columns[28]);
                projectData.setRiskLevel(columns[29]);
                projectData.setRiskDescription(columns[30]);
                projectData.setRiskMitigation(columns[31]);
                projectData.setStakeholderList(columns[32]);
                projectData.setMilestoneList(columns[33]);
                projectData.setTaskList(columns[34]);
                projectData.setIssueLog(columns[35]);
                projectData.setApprovalStatus(columns[36]);
                projectData.setApprovalDate(columns[37]);
                projectData.setNotes(columns[38]);
                projectData.setProjectType(columns[39]);
                projectData.setResourceAllocation(columns[40]);
                projectData.setProgressPercentage(parseDouble(columns[41]));
                projectData.setComments(columns[42]);
                projectData.setEstimatedDuration(columns[43]);
                projectData.setActualDuration(columns[44]);
                projectData.setProjectGoals(columns[45]);
                projectData.setProjectScope(columns[46]);
                projectData.setProjectConstraints(columns[47]);
                projectData.setProjectAssumptions(columns[48]);
                projectData.setProjectBenefits(columns[49]);

                // Save the data to DynamoDB
                try {
                    projectTable.putItem(projectData);
                    successCount++;
                } catch (Exception e) {
                    logger.error("Error saving project data: {}", e.getMessage());
                    failureCount++;
                }
            }

            logger.info("Import completed. Success: {}, Failures: {}", successCount, failureCount);
        } catch (IOException e) {
            logger.error("Error reading CSV file: {}", e.getMessage());
        }
    }

    private Double parseDouble(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Double.parseDouble(value);
    }
}
