package model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    public enum ROLE {
        cashier, superCashier, expert, guest
    }

    private int id;

    private String login;
    private String password;
    private ROLE role;

    private String firstName;
    private String secondName;
    private String code;

    private List<Check> allChecks = new ArrayList<>();


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public ROLE getRole() {
        if (role == null) {
            return ROLE.guest;
        }
        return role;
    }
    public void setRole(ROLE role) { this.role = role; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSecondName() { return secondName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public List<Check> getAllChecks() { return allChecks; }
    public void setAllChecks(List<Check> allChecks) { this.allChecks = allChecks; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
