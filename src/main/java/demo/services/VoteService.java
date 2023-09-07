package demo.services;

import demo.entities.Post;
import demo.entities.User;
import demo.entities.Vote;
import demo.entities.VoteType;
import demo.repositories.VoteRepository;
import demo.requests.AddVoteRequest;
import demo.requests.CreatePostRequest;
import demo.response.VoteResponse;

import java.util.List;
import java.util.UUID;

public interface VoteService {

    Vote addVote(AddVoteRequest request) ;
    VoteResponse getUsers(UUID attributeId);

}
