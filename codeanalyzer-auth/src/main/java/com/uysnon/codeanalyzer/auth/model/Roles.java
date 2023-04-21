package com.uysnon.codeanalyzer.auth.model;

public enum Roles {
    USER("user"), ADMIN("admin");

    private String authority;

    Roles(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
