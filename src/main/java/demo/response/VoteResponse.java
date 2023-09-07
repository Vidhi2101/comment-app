package demo.response;

import demo.entities.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class VoteResponse {
    private Map<VoteType, List<String>> voteUsersList;

    public VoteResponse(List<String> likeUsers, List<String> dislikeUsers) {
        voteUsersList = new HashMap<>();
        voteUsersList.put(VoteType.LIKE, likeUsers);
        voteUsersList.put(VoteType.DISLIKE, dislikeUsers);
    }
}
