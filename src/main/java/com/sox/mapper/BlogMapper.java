package com.sox.mapper;

import com.sox.pojo.Blog;
import com.sox.pojo.BlogUserAssociation;
import com.sox.pojo.Comment;
import com.sox.pojo.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BlogMapper {

    //select all blog
    List<BlogUserAssociation> queryAllBlogs(Map<String,Integer> map);

    List<BlogUserAssociation> selectBlogsByKeyword(Map<String,Object> map);

    int selectAllBlogsCount(Map<String,Integer> map);

    //select blog by Blog id
    @Select("select * from blog.blog where blogID = #{blogID}")
    Blog selectBlogByBlogId(@Param("blogID") String blogID);

    //select blog by author id
    @Select("select * from blog.blog where authorID = #{authorID}")
    List<Blog> selectBlogByAuthorID(@Param("authorID") String authorID);

    //insert blog
    int insertBlog(Blog blog);

    //update blog view
    @Update("update blog.blog set `view` = `view` + 1 where blogID = #{blogID}")
    int updateBlogView(@Param("blogID") String blogID);

    //delete a blog by blog id
    @Delete("delete from blog.blog where blogID = #{blogID}")
    int deleteBlogByBlogId(@Param("blogID") String blogID);

    @Select("select * from blog.type")
    List<Type> queryAllTypes();

    List<String> queryTypesByBlogID(String blogID);

    @Insert("insert into blog.blog_type_assoc(blogID,typeID) values(#{blogID},#{typeID})")
    int insertBlogTypeAssoc(String blogID,int typeID);


    @Insert("insert into blog.comment(blogID,commenterID,commentContent,`date`) " +
            "values(#{blogID},#{commenterID},#{commentContent},#{date})")
    int insertComment(Comment comment);

    @Select("select * from blog.comment where blogID = #{blogID}")
    List<Comment> selectCommentByBlogID(@Param("blogID") String blogID);
}
