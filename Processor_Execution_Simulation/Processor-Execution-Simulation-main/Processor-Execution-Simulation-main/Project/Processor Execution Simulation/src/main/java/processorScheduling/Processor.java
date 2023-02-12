package processorScheduling;

import states.ProcessorState;



public class Processor extends Data
{

        private Task task;
        private static int incremented_Processor_id = 1;
        private ProcessorState state;
        private final int id;

        public Task getTask()
        {
            return task;
        }

        public int getId()
        {
            return id;
        }

        public Processor(Task task)
        {
            this();
            this.task = task;
            state = ProcessorState.BUSY;
        }

        public Processor()
        {
            this.id = incremented_Processor_id++;
            this.state = ProcessorState.IDLE;
        }

        public void assignTask(Task task)
        {
            this.task = task;
            this.task.setExecuting();
            this.state = ProcessorState.BUSY;
            setOutputString( "Processor id = " + this.getId() + " has been assigned task id = " + this.task.getId() + "\n" );
        }

        public void executeTask()
        {
            if(this.task == null)
            {
                throw new RuntimeException( "No task is assigned to process with id = " + this.id );
            }
            setOutputString( "Processor id = " + this.id + " is executing task id = " + this.task.getId() + "\n" );
            this.task.run();
            if(this.task.isCompleted())
            {
                this.task.setCompleted();
               setCompletedTasks( this.task );
                setOutputString( "Processor id = " + this.id + " is has completed task id = " + this.task.getId() + " and now is idle \n" );
                this.task = null;
                this.state = ProcessorState.IDLE;
            }
        }

        public boolean isIdle()
        {
            return this.state == ProcessorState.IDLE;
        }

        public boolean isBusy()
        {
            return this.state == ProcessorState.BUSY;
        }

        public boolean isNotBusy()
        {
            return this.state != ProcessorState.BUSY;
        }

        public Task removeTask()
        {
            Task task = this.task;
            this.task.setWaiting();
            this.task = null;
            this.state = ProcessorState.IDLE;

            return task;
        }

}
