package com.uysnon.codeanalyzer.core.rules;

import java.util.Objects;

public class ValidationResult {
    private boolean isNormal;
    private String description;

    public ValidationResult() {
    }

    public ValidationResult(boolean isNormal, String description) {
        this.isNormal = isNormal;
        this.description = description;
    }

    public boolean isNormal() {
        return isNormal;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidationResult)) return false;
        ValidationResult that = (ValidationResult) o;
        return isNormal == that.isNormal && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isNormal, description);
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "isNormal=" + isNormal +
                ", description='" + description + '\'' +
                '}';
    }
}
