package processorScheduling;

import states.TaskPriority;
import states.TaskState;

public  class Task implements Comparable<Task>
{
    private static int incremented_task_id = 1;
    private final int id;
    private final int creation_time;
    private int executionTime;
    private TaskState state;
    private final TaskPriority taskPriority;

    public Task(int creation_time , int executionTime , TaskPriority taskPriority)
    {
        this.id = incremented_task_id++;
        this.creation_time = creation_time;
        this.executionTime = executionTime;
        this.taskPriority = taskPriority;
        this.state = TaskState.WAITING;
    }

    public void run()
    {
        if(this.executionTime > 0 && this.state != TaskState.COMPLETED)
        {
            this.executionTime--;
            if(this.executionTime == 0)
            {
                this.state = TaskState.COMPLETED;
            }
        }
        else
        {
            throw new RuntimeException( "Cannot execute a COMPLETED task !" );
        }
    }

    public void setExecuting()
    {
        state = TaskState.EXECUTING;
    }

    public void setCompleted()
    {
        state = TaskState.COMPLETED;
    }

    public void setWaiting()
    {
        state = TaskState.WAITING;
    }

    public int getId()
    {
        return id;
    }

    public int getCreation_time()
    {
        return creation_time;
    }
    public boolean isCompleted()
    {
        return state == TaskState.COMPLETED;
    }


    public boolean isHighPriority()
    {
        return this.taskPriority == TaskPriority.HIGH;
    }

    public boolean isLowPriority()
    {
        return this.taskPriority == TaskPriority.LOW;
    }

    @Override
    public int compareTo(Task o)
    {
        if(this.taskPriority != o.taskPriority)
        {
            if(this.isHighPriority())
            {
                return -1;
            }
            else
            {
                return 1;
            }
        }
        if(this.creation_time != o.creation_time)
        {
            return this.creation_time - o.creation_time;
        }
        return this.executionTime - o.executionTime;
    }
}
