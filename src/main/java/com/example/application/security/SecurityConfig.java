
package com.example.application.security;

import com.example.application.data.entity.UserRole;
import com.example.application.databaseactions.dbConnect;
import com.example.application.views.loginview.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/images/*.png").permitAll();
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsService users() {


        List<UserDetails> users = new ArrayList<UserDetails>();
        Connection conn = dbConnect.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT username, password, user_role FROM USERS";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                UserDetails user = User.builder()
                        .username(rs.getString("username"))
                        .password("{noop}" +rs.getString("password"))
                        .roles("USER")
                        .build();

                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UserDetails user = User.builder()
                .username("user")
                // password = password with this hash, don't tell anybody :-)
                .password("{noop}password")
                .roles("USER")
                .build();
      /*  UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER", "ADMIN")
                .build();

       */
        users.add(user);
        return new InMemoryUserDetailsManager(users);
    }
}