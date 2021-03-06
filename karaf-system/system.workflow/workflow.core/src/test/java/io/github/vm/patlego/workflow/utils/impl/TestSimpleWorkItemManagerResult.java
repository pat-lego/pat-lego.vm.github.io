package io.github.vm.patlego.workflow.utils.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.vm.patlego.workflow.datasource.tables.WorkItemId;
import io.github.vm.patlego.workflow.datasource.tables.WorkflowManagerWF;
import io.github.vm.patlego.workflow.datasource.tables.WorkflowManagerWI;
import io.github.vm.patlego.workflow.utils.WorkItemManagerResult;
import io.github.vm.patlego.workflow.utils.WorkflowManagerResult;

public class TestSimpleWorkItemManagerResult {

    WorkflowManagerWF manager;
    String id;
    
    @BeforeEach
    public void init() {
        this.id = UUID.randomUUID().toString();

        this.manager = new WorkflowManagerWF();
        this.manager.setWorkflowId(this.id);
    }

    @Test
    public void testConvertSimpleWorkItemManagerResult() {
        WorkItemId id = new  WorkItemId();
        id.setWorkflowId(this.manager);
        id.setWorkitemName(this.id);

        WorkflowManagerWI entity = new WorkflowManagerWI();
        entity.setId(id);
        entity.setSequenceNumber(12);
        entity.setWorkflowName(this.id);
        WorkItemManagerResult workItem = SimpleWorkItemManagerResult.create(entity);

        assertEquals(this.id, workItem.getName());
        assertEquals(12, workItem.getSequenceNumber());
        assertEquals(this.id, workItem.getWorkflowName());
    }

    @Test
    public void testConvertWorkflowManagerWI() {
        WorkItemManagerResult entity = new SimpleWorkItemManagerResult(12, this.id, this.id, true);
        WorkflowManagerResult wfManager = new SimpleWorkflowManagerResult(this.id);
        WorkflowManagerWI workItem = SimpleWorkItemManagerResult.create(entity, wfManager);

        assertEquals(this.id, workItem.getId().getWorkflowId().getWorkflowId());
        assertEquals(12, workItem.getSequenceNumber());
        assertEquals(this.id, workItem.getId().getWorkitemName());
        assertEquals(this.id, workItem.getWorkflowName());
        assertTrue(workItem.getSuccess());
    }
    
}