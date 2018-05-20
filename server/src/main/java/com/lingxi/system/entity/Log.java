package com.lingxi.system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="logging_event")
public class Log implements Serializable {

    @Id
    @Column(name = "event_id")
    private Long id;

    @Column(name = "logger_name")
    private String name;

    @Column(name = "level_string")
    private String level;

    @Column(name = "formatted_message")
    private String message;

    private Long timestmp;
    private String thread_name;
    private String reference_flag;
    private String caller_filename;
    private String caller_class;
    private String caller_method;
    private String caller_line;

    private String arg0;
    private String arg1;
    private String arg2;
    private String arg3;

    public Date getTimestmp() {
        return new Date(timestmp);
    }
}
