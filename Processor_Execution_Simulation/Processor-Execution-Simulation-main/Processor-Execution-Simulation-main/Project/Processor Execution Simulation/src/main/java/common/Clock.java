package common;

public class Clock
{
    private static int currentCycle = 1;
    public void setCurrentCycle(int currentCycle) {
        this.currentCycle = currentCycle;
    }

    public static int getCurrentCycle() {
        return currentCycle;
    }
}
