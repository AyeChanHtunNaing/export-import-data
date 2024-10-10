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
        this.projectTable = enhancedClient.table("local_ProjectTestingData", TableSchema.fromBean(ProjectData.class));
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
                projectData.setProjectId(columns[0]); // Set sort key
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
                projectData.setProjectdescription(columns[16]);
                projectData.setProjectowner(columns[17]);
                projectData.setProjectmanager(columns[18]);
                projectData.setBudget(columns[19]);
                projectData.setActualcost(columns[20]);
                projectData.setEstimatedcost(columns[21]);
                projectData.setCurrency(columns[22]);
                projectData.setFundingsource(columns[23]);
                projectData.setProjectphase(columns[24]);
                projectData.setPrioritylevel(columns[25]);
                projectData.setLocation(columns[26]);
                projectData.setClientname(columns[27]);
                projectData.setClientcontact(columns[28]);
                projectData.setRisklevel(columns[29]);
                projectData.setRiskdescription(columns[30]);
                projectData.setRiskmitigation(columns[31]);
                projectData.setStakeholderlist(columns[32]);
                projectData.setMilestonelist(columns[33]);
                projectData.setTasklist(columns[34]);
                projectData.setIssuelog(columns[35]);
                projectData.setApprovalstatus(columns[36]);
                projectData.setApprovaldate(columns[37]);
                projectData.setNotes(columns[38]);
                projectData.setProjecttype(columns[39]);
                projectData.setResourceallocation(columns[40]);
                projectData.setProgresspercentage(columns[41]);
                projectData.setComments(columns[42]);
                projectData.setEstimatedduration(columns[43]);
                projectData.setActualduration(columns[44]);
                projectData.setProjectgoals(columns[45]);
                projectData.setProjectscope(columns[46]);
                projectData.setProjectconstraints(columns[47]);
                projectData.setProjectassumptions(columns[48]);
                projectData.setProjectbenefits(columns[49]);

                // Save the data to DynamoDB
                try {
                    projectTable.putItem(projectData); // This should work if the partition key is set
                    successCount++;
                } catch (Exception e) {
                    logger.error("Error saving project data: {}", e.getMessage());
                    failureCount++;
                }
            }

            logger.info("Import completed. Success: {}, Failures: {}", successCount, failureCount);
            System.out.println("Import completed successfully. Total successes: " + successCount + ", Total failures: " + failureCount);
        } catch (IOException e) {
            logger.error("Error reading CSV file: {}", e.getMessage());
        }
    }
}
