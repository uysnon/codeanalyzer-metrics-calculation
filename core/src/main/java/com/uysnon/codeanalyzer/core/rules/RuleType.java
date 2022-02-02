package com.uysnon.codeanalyzer.core.rules;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Objects;

public class RuleType {
    private String title;
    @JsonProperty("scope")
    @JsonAlias({"scope"})
    private List<Scopes> scopes;

    public RuleType() {
    }

    public RuleType(String name, List<Scopes> scopes) {
        this.title = name;
        this.scopes = scopes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Scopes> getScopes() {
        return scopes;
    }

    public void setScopes(List<Scopes> scopes) {
        this.scopes = scopes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RuleType)) return false;
        RuleType ruleType = (RuleType) o;
        return Objects.equals(title, ruleType.title) && Objects.equals(scopes, ruleType.scopes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, scopes);
    }

    @Override
    public String toString() {
        return "RuleType{" +
                "name='" + title + '\'' +
                ", scopes=" + scopes +
                '}';
    }
}
