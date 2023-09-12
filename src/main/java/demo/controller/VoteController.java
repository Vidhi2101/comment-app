package demo.controller;


import demo.entities.Vote;
import demo.entities.VoteType;
import demo.exceptions.BadRequestException;
import demo.exceptions.UserNotFoundException;
import demo.model.request.AddVoteRequest;
import demo.services.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/vote")
@Slf4j
@AllArgsConstructor
public class VoteController {

    @Autowired
    private final VoteService voteService;


    @PostMapping("/add")
    public ResponseEntity<?> addVote(@RequestBody AddVoteRequest addVoteRequest) throws Exception{
        try {
            Vote vote  = voteService.addVote(addVoteRequest);
            return new ResponseEntity<>(VoteType.findByCode(addVoteRequest.getVoteType()), HttpStatus.CREATED);
        }catch (UserNotFoundException  | BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/getVotes/{attributeId}")
    public ResponseEntity<?> getVotes(@PathVariable String attributeId) throws Exception{
        try {
            return new ResponseEntity<>(voteService.getUsers(attributeId), HttpStatus.CREATED);
        }catch (UserNotFoundException | BadRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
