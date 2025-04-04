package me.crymath.stackwatch.client;

import java.util.List;
import me.crymath.stackwatch.model.Comment;

public interface CommentClient {
    List<Comment> listComments();

    Comment getComment(int commentId);

    List<Comment> listCommentsForPost(int postId);
}
