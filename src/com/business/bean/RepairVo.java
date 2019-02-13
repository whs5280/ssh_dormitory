package com.business.bean;
//维修bean

import com.business.util.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="repair")
public class RepairVo extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id",length=20,unique=true,nullable=false,scale=0)
    private Long id;
    @Column(name="dorm_id",length=20)
    private String dorm_id;
    @Column(name="building_id",length=20)
    private String building_id;
    @Column(name="content",length=100)
    private String content;
    @Column(name="status",length=10)
    private int status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDorm_id() {
        return dorm_id;
    }

    public void setDorm_id(String dorm_id) {
        this.dorm_id = dorm_id;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
