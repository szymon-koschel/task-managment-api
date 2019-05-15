package com.api.task.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task_periodicity")
@EntityListeners(AuditingEntityListener.class)
public class TaskPeriodicity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_periodicity_id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task task_id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date task_date;

    public Long getTask_periodicity_id() {
        return task_periodicity_id;
    }

    public void setTask_periodicity_id(Long task_periodicity_id) {
        this.task_periodicity_id = task_periodicity_id;
    }

    public Task getTask_id() {
        return task_id;
    }

    public void setTask_id(Task task_id) {
        this.task_id = task_id;
    }

    public Date getTask_date() {
        return task_date;
    }

    public void setTask_date(Date task_date) {
        this.task_date = task_date;
    }
}
