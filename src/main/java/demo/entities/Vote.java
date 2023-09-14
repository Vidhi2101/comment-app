package demo.entities;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

@Table(name = "vote")
@Entity
@Setter
@Getter
@ToString
public class Vote {

    @Column(nullable = false, name = "id")
    @GeneratedValue
    @Id
    private Long id;


    @Column(name = "attribute_id", nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    @Formula("(CASE WHEN post_id IS NOT NULL THEN post_id ELSE comment_id END)")
    private UUID attributeId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable= false, referencedColumnName = "id")
    private User user;

    @Column(name = "vote_type", nullable = false)
    private int voteType;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

}
