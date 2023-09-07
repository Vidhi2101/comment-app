package demo.services;



import demo.entities.Post;
import demo.requests.CreatePostRequest;
import demo.response.GetPostResponse;
import demo.response.PostResponse;

import java.util.UUID;
import java.util.concurrent.CompletionStage;


public interface PostService {

    Post createPost(CreatePostRequest request) ;
    PostResponse getPostById(UUID id) throws Exception;
    GetPostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir, UUID userId, boolean includeComment);
}
