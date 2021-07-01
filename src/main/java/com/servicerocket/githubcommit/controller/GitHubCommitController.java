package com.servicerocket.githubcommit.controller;

import com.servicerocket.githubcommit.model.GitHubCommitResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/servicerocket")
@Slf4j
public class GitHubCommitController {

    @GetMapping(path = "/commits")
    public GitHubCommitResponse executeCommitsScript(
            @RequestParam(value = "owner") String owner,
            @RequestParam(value = "repo") String repo,
            @RequestParam(value = "per_page", required = false) String per_page,
            @RequestParam(value = "page", required = false) String page
            ){
        log.info("executeCommitsScript() with params {},{} ",owner,repo);
        if(owner==null || repo == null || owner.isEmpty() || repo.isEmpty()){
            return GitHubCommitResponse.builder().errorMessage("owner and repo params should not be empty").build();
        }
        Process process = null;
        List<String> interestingComments = new ArrayList<String>();
        List<String> totalCount = new ArrayList<String>();
        String totalRowCount = null;
        try{
            String scriptPath = ClassLoader.getSystemClassLoader().getResource("scripts/GitHubFetchScript.py").getFile();
            log.info("scripts absolute path : {}",scriptPath);
            String[] cmd = {"python",scriptPath,owner,repo,per_page,page};
            //calling python script GitHubFetchScript.py
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            br.lines().forEach(line -> {
                if(line.contains("README")){
                    interestingComments.add(line);
                }
                else if(line.contains("TotColCnt")){
                    totalCount.add(line);
                }
            });
            if(totalCount.size() > 0){
                String[] count = totalCount.get(0).split(":");
                totalRowCount = count[1];
            }
        }catch(Exception ex){
            log.info("Exception occurred while calling script {}",ex.getCause());
            return GitHubCommitResponse.builder().errorMessage("Exception occurred while calling Python Script").build();
        }
        return GitHubCommitResponse.builder().totalRowCount(totalRowCount).interestingComments(interestingComments).build();
    }

}
