package demo.services;

import demo.entities.User;
import demo.entities.Vote;
import demo.entities.VoteType;
import demo.exceptions.BadRequestException;
import demo.exceptions.UserNotFoundException;
import demo.repositories.CommentRepository;
import demo.repositories.PostRepository;
import demo.repositories.UserRepository;
import demo.repositories.VoteRepository;
import demo.requests.AddVoteRequest;
import demo.response.VoteResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {
    @Autowired
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    @Override
    public Vote addVote(AddVoteRequest request) {
        if(!commentRepository.existsById(request.getAttributeId()) && !postRepository.existsById(request.getAttributeId()))
            throw new BadRequestException("One of Either post id or comment id should be present");
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    // TODO: check here if this post or comment belongs to the user which try to give the vote.
        return voteRepository.save(request.toVote(request.getAttributeId(), request.getVoteType(), user));

    }


    @Override
    public VoteResponse getUsers(UUID attributeId){
        List<Vote> votes =  voteRepository.findByAttributeId(attributeId);
        List<String> likeUsers = votes.stream().filter(e -> VoteType.LIKE.voteType.equals(e.getVoteType()))
                .map(a -> a.getUser().getUserName()).collect(Collectors.toList());
        List<String> disLikeUsers = votes.stream().filter(e -> VoteType.DISLIKE.voteType.equals(e.getVoteType()))
                .map(a -> a.getUser().getUserName()).collect(Collectors.toList());
        return new VoteResponse(likeUsers, disLikeUsers);
    }


}
