/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.carlosortiz.workqueue.core.workqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Carlos
 */
public class JobRequest {
    private String jobId;
    private String jobCode;
    private String user;
    private List<JobParam> params;

    private JobRequest( String code, String user, List<JobParam> params) {

        if (code==null || code.isEmpty() ) {
            throw new IllegalArgumentException("JobCode is null or empty");
        }

        if (user==null || user.isEmpty() ) {
            throw new IllegalArgumentException("User is null or empty");
        }

        this.jobId = UUID.randomUUID().toString();
        this.jobCode = code;
        this.user = user;
        this.params = params;
    }

    public void setJobAggregationId(String aggregationId) {
        if (aggregationId==null || aggregationId.isEmpty()) {
            throw new IllegalArgumentException("AggregationId is null or empty");
        }
        this.jobId = aggregationId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getUser() {
        return user;
    }

    public String getJobId() {
        return jobId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public List<JobParam> getParams() {
        return params;
    }

    public static class Builder {
        private String jobId;
        private String jobCode;
        private String user;
        private List<JobParam> params;

        Builder() {
            params = new ArrayList<>();
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder jobCode(String jobCode) {
            this.jobCode = jobCode;
            return this;
        }

        public Builder addJobParam(String name,String value) {
            params.add(new JobParam(name,value));
            return this;
        }

        public JobRequest create() {
           return new JobRequest(jobCode,user,params);
        }

    }

}
