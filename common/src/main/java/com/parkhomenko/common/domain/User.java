package com.parkhomenko.common.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.parkhomenko.common.domain.util.View;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author Dmytro Parkhomenko
 * Created on 19.07.16.
 */

public abstract class User implements Serializable {

    private Long id;
    private int version;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String login;
    private String password;
    private Set<Role> roles;
    private Boolean blocked;

    public User() {
    }

    @JsonView(View.Summary.class)
    @DocumentId
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(View.Summary.class)
    @Field
    @Analyzer(definition = "name_login_analyzer")
    public String getName() {
        String result = firstname;

        if(Objects.nonNull(lastname)) {
            result += ' ' + lastname;
        }

        return result;
    }

    public void setName(String name) {
        StringTokenizer t = new StringTokenizer(name);

        firstname = t.nextToken();

        try {
            lastname = t.nextToken();
        } catch (NoSuchElementException exp) {
            lastname = null;
        }
    }

    int getVersion() {
        return version;
    }

    void setVersion(int version) {
        this.version = version;
    }

    @JsonView(View.Summary.class)
    @Field
    @Analyzer(definition = "email_analyzer")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonView(View.Summary.class)
    @Field
    @Analyzer(definition = "phone_analyzer")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonView(View.Summary.class)
    @Field
    @Analyzer(definition = "name_login_analyzer")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(View.UserDetails.class)
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @JsonView(View.Summary.class)
    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return login != null ? login.equals(user.login) : user.login == null;

    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", version=" + version +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", blocked=" + blocked +
                '}';
    }
}
