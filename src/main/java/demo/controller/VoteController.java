package demo.controller;


import demo.entities.Vote;
import demo.exceptions.BadRequestException;
import demo.exceptions.UserNotFoundException;
import demo.requests.AddVoteRequest;
import demo.response.VoteResponse;
import demo.services.UserService;
import demo.services.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/vote")
@Slf4j
@AllArgsConstructor
public class VoteController {

    @Autowired
    private final VoteService voteService;
    @Autowired
    private final UserService userService;


    @PostMapping("/add")
    public ResponseEntity<Vote> addVote(@RequestBody AddVoteRequest addVoteRequest) throws Exception{
        try {
            Vote vote  = voteService.addVote(addVoteRequest);
            return new ResponseEntity<>(vote, HttpStatus.CREATED);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException("User is not present");
        }catch (BadRequestException e){
            throw new BadRequestException("Invalid request");
        }catch(Exception e){
            throw new RuntimeException("Unknown exception");
        }
    }

    @GetMapping("/getVotes/{attributeId}")
    public ResponseEntity<VoteResponse> getVotes(@PathVariable UUID attributeId) throws Exception{
        try {
            return new ResponseEntity<>(voteService.getUsers(attributeId), HttpStatus.CREATED);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException("User is not present");
        }catch(Exception e){
            throw new RuntimeException("Unknown exception");
        }
    }
}
