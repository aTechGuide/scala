package guide.atech.courses.multithreading_michael.section2;

import java.util.List;

public class MultiExecutor {

    // Add any necessary member variables here
    final private List<Runnable> tasks;

    /* 
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        // Complete your code here
        this.tasks = tasks;
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        // complete your code here
    }
}