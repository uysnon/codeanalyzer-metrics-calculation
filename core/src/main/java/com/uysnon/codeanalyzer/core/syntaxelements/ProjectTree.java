package com.uysnon.codeanalyzer.core.syntaxelements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectTree {
    private List<SourceCodeUnit> sourceCodeUnits;

    public ProjectTree() {
        sourceCodeUnits = new ArrayList<>();
    }

    public List<SourceCodeUnit> getSourceCodeUnits() {
        return sourceCodeUnits;
    }

    public void setSourceCodeUnits(List<SourceCodeUnit> sourceCodeUnits) {
        this.sourceCodeUnits = sourceCodeUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectTree)) return false;
        ProjectTree that = (ProjectTree) o;
        return Objects.equals(sourceCodeUnits, that.sourceCodeUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceCodeUnits);
    }

    @Override
    public String toString() {
        return "ProjectTree{" +
                "sourceCodeUnits=" + sourceCodeUnits +
                '}';
    }
}
