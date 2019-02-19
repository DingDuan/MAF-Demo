package demo.entity;

import lombok.Data;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity implements Persistable<Integer> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

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

    public Integer getId() {
        return id;
    }

}
