package com.uysnon.codeanalyzer.core.rules;

import java.util.Objects;

public class RuleStructure{
    private int importance;
    private String value;
    private RuleType type;

    public RuleStructure() {
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleStructure)) return false;
        RuleStructure that = (RuleStructure) o;
        return importance == that.importance && Objects.equals(value, that.value) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(importance, value, type);
    }

    @Override
    public String toString() {
        return "RuleStructure{" +
                "importance=" + importance +
                ", value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
