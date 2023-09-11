package demo.services;

import demo.AppConstants;
import demo.entities.User;
import demo.entities.Vote;
import demo.entities.VoteType;
import demo.exceptions.BadRequestException;
import demo.exceptions.UserNotFoundException;
import demo.repositories.CommentRepository;
import demo.repositories.PostRepository;
import demo.repositories.UserRepository;
import demo.repositories.VoteRepository;
import demo.model.request.AddVoteRequest;
import demo.model.response.VoteResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        if(request.getAttributeId() == null) throw new BadRequestException("Request should contain attribute id");
        Optional<VoteType> byCode = VoteType.findByCode(request.getVoteType());
        if(!byCode.isPresent()){
            throw new BadRequestException("Vote type is incorrect");
        }
        UUID attributeId = convertToUUID(request.getAttributeId());
        UUID userId = convertToUUID(request.getUserId());
        if(!commentRepository.existsById(attributeId) && !postRepository.existsById(attributeId))
            throw new BadRequestException("One of Either post id or comment id should be present");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

    // TODO: check here if this post or comment belongs to the user which try to give the vote.
        if(!voteRepository.existsByAttributeIdAndUserId(attributeId, userId)) {
            return voteRepository.save(request.toVote(attributeId, request.getVoteType(), user));
        }else {
            voteRepository.update(request.getVoteType(), attributeId, user.getId());
            return new Vote();
        }

    }

    private UUID convertToUUID(String id){
        try {
            return AppConstants.convertToUUID(id);
        }catch (IllegalArgumentException ex){
            throw new BadRequestException("Parameter is incorrect");
        }
    }


    @Override
    public VoteResponse getUsers(String attributeId){
        List<Vote> votes =  voteRepository.findByAttributeId(AppConstants.convertToUUID(attributeId));
        List<String> likeUsers = votes.stream().filter(e -> VoteType.LIKE.voteType.equals(e.getVoteType()))
                .map(a -> a.getUser().getUserName()).collect(Collectors.toList());
        List<String> disLikeUsers = votes.stream().filter(e -> VoteType.DISLIKE.voteType.equals(e.getVoteType()))
                .map(a -> a.getUser().getUserName()).collect(Collectors.toList());
        return new VoteResponse(likeUsers, disLikeUsers);
    }


}
