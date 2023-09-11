package demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

@Table(name = "vote")
@Entity
@Setter
@Getter
@ToString
public class Vote {

    @Column(name = "attribute_id")
    @JdbcTypeCode(Types.VARCHAR)
    @Id
    @Formula("(CASE WHEN post_id IS NOT NULL THEN post_id ELSE comment_id END)")
    private UUID attributeId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private int voteType;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

}
