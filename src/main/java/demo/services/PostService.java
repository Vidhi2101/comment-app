package demo.services;



import demo.entities.Post;
import demo.model.request.CreatePostRequest;
import demo.model.response.GetPaginatedPostResponse;
import demo.model.response.GetPostResponse;
import demo.model.response.PostResponse;


public interface PostService {

    PostResponse createPost(CreatePostRequest request) ;
    GetPostResponse getPostById(String id) throws Exception;
    GetPaginatedPostResponse getAllPosts(int pageNo, int pageSize, String sortDir, String userId, boolean includeComment);
}
