/*
 * Created on Thu Jul 30 2020
 *
 * @author Patrique Legault
 * @version 1.0
 * @since Thu Jul 30 2020
 *
 * Copyright (c) 2020 LegoTech
 */

package io.github.vm.patlego.workflow.datasource.tables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "workflow_manager_wi", schema = "patlegovm")
@Entity(name =  "workflow_manager_wi")
public class WorkflowManagerWI implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6886620220164383285L;

    @EmbeddedId
    private WorkItemId id;

    @Column(name = "sequence_number", nullable = true)
    private Integer sequenceNumber;

    @Column(name = "success", nullable = true)
    private Boolean success;

    @Column(name = "workflow_name", nullable = true)
    private String workflowName;

    public WorkItemId getId() {
        return this.id;
    }

    public void setId(WorkItemId id) {
        if (id == null) {
            throw new IllegalArgumentException(String.format("Cannot reference a primary key %s with a nullable value", WorkItemId.class.getName()));
        }
        this.id = id;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
}