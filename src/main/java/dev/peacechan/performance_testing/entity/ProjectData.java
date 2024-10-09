package dev.peacechan.performance_testing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@NoArgsConstructor
@DynamoDbBean
public class ProjectData {

    @Setter
    private String projectId;
    private String projectCategory;
    private String projectPn2Id;
    private String name;
    private String logInCompanyNumber;
    private String createdBy;
    private String createdAt;
    private String lastUpdatedBy;
    private String lastUpdatedAt;
    private String startDateToApply;
    private String endDateToApply;
    private String status;
    private String notificationEmailAddress1;
    private String notificationEmailAddress2;
    private String notificationEmailAddress3;
    private String notificationEmailAddress4;
    private String projectDescription;
    private String projectOwner;
    private String projectManager;
    private Double budget;
    private Double actualCost;
    private Double estimatedCost;
    private String currency;
    private String fundingSource;
    private String projectPhase;
    private String priorityLevel;
    private String location;
    private String clientName;
    private String clientContact;
    private String riskLevel;
    private String riskDescription;
    private String riskMitigation;
    private String stakeholderList;
    private String milestoneList;
    private String taskList;
    private String issueLog;
    private String approvalStatus;
    private String approvalDate;
    private String notes;
    private String projectType;
    private String resourceAllocation;

    private Double progressPercentage;
    private String comments;
    private String estimatedDuration;
    private String actualDuration;
    private String projectGoals;
    private String projectScope;
    private String projectConstraints;
    private String projectAssumptions;
    private String projectBenefits;

    @DynamoDbPartitionKey
    public String getProjectId() {
        return projectId;
    }

}
