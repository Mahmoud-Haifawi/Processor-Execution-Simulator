package processorScheduling;
import processorScheduling.Processor;
import processorScheduling.Task;
import states.TaskPriority;

import java.util.ArrayDeque;

public class Data
{
    private static int numberOfProcessors;
    private static int numberOfTasks;
    private static final StringBuilder outputString = new StringBuilder();
    private static final ArrayDeque<Processor> processors = new ArrayDeque<>();
    private static final  ArrayDeque<Task> arrivedTasks = new ArrayDeque<>();
    private static final  ArrayDeque<Task> completedTasks = new ArrayDeque<>();


    public static void setOutputString(String outputString2)
    {
       outputString.append(outputString2);
    }


    public void setNumberOfProcessors(int numberOfProcessors) {
        this.numberOfProcessors = numberOfProcessors;
    }

    public static int getNumberOfProcessors() {
        return numberOfProcessors;
    }

    public void setNumberOfTasks(int numberOfTasks) {
       this.numberOfTasks = numberOfTasks;
    }

    public static int getNumberOfTasks() {
        return numberOfTasks;
    }

    public static StringBuilder getOutputString() {
        return outputString;
    }

    public static ArrayDeque<Processor> getProcessors() {
        return processors;
    }
    public void setProcessors()
    {
        processors.add( new Processor() );

    }

    public static ArrayDeque<Task> getArrivedTasks()
    {
        return arrivedTasks;
    }
    public void setArrivedTasks(int creation_time, int excitation_time , TaskPriority taskPriority ){
        arrivedTasks.add ( new Task( creation_time , excitation_time , taskPriority ) );
    }

    public static ArrayDeque<Task> getCompletedTasks() {
        return completedTasks;
    }
    public static void setCompletedTasks(Task task){
        completedTasks.add( task );
    }





}
