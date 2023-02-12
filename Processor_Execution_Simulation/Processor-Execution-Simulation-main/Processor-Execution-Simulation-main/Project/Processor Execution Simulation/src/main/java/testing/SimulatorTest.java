package testing;

import processorScheduling.ProcessorExecutionISimulator;

public class SimulatorTest
{
    public static void main(String[] args)
    {
        ProcessorExecutionISimulator simulator = ProcessorExecutionISimulator.getInstance();
        simulator.run();
    }
}
