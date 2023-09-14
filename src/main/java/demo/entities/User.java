package demo.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

@Table(name = "user")
@Entity
@Setter
@Getter
@ToString
public class User {

    @Column(nullable = false, name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Column(unique = true, length = 200, nullable = false, name = "user_name")
    private String userName;

    @Column(unique = true, length = 200)
    private String mail;

    @Column(length = 200)
    private String password;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "logged_in_at")
    private Date loggedInAt;
}
