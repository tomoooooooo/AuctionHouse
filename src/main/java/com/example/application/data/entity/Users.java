package com.example.application.data.entity;

import com.example.application.databaseactions.dbConnect;
import com.vaadin.flow.component.notification.Notification;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Users{

    @Id
    @SequenceGenerator(name = "useridgenerator", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "useridgenerator")
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String username;
    @Size(min = 4, max = 64, message = "Password must be 4-64 char long")
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public Users(String firstName, String lastName, String email, String username, String password, UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }



    public void addToDb() throws SQLException {
        try {
            Connection conn = dbConnect.connect();
            PreparedStatement ps = null;
            String sql = "INSERT INTO USERS (id, first_name, last_name, email, username, password, user_role) VALUES(?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, 100);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.setString(5, username);
            ps.setString(6, password);
            ps.setString(7, userRole.toString());
            ps.execute();
            ps.close();
            conn.close();
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
