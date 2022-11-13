package com.arslan;

public class Evaluations {
    private  int evaluationId;

    Evaluations(int evaluationId){
        this.setEvaluationId(evaluationId);
    }

    private void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public int getEvaluationId() {
        return evaluationId;
    }
}
