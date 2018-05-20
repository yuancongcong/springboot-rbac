package com.lingxi.framework.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.MappedSuperclass;
import java.util.Date;


@Getter
@Setter
@MappedSuperclass
public class IdDateEntity extends IdEntity {

    @JSONField(serialize = false)
    private Date createTime;

    @JSONField(serialize = false)
    private Date updateTime;

    public void setCurrentDate(){
        Date current = new Date();
        if(StringUtils.isEmpty(this.getId())){
            this.createTime = current;
        }
        this.updateTime = current;
    }
}
