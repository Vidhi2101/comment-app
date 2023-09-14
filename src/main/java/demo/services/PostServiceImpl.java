package demo.services;


import demo.AppConstants;
import demo.entities.Post;
import demo.entities.User;
import demo.exceptions.BadRequestException;
import demo.exceptions.PostNotFoundException;
import demo.exceptions.UserNotFoundException;
import demo.model.response.PostResponse;
import demo.repositories.CommentRepository;
import demo.repositories.PostRepository;
import demo.repositories.UserRepository;
import demo.model.request.CreatePostRequest;
import demo.model.response.GetPaginatedPostResponse;
import demo.model.response.GetPostResponse;
import demo.model.response.mapper.PostMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final CommentRepository commentRepository;


    @Override
    public PostResponse createPost(CreatePostRequest request)  {
        validateRequest(request);
        User user = userRepository.findById(convertToUUID(request.getUserId()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return postMapper.mapToResponse(postRepository.save(request.toPost(request.getMeta(), user)));

    }

    @Override
    public GetPostResponse getPostById(String id, boolean includeComment) throws Exception{
        Post post = postRepository.findById(convertToUUID(id)).orElseThrow(() -> new PostNotFoundException("Post not found "));
        Pageable commentPage =  PageRequest.of(AppConstants.DEFAULT_PAGE_NUMBER, AppConstants.DEFAULT_PAGE_SIZE,Sort.by(AppConstants.DEFAULT_SORT_BY).descending());
        return postMapper.mapToResponse(post, includeComment ? commentRepository.findByParentIdAndPostId(commentPage, null, post.getId()).getContent() : Collections.emptyList());
    }


    public GetPaginatedPostResponse getAllPosts(int pageNo, int pageSize, String sortDir, String userId, boolean includeComment) {
        User user = userRepository.findById(convertToUUID(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(AppConstants.DEFAULT_SORT_BY).ascending()
                : Sort.by(AppConstants.DEFAULT_SORT_BY).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
        Pageable commentPage =  PageRequest.of(AppConstants.DEFAULT_PAGE_NUMBER, AppConstants.DEFAULT_PAGE_SIZE,sort);

        Page<Post>posts = postRepository.findPostsByUserId(user.getId(), pageable);
        List<GetPostResponse> listOfPosts = posts.getContent().stream()
                .map(e -> postMapper.mapToResponse(e, includeComment ? commentRepository.findByParentIdAndPostId(commentPage, null,e.getId()).getContent() : Collections.emptyList())).collect(Collectors.toList());;

        return GetPaginatedPostResponse.builder()
                .postList(listOfPosts)
                .pageNo(posts.getNumber())
                .pageSize(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast()).build();
    }

    private UUID convertToUUID(String id){
        try {
            return AppConstants.convertToUUID(id);
        }catch (IllegalArgumentException ex){
            throw new BadRequestException("Parameter is incorrect");
        }
        catch (Exception e){
            throw  new RuntimeException();
        }
    }

    private void validateRequest(CreatePostRequest request){
        if(request.getUserId() == null)
            throw new BadRequestException("UserId cannot be null");
    }
}

