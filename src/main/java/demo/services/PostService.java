package demo.services;



import demo.entities.Post;
import demo.requests.CreatePostRequest;
import demo.response.GetPostResponse;

import java.util.concurrent.CompletionStage;


public interface PostService {

    CompletionStage<Post> createPost(CreatePostRequest request) ;
    Post getPostById(Integer id) throws Exception;
    GetPostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir, Integer userId);
}
