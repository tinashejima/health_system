package com.health_project.server.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;


    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public UserEntity(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // --- Setters ---

    public void setId(String id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // --- Utility Methods ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        if (id != null) {
            return Objects.equals(id, user.id);
        } else {
            return Objects.equals(username, user.username);
        }
    }

    @Override
    public int hashCode() {
        // If ID is available, use it for hashing. Otherwise, use username.
        return id != null ? Objects.hash(id) : Objects.hash(username);
    }

    @Override
    public String toString() {
        return "UserEntity{" + // Class name in toString changed
                "id='" + id + '\'' +
                ", username='" + username + '\'' +

                '}';
    }


    public boolean validatePassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }
}
