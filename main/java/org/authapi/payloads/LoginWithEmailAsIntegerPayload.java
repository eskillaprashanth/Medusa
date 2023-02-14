package org.authapi.payloads;

public class LoginWithEmailAsIntegerPayload {
    private Integer email;

    @Override
    public String toString() {
        return "LoginWithEmailAsIntegerPayload{" +
                "email=" + email +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getEmail() {
        return email;
    }

    public void setEmail(Integer email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
}
