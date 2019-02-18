package demo.com.tcsa.entity;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity implements Persistable<Long> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(name = "f_create_time")
//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdTime;
//
//    @Column(name = "f_modify_time")
//    @LastModifiedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date modifyTime;


    @Override
    public boolean isNew() {
        return null == id;
    }

//    public DateTime getcreateTime() {
//
//        return null == createdTime ? null : new DateTime(createdTime);
//    }
//
//    public void setcreateTime(final DateTime createTime) {
//
//        this.createdTime = null == createTime ? null : createTime.toDate();
//    }
//
//    public DateTime getmodifyTime() {
//
//        return null == modifyTime ? null : new DateTime(modifyTime);
//    }
//
//    public void setmodifyTime(final DateTime modifyTime) {
//
//        this.modifyTime = null == modifyTime ? null : modifyTime.toDate();
//    }

    public Long getId() {
        return id;
    }

}
