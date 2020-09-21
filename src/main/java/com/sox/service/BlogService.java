package com.sox.service;

import com.sox.pojo.Blog;
import com.sox.pojo.BlogUserAssociation;
import com.sox.pojo.Comment;
import com.sox.pojo.Type;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface BlogService {
    //select all blog
    List<BlogUserAssociation> queryAllBlogs(Map<String,Integer> map);

    List<BlogUserAssociation> selectBlogsByKeyword(Map<String,Object> map);

    int selectAllBlogsCount(Map<String,Integer> map);

    //select blog by Blog id
    Blog selectBlogByBlogId(String blogID);

    //select blog by author
    List<Blog> selectBlogByAuthorID(String authorID);

    //insert blog
    int insertBlog(Blog blog);

    //update blog view
    int updateBlogView(@Param("blogID") String blogID);

    //delete a blog by blog id
    int deleteBlogByBlogId(String blogID);

    List<Type> queryAllTypes();

    List<String> queryTypesByBlogID(String blogID);

    int insertBlogTypeAssoc(String blogID,int typeID);

    int insertComment(Comment comment);

    List<Comment> selectCommentByBlogID( String blogID);
}
