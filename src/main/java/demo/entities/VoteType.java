package demo.entities;

import java.util.Arrays;
import java.util.Optional;

public enum VoteType {
    LIKE(1),
    DISLIKE(-1),

    UNLIKE(0);

    public Integer voteType;
    VoteType(Integer i) {
        this.voteType = i;
    }


    public static Optional<VoteType> findByCode(int voteCode) {
        return Arrays.stream(values()).filter(v -> v.voteType == voteCode).findFirst();
    }
}
