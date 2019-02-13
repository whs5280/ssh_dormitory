package com.business.bean;
//费用bean

import com.business.util.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="charge")
public class ChargeVo extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id",length=20,unique=true,nullable=false,scale=0)
    private Long id;
    @Column(name="dorm_id",length=20)
    private Long dorm_id;
    @Column(name="building_id",length=20)
    private Long building_id;
    @Column(name="month",length=20)
    private int month;
    @Column(name="charges",length=20)
    private Double charges;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDorm_id() {
        return dorm_id;
    }

    public void setDorm_id(Long dorm_id) {
        this.dorm_id = dorm_id;
    }

    public Long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(Long building_id) {
        this.building_id = building_id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }
}
