package com.example.remind.comment.service;

import com.example.remind.auth.dto.AuthUser;
import com.example.remind.comment.entity.Comment;
import com.example.remind.comment.entity.CommentLike;
import com.example.remind.comment.repository.CommentLikeRepository;
import com.example.remind.comment.repository.CommentRepository;
import com.example.remind.post.repository.PostRepository;
import com.example.remind.user.entity.User;
import com.example.remind.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    // 댓글에 좋아요 추가
    @Transactional
    public void commentLike(AuthUser authUser,Long postId, Long commentId) {

        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("해당 유저가 없습니다.")
        );

        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("해당 댓글이 존재하지 않습니다.")
        );

        // 작성자와 로그인한 사람 일치 확인
        if (comment.getUser().equals(user)) {
            throw new IllegalArgumentException("자기 댓글에 좋아요를 남길 수 없습니다.");
        }

        // 좋아요는 한 번만 가능
        if (commentLikeRepository.existsByUserAndComment(user, comment)) {
            throw new IllegalStateException("좋아요는 한 번만 가능합니다.");
        }

        CommentLike commentLike = new CommentLike(user, comment);
        commentLikeRepository.save(commentLike);

        // 해당 댓글에 유저 수로 좋아요 수 지정
        Integer commentLikeCount = commentLikeRepository.countByComment(comment);
        comment.updateCommentLikes(commentLikeCount);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteCommentLike(AuthUser authUser, Long postId, Long commentId) {

        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalStateException("해당 유저가 존재하지 않습니다.")
        );

        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("해당 댓글이 존재하지 않습니다.")
        );

        CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment).orElseThrow(
                () -> new IllegalStateException("좋아요 누른 적 없어서 취소할 수 없습니다.")
        );

        commentLikeRepository.delete(commentLike);

        // 좋아요 취소 후 좋아요 수 반영~
        Integer likeCount = commentLikeRepository.countByComment(comment);
        comment.updateCommentLikes(likeCount);
        commentRepository.save(comment);
    }
}
