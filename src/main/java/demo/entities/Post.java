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

@Table(name = "post")
@Entity
@Setter
@Getter
@ToString
public class Post {
    @Column(nullable = false, name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Column(length = 1000, nullable = false, name = "metadata")
    private String metaData;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",nullable = false, referencedColumnName = "id")
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column
    private Date updatedAt;
}
