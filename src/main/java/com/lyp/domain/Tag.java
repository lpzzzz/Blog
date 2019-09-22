package com.lyp.domain;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签实体类
 */

@Entity
@Table(name = "t_tag")
public class Tag {

    @Id  //javax.persistence 这个包下的
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags",cascade =CascadeType.ALL)
    private List<Blog> blogs = new ArrayList<Blog>();


    public Tag() {
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
