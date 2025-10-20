package com.example.redditdemo.service;

import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.User;
import com.example.redditdemo.model.Vote;
import com.example.redditdemo.model.VoteType;
import com.example.redditdemo.repository.PostRepository;
import com.example.redditdemo.repository.UserRepository;
import com.example.redditdemo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void vote(Long postId, String username, VoteType voteType) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vote existingVote = voteRepository.findByPostAndUser(post, user);

        if (existingVote != null) {
            // Toggle vote: remove if same, update if different
            if (existingVote.getVoteType() == voteType) {
                voteRepository.delete(existingVote);
            } else {
                existingVote.setVoteType(voteType);
                voteRepository.save(existingVote);
            }
        } else {
            Vote vote = new Vote();
            vote.setPost(post);
            vote.setUser(user);
            vote.setVoteType(voteType);
            voteRepository.save(vote);
        }

        // Optional: update a vote count field in Post if you add it
        int upvotes = voteRepository.countByPostAndVoteType(post, VoteType.UPVOTE);
        int downvotes = voteRepository.countByPostAndVoteType(post, VoteType.DOWNVOTE);
        post.setVoteCount(upvotes - downvotes);
        postRepository.save(post);
    }
}
