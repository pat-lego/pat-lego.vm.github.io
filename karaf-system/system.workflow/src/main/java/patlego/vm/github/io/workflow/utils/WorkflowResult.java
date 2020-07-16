package patlego.vm.github.io.workflow.utils;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface WorkflowResult {

    /**
     * Determines if the Workflow has successfully completed or not
     * @return True -> Completed without errors, False -> Did not complete encountered errors
     */
    public @Nonnull Boolean hasSucceeded();

    /**
     * Returns the exception that caused the Workflow failure
     * @return Exception
     */
    public @Nullable Exception getException();

    /**
     * Returns the failed WorkItem name
     * @return String The name of the WorkItem that caused the failure
     */
    public @Nullable String getFailedWorkItemName();

    /**
     * Returns the final parameter map that was used between all WorkItems
     * @return Map<String, Object>
     */
    public @Nonnull Map<String, Object> getParameters();

    /**
     * Returns an identifier used to query for the Workflow instance
     * @return String
     */
    public @Nonnull String getId();
    
}