package demo.services;


import demo.entities.Post;
import demo.entities.User;
import demo.exceptions.PostNotFoundException;
import demo.exceptions.UserNotFoundException;
import demo.repositories.PostRepository;
import demo.repositories.UserRepository;
import demo.requests.CreatePostRequest;
import demo.response.GetPostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;

    }

    @Override
    public CompletionStage<Post> createPost(CreatePostRequest request)  {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return CompletableFuture.completedFuture(postRepository.save(request.toPost(request.getMeta(), user)));

    }

    @Override
    public Post getPostById(Integer id) throws Exception{
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
    }

//    public CompletionStage<List<Post>> getPostsByUserId(Integer userId){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
//        return CompletableFuture.completedFuture(postRepository.findAllByUserId(userId));
//    }


    public GetPostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAllByUserId(pageable, userId);

        List<Post> listOfPosts = posts.getContent();

        return GetPostResponse.builder()
                .posts(listOfPosts)
                .pageNo(posts.getNumber())
                .pageSize(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast()).build();
    }
}

