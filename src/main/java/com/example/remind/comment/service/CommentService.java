package com.example.remind.comment.service;


import com.example.remind.auth.dto.AuthUser;
import com.example.remind.comment.dto.request.CommentRequestDto;
import com.example.remind.comment.dto.request.CommentUpdateRequestDto;
import com.example.remind.comment.dto.response.CommentResponseDto;
import com.example.remind.comment.entity.Comment;
import com.example.remind.comment.repository.CommentRepository;
import com.example.remind.common.config.PasswordEncoder;
import com.example.remind.post.entity.Post;
import com.example.remind.post.repository.PostRepository;
import com.example.remind.user.entity.User;
import com.example.remind.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public CommentResponseDto createComment(AuthUser authUser, Long postId, CommentRequestDto dto) {

        // 로그인 한 사람 정보
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new RuntimeException("해당 유저가 존재하지 않습니다.")
        );

        // 방문한 게시글
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("해당 포스트가 존재하지 않습니다.")
        );

        Comment comment = new Comment(LocalDateTime.now(), LocalDateTime.now(), dto.getComment(), user , post);
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getComment(),
                comment.getUser().getId(), comment.getPost().getId(),
                comment.getLikes(), comment.getCreatedAt(), comment.getUpdatedAt()
        );

    }

//     댓글 수정
    @Transactional
    public CommentResponseDto updateComment(AuthUser authUser, Long postId, Long commentId, CommentUpdateRequestDto dto) {

        // 게시글 존재 여부
        if (!postRepository.existsById(postId)) {
            throw new IllegalStateException("해당 게시물이 존재하지 않습니다.");
        }

        // 해당 댓글 존재 여부
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("해당 댓글이 존재하지 않습니다.")
        );

        if (!comment.getUser().getId().equals(authUser.getId())) {
            throw new IllegalStateException("본인이 아니므로 삭제할 수 없습니다.");
        }

        // 비밀번호 일치 확인 후 수정 가능
        if (!passwordEncoder.matches(dto.getPassword(), comment.getUser().getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        // 업데이트 후 저장
        comment.updateComment(dto.getComment());
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getComment(),
                comment.getUser().getId(), comment.getPost().getId(),
                comment.getLikes(), comment.getCreatedAt(), comment.getUpdatedAt()
        );
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, AuthUser authUser) {

        // 게시글 존재 여부
        if (!postRepository.existsById(postId)) {
            throw new IllegalStateException("해당 게시물이 존재하지 않습니다.");
        }

        // 댓글 존재 여부
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("해당 댓글이 존재하지 않습니다.")
        );

        // 댓글 작성자와 삭제 요청한 유저의 아이디 비교
        if (!comment.getUser().getId().equals(authUser.getId())) {
            throw new IllegalStateException("본인만 삭제 가능합니다.");
        }

        commentRepository.delete(comment);
    }
}
