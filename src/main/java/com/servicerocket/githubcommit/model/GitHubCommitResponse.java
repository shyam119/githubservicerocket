package com.servicerocket.githubcommit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitHubCommitResponse {
    private String totalRowCount;
    private List<String> interestingComments;
    private String errorMessage;
}
