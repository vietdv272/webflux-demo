package com.demo.dao.enity;


public class RuleResult {

    public int ruleID;
    public boolean result;
    public int errorCode;

    //Getters and setters


    public RuleResult() {
    }

    public RuleResult(int ruleID, boolean result, int errorCode) {
        this.ruleID = ruleID;
        this.result = result;
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "Employee [ruleID=" + ruleID + ", result=" + result + ", errorCode=" + errorCode + "]";
    }
}
