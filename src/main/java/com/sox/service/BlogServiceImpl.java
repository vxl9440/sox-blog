package com.sox.service;

import com.sox.mapper.BlogMapper;
import com.sox.pojo.Blog;
import com.sox.pojo.BlogUserAssociation;
import com.sox.pojo.Comment;
import com.sox.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogMapper blogMapper;

    @Override
    public List<BlogUserAssociation> queryAllBlogs(Map<String,Integer> map) {
        return blogMapper.queryAllBlogs(map);
    }

    @Override
    public List<BlogUserAssociation> selectBlogsByKeyword(Map<String,Object> map) {
        return blogMapper.selectBlogsByKeyword(map);
    }

    @Override
    public int selectAllBlogsCount(Map<String,Integer> map) {
        return blogMapper.selectAllBlogsCount(map);
    }

    @Override
    public Blog selectBlogByBlogId(String blogID) {
        return blogMapper.selectBlogByBlogId(blogID);
    }

    @Override
    public List<Blog> selectBlogByAuthorID(String authorID) {
        return blogMapper.selectBlogByAuthorID(authorID);
    }

    @Override
    public int insertBlog(Blog blog) {
        return blogMapper.insertBlog(blog);
    }

    @Override
    public int updateBlogView(String blogID) {
        return blogMapper.updateBlogView(blogID);
    }

    @Override
    public int deleteBlogByBlogId(String blogID) {
        return blogMapper.deleteBlogByBlogId(blogID);
    }

    @Override
    public List<Type> queryAllTypes() {
        return blogMapper.queryAllTypes();
    }

    @Override
    public List<String> queryTypesByBlogID(String blogID) {
        return blogMapper.queryTypesByBlogID(blogID);
    }

    @Override
    public int insertBlogTypeAssoc(String blogID, int typeID) {
        return blogMapper.insertBlogTypeAssoc(blogID,typeID);
    }

    @Override
    public int insertComment(Comment comment) {
        return blogMapper.insertComment(comment);
    }

    @Override
    public List<Comment> selectCommentByBlogID(String blogID) {
        return blogMapper.selectCommentByBlogID(blogID);
    }
}
