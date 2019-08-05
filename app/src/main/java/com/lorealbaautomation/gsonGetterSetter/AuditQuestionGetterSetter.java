
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuditQuestionGetterSetter {

    @Expose
    private List<AuditQuestion> auditQuestion = null;

    public List<AuditQuestion> getAuditQuestion() {
        return auditQuestion;
    }

    public void setAuditQuestion(List<AuditQuestion> auditQuestion) {
        this.auditQuestion = auditQuestion;
    }

}
