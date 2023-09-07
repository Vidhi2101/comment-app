package demo.services;


import demo.entities.Post;
import demo.entities.User;
import demo.exceptions.PostNotFoundException;
import demo.exceptions.UserNotFoundException;
import demo.repositories.CommentRepository;
import demo.repositories.PostRepository;
import demo.repositories.UserRepository;
import demo.requests.CreatePostRequest;
import demo.response.GetPostResponse;
import demo.response.PostResponse;
import demo.response.mapper.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final CommentRepository commentRepository;

//    @Autowired
//    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
//        this.postRepository = postRepository;
//        this.userRepository = userRepository;
//        this.postMapper = postMapper;
//    }


    @Override
    public Post createPost(CreatePostRequest request)  {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return postRepository.save(request.toPost(request.getMeta(), user));

    }

    @Override
    public PostResponse getPostById(UUID id) throws Exception{
         Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
          return postMapper.mapToResponse(post, commentRepository.findByParentIdAndPostId(null, post.getId()));
    }


    public GetPostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir, UUID userId, boolean includeComment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAllByUserId(pageable, userId);
        List<PostResponse> listOfPosts = posts.getContent().stream()
                .map(e -> postMapper.mapToResponse(e, includeComment ? commentRepository.findByParentIdAndPostId(null,e.getId()) : Collections.emptyList())).collect(Collectors.toList());;

        return GetPostResponse.builder()
                .postList(listOfPosts)
                .pageNo(posts.getNumber())
                .pageSize(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast()).build();
    }
}

