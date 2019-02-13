package com.business.bean;
//考勤违规bean

import com.business.util.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="illegal")
public class IllegalVo extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id",length=20,unique=true,nullable=false,scale=0)
    private Long id;
    @Column(name="illegal_stu",length=20)
    private Long illegal_stu;
    @Column(name="illegal_content",length=100)
    private String illegal_content;
    @Column(name="illegal_time",length=100)
    private String illegal_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIllegal_stu() {
        return illegal_stu;
    }

    public void setIllegal_stu(Long illegal_stu) {
        this.illegal_stu = illegal_stu;
    }

    public String getIllegal_content() {
        return illegal_content;
    }

    public void setIllegal_content(String illegal_content) {
        this.illegal_content = illegal_content;
    }

    public String getIllegal_time() {
        return illegal_time;
    }

    public void setIllegal_time(String illegal_time) {
        this.illegal_time = illegal_time;
    }
}
