package com.example.blog_api.service;

import com.example.blog_api.dto.comment.CommentCreateDTO;
import com.example.blog_api.dto.comment.CommentDTO;
import com.example.blog_api.dto.comment.CommentUpdateDTO;
import com.example.blog_api.entity.Comment;
import com.example.blog_api.entity.Post;
import com.example.blog_api.entity.User;
import com.example.blog_api.exception.ApiException;
import com.example.blog_api.exception.ResourceNotFoundException;
import com.example.blog_api.mapper.CommentMapper;
import com.example.blog_api.respository.CommentRepository;
import com.example.blog_api.respository.PostRepository;
import com.example.blog_api.respository.UserRepository;
import com.example.blog_api.security.AuthUtil;
import com.example.blog_api.service.impl.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public CommentServiceImpl(CommentRepository commentRepo,
                              PostRepository postRepo,
                              UserRepository userRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @Override
    public CommentDTO create(CommentCreateDTO dto) {

        Post post = postRepo.findById(dto.getPostId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found: " + dto.getPostId()));

        User author = userRepo.findById(dto.getAuthorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + dto.getAuthorId()));

        Comment comment = new Comment(post, author, dto.getContent());
        comment = commentRepo.save(comment);

        return CommentMapper.toDTO(comment);
    }

    public CommentDTO update(Long id, CommentUpdateDTO dto) {

        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + id));

        User user = AuthUtil.getLoggedInUser(userRepo);

        boolean isAdmin = AuthUtil.isAdmin(user);

        if (!isAdmin && !comment.getAuthor().getId().equals(user.getId())) {
            throw new ApiException("You are not allowed to update this comment");
        }

        comment.setContent(dto.getContent());
        commentRepo.save(comment);

        return CommentMapper.toDTO(comment);
    }


    @Override
    public void delete(Long id) {

        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + id));

        User user = AuthUtil.getLoggedInUser(userRepo);

        boolean isAdmin = AuthUtil.isAdmin(user);

        if (!isAdmin && !comment.getAuthor().getId().equals(user.getId())) {
            throw new ApiException("You are not allowed to delete this comment");
        }

        commentRepo.delete(comment);
    }



    @Override
    public CommentDTO getById(Long id) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment not found: " + id));

        return CommentMapper.toDTO(comment);
    }

    @Override
    public List<CommentDTO> getAllCommentsForPost(Long postId) {
        return commentRepo.findByPostId(postId)
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getMyComments() {

        User user = AuthUtil.getLoggedInUser(userRepo);

        return commentRepo.findByAuthorId(user.getId())
                .stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
    }

}
