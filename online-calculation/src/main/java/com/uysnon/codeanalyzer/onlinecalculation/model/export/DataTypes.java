package com.uysnon.codeanalyzer.onlinecalculation.model.export;

public enum DataTypes {
    INTEGER("integer"),
    DOUBLE("double"),
    PERCENT_DOUBLE("percent_double")
    ;
    String code;

    DataTypes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
