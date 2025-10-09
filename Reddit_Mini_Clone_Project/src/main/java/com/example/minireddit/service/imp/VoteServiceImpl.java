package com.example.minireddit.service.imp;

import com.example.minireddit.model.Post;
import com.example.minireddit.model.User;
import com.example.minireddit.model.Vote;
import com.example.minireddit.repository.VoteRepository;
import com.example.minireddit.service.VoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository repo;

    public VoteServiceImpl(VoteRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public void vote(User user, Post post, int value) {
        // value should be +1 for upvote, -1 for downvote
        if (value != 1 && value != -1) {
            throw new IllegalArgumentException("Vote value must be 1 or -1");
        }

        boolean isUpvote = value == 1;

        repo.findByUserAndPost(user, post).ifPresentOrElse(existing -> {
            // If same vote again → remove (toggle off)
            if (existing.isUpvote() == isUpvote) {
                repo.delete(existing);
            } else {
                existing.setUpvote(isUpvote);
                repo.save(existing);
            }
        }, () -> {
            Vote newVote = new Vote(user, post, isUpvote);
            repo.save(newVote);
        });
    }

    @Override
    public long countUpvotes(Post post) {
        return repo.countByPostAndUpvoteTrue(post);
    }

    @Override
    public long countDownvotes(Post post) {
        return repo.countByPostAndUpvoteFalse(post);
    }
}
