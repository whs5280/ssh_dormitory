package com.business.bean;
//来访记录bean
import com.business.util.BaseEntity;
import javax.persistence.*;

@Entity
@Table(name="dorm_visitor")
public class VisitorVo extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="dv_id",length=20,unique=true,nullable=false,scale=0)
    private Long dv_id;
    @Column(name="visitor_name",length=30)
    private String visitor_name;
    @Column(name="visitor_event",length=50)
    private String visitor_event;
    @Column(name="visitor_time",length=100)
    private String visitor_time;

    public Long getDv_id() {
        return dv_id;
    }

    public void setDv_id(Long dv_id) {
        this.dv_id = dv_id;
    }

    public String getVisitor_name() {
        return visitor_name;
    }

    public void setVisitor_name(String visitor_name) {
        this.visitor_name = visitor_name;
    }

    public String getVisitor_event() {
        return visitor_event;
    }

    public void setVisitor_event(String visitor_event) {
        this.visitor_event = visitor_event;
    }

    public String getVisitor_time() {
        return visitor_time;
    }

    public void setVisitor_time(String visitor_time) {
        this.visitor_time = visitor_time;
    }
}

