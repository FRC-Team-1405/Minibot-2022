package frc.robot.lib;

import edu.wpi.first.wpilibj.DigitalInput;

public class DigitalFlag {
    private static int startPin = 0;

    private static DigitalInput[] flags = new DigitalInput[] {
        new DigitalInput(startPin + 0),
        new DigitalInput(startPin + 1),
        new DigitalInput(startPin + 2),
        new DigitalInput(startPin + 3),
        new DigitalInput(startPin + 4),
        new DigitalInput(startPin + 5),
        new DigitalInput(startPin + 6),
        new DigitalInput(startPin + 7)
    };

    public static boolean IsCIM() {
        return flags[0].get();
    }
    public static boolean IsFalcon() {
        return !flags[0].get();
    }

    public static boolean IsTank() {
        return flags[1].get();
    }
    public static boolean IsMecanum() {
        return !flags[1].get();
    }

    public static boolean IsSimulation() {
        return flags[7].get();
    }
}

