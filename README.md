# comment-app

## Compile
Maven clean install 


## Tables Created
1. Post 
2. Comment
3. User


## Run
intellij Run 

## HLD
https://docs.google.com/document/d/1_2vrJEBDlRY3_iIjiHsq2U9GPhINC_a0u6f4nol6qB4/edit?usp=sharing
**Problem Statement:**
Design a comments service for a social media website which can support scalable levels
of nesting. 
A user can create a post.
A user can comment on a post.
A user can reply to a comment.
A user can like/dislike the comment/post.

**Functional Requirement:**
**User**: A user should be able to register. 
**Post**:
A user can create a post with some metadata. 
A user can also like/dislike or comment on a post. 
He can also view the post which he created.
 Latest post should be  visible first. With pagination enabled.
**Comment**:
A user can comment on a post or can reply to a comment as well. 
Nested levels of replies can be viewed. 
A user can also like/dislike the comment. 
He should be able to view all comments to any post. 
If a user clicks on “View More comments”, next x comments should be visible.
If a user clicks on “View More replies”, the next n level of comments should be visible.
Latest comment/reply should be visible first with pagination enable
Vote: A user can see all the users who have liked/disliked the post/comment.
Non-Functional Requirements:

Scalability: The system should scale seamlessly to accommodate a growing user base.
Reliability: The platform should offer reliable performance under different conditions.
Availability: Continuous accessibility is crucial to prevent potential business losses.
Consistency: The system should ensure eventual consistency.
Serviceability: The system should be simple to manage, maintain, and recover from failures. 
Disaster Recovery: The system should have backup and redundancy strategies in place for disaster recovery.
Performance Monitoring: The system should have a mechanism to monitor performance and metrics.

Assumptions:
A user authentication and login criteria will be there to validate user requests.
For now, A user can see any other user’s post and comments as well. 
A user is not allowed to edit/delete a post/comment.
Timezone Handling: The system can handle different time zones.
Localization: The system is already equipped to support multiple languages.
Security: A robust system to protect user data is assumed to be in place.
Rate Limiting: Safeguards against API misuse are in place.
Future scopes:
A user can edit/delete a post or comment.
You can tag a person to a post/comment.
While showing comments, @mentions should come on top.
Safeguard against post visibility. A user is not allowed to view other people's posts.
Create a user's followers kind of thing.
More voteType like more reactions can be added.

BAck of the envelope estimations:
User: In our estimates, we assume a user base of 1 million daily active users with a year-on-year growth rate of 5%. 
Post/Comment: Each DAU on average can do 5 posts or comments per day. And can read 40 reads per day .
Vote: Each DAU on average do 20 likes/dislikes per day.

	Traffic estimate:
Read operation:  
Post/comment: 1 million users * 50 reads = 50 million reads/day
Likes/dislikes = 1 million users * 10 reads = 10 million reads/day
Total Reads = (50 + 10) = 60 million reads/day
Write operation: 
For each new user registration, let's say 100 new users per day
For each new comment/post = 1 million users * 5 writes = 5 million writes/day
For each like/dislike = 1 million users* 20 vote/user/day = 20 million writes/day
Total Writes = (5 + 20) = 25 million writes/day


Database estimation:
User
Assume each user data with basic information takes 100 KB per user
Initial Storage : 1 million users* 100KB = 100GB
Additional storage for 5 years with 5% growth = 5%* 1 million * 5 years * 100KB ~ 1TB
5 year storage = 100GB + 1 TB = 1.1 TB
Vote: 
Assume each vote action takes 1 KB of storage
Initial Storage: 1 million users* 20 actions per user * 1KB = 20 GB per day
5 years storage = 40 GB/day * 365 * 5 ~= 73TB
Post/Comment: 
Assume each post/comment takes. 10KB per post/comment
Initial storage: 1 million users* 5 comment/post * 10 KB = 50 GB/day
5 years storage = 50 GB/day * 365 * 5 years = 92TB

Total storage required in 5 years = (1 TB + 73TB + 92 TB) ~= 166 TB

High level Architecture:



Load Balancer: The system begins with a load balancer that distributes incoming network traffic across multiple servers to prevent any one server from becoming a bottleneck, thus improving responsiveness and availability of applications.

Web Servers and Application Layer(CommentService):The servers process these requests (made via the APIs) and return appropriate responses. Multiple web servers are maintained for redundancy and to handle heavy loads. This layer also contains our business logic. It processes the incoming requests, and interacts with the database and provides required responses.

Caching Layer: To speed up read operations for frequently-accessed but rarely-modified data, a caching layer using a technology like Redis is used.

Background Job Queue: Some tasks like analytic use case, syncing data with multiple devices or sending notifications to drivers are handled asynchronously using a job queue.

CDN: For posts which contain heavy images/videos posted can be stored in geographically located servers to reduce server load and improve content delivery.

SQL Database (MySQL): This is the primary database where we'll store user,post,comment data. We definitely need multiple database servers with sharding algorithms and for a few cases NOSQL databases can also be used to handle the load.

Central Logging and Metrics Collection: All logs from the various components are sent to a centralized logging system, which helps in debugging and tracking system activity. A system like Prometheus is used for collecting metrics from the various components, which helps in monitoring system health and making scaling decisions.


Data Sharding logic:
As a huge amount of data storage is required, we would require multiple servers to store the data.
A potential sharding key could be postId. All the comment, replies will go on the same shard, improving data locality. 
Another choice could be a mix of userId_postId




## LLD

TODO://
