package demo.repositories;

import demo.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByAttributeId(UUID attributeId);

    Boolean existsByAttributeIdAndUserId(UUID attributeId, UUID userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Vote v set v.voteType =:voteType where v.attributeId =:attributeId and v.user.id =:userId")
    int update(@Param("voteType") int voteType, @Param("attributeId") UUID attributeId, @Param("userId") UUID userId);
}
