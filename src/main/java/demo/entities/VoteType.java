package demo.entities;

public enum VoteType {
    LIKE(1),
    DISLIKE(-1);

    public Integer voteType;
    VoteType(Integer i) {
        this.voteType = i;
    }
}
