package dev.peacechan.performance_testing.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@NoArgsConstructor
@DynamoDbBean
public class ProjectData {

    @Setter
    private String projectId; // This will be the sort key
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
    private String projectdescription;
    private String projectowner;
    private String projectmanager;
    private String budget;
    private String actualcost;
    private String estimatedcost;
    private String currency;
    private String fundingsource;
    private String projectphase;
    private String prioritylevel;
    private String location;
    private String clientname;
    private String clientcontact;
    private String risklevel;
    private String riskdescription;
    private String riskmitigation;
    private String stakeholderlist;
    private String milestonelist;
    private String tasklist;
    private String issuelog;
    private String approvalstatus;
    private String approvaldate;
    private String notes;
    private String projecttype;
    private String resourceallocation;
    private String progresspercentage;
    private String comments;
    private String estimatedduration;
    private String actualduration;
    private String projectgoals;
    private String projectscope;
    private String projectconstraints;
    private String projectassumptions;
    private String projectbenefits;

    // This is the partition key
    @DynamoDbPartitionKey
    public String getPK() {
        return "PROJECT"; // Static value for partition key
    }

    // This is the sort key
    @DynamoDbSortKey
    public String getProjectId() {
        return projectId;
    }
}
