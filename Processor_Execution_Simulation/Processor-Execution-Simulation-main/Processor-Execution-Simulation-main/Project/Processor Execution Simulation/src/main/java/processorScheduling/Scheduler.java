package processorScheduling;
import common.Clock;

import java.util.HashSet;

public class Scheduler extends Data
{
    private static final HashSet<Task> highPrioritySet = new HashSet<>();
    private static final HashSet<Task> lowPrioritySet = new HashSet<>();

    private Scheduler() {

    }

    static Clock clock= new Clock();

    public static void run() {
        while (!isFinish()) {
            setOutputString("At cycle = " + clock.getCurrentCycle() + "\n");
            checkArrivedTasks();
            schedule();
            executeProcessors();
           setOutputString("\n");
            clock.setCurrentCycle(clock.getCurrentCycle()+1);
        }
        setOutputString("PROGRAM HAS COMPLETED.\n");
    }

    private static boolean isFinish() {
        return getCompletedTasks().size() == getNumberOfTasks();
    }

    private static void executeProcessors() {
        getProcessors().forEach(processor -> {
            if (processor.isBusy()) {
                processor.executeTask();
            }
        });
    }


    private static void schedule() {
        assignIdleProcessors();
        solveTieBreaking();
    }

    private static void solveTieBreaking() {
        if (isHighPriorityWaiting() && allProcessorsAssignedHigh()) {
            for (Processor processor : getProcessors()) {
                highPrioritySet.add(processor.removeTask());
            }assignIdleProcessors();}



    }

    private static boolean allProcessorsAssignedHigh() {
        for (Processor processor : getProcessors()) {
            if (processor.isBusy() && !processor.getTask().isHighPriority()) {
                return false;
            } else if (processor.isNotBusy()) {
                return false;
            }
        }
        return true;
    }

    private static void assignIdleProcessors() {
        for (Processor processor :getProcessors()) {
            if (processor.isIdle() && !highPrioritySet.isEmpty()) {
                Task task = getLongestExecutionTimeTask(highPrioritySet);
                processor.assignTask(task);
                highPrioritySet.remove(task);
            }
        }

        for (Processor processor : getProcessors()) {
            if (processor.isIdle() && !lowPrioritySet.isEmpty()) {
                Task task = getLongestExecutionTimeTask(lowPrioritySet);
                processor.assignTask(task);
                lowPrioritySet.remove(task);
            }
        }
    }

    private static Task getLongestExecutionTimeTask(HashSet<Task> tasks) {
        Task longestTask = null;
        int maxExecutionTime = Integer.MIN_VALUE;
        for (Task task : tasks) {
            if (task.getCreation_time() > maxExecutionTime) {
                maxExecutionTime = task.getCreation_time();
                longestTask = task;
            }
        }
        return longestTask;
    }


    private static void checkArrivedTasks() {
        while (!getArrivedTasks().isEmpty()) {
            Task task = getArrivedTasks().peek();
            if (task.getCreation_time() == clock.getCurrentCycle()) {
                setOutputString("Task id = " + task.getId() + " has arrived \n");
                if (task.isLowPriority()) {
                    lowPrioritySet.add(task);
                } else {
                    highPrioritySet.add(task);
                }
               getArrivedTasks().remove();
            } else {
                break;
            }
        }
    }

    private static boolean isHighPriorityWaiting() {
        return !highPrioritySet.isEmpty();
    }


}
