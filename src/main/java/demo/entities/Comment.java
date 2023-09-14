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

@Table(name = "comment")
@Entity
@Setter
@Getter
@ToString
public class Comment {

    @Column(nullable = false, name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Column(name = "parent_id")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID parentId;

    @Column(length = 200, nullable = false)
    private String metadata;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_id",nullable = false, referencedColumnName = "id")
    private Post post;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;


    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
