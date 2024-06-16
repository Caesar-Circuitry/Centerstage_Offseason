package org.firstinspires.ftc.teamcode.rrPaths.Actions;

public class GlobalVars {
    public static boolean StopViperAuto = false;
    public enum ViperPos{
        ZERO, // in = 0
        YELLOW, //in = 11
        ONE, // in = 13
        TWO, // in = 16
        THREE, // in = 24
        HANGUP, // in = 17
        HANGDOWN //in = 4
    }
    public static ViperPos viperPos = ViperPos.ZERO;
    public static double speed = 1; // 0 to 1
    public GlobalVars(){
        viperPos = ViperPos.ZERO;
        StopViperAuto = false;
        speed = 1;
    }
    public double getInches(){
        switch (viperPos) {
            case ZERO:
                return 0;
            case YELLOW:
                return 11;
            case ONE:
                return 13;
            case TWO:
                return 16;
            case THREE:
                return 24;
            case HANGUP:
                return 17;
            case HANGDOWN:
                return 4;
        }
        return 0;
    }

    public boolean isStopViperAuto() {
        return StopViperAuto;
    }

    public ViperPos getViperPos() {
        return viperPos;
    }

    public void setStopViperAuto(boolean stopViperAuto) {
        StopViperAuto = stopViperAuto;
    }

    public static void setViperPos(ViperPos Pos) {
        viperPos = Pos;
    }

    public double getSpeed() {
        return speed;
    }

    public static void setSpeed(double speed) {
        GlobalVars.speed = speed;
    }
}
