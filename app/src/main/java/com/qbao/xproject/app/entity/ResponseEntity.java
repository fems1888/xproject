package com.qbao.xproject.app.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Created by jackieyao on 2018/9/11 上午11:02.
 */
@Entity
public class ResponseEntity {
    @Id(autoincrement = true)
    private Long id;

    @Generated(hash = 1687671415)
    public ResponseEntity(Long id) {
        this.id = id;
    }

    @Generated(hash = 477411930)
    public ResponseEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
