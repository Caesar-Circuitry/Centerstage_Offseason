package org.firstinspires.ftc.teamcode.SubSystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class Hang extends SubSystemParent{
    public enum hangState{
        ARMED,
        FIRED,
    }
    private Hang.hangState targetState,actualState;
    private final Servo hangServoLeft,hangServoRight;
    public static double ArmedPosLeft = 0,FiredPosLeft = 1,ArmedPosRight = 0,FiredPosRight = 1;
    public Hang(HardwareMap hardwareMap){
        this.targetState = hangState.ARMED;
        this.hangServoLeft = hardwareMap.get(Servo.class,"HangLeft");
        this.hangServoRight = hardwareMap.get(Servo.class,"HangRight");
        this.hangServoLeft.setPosition(ArmedPosLeft);
        this.hangServoRight.setPosition(ArmedPosRight);
    }
    public void switchState(hangState hangState){
                this.targetState = hangState;
    }
    @Override
    public void periodic() {
        switch (targetState){
            case ARMED:
                this.hangServoLeft.setPosition(ArmedPosLeft);
                this.hangServoRight.setPosition(ArmedPosRight);
                this.actualState = hangState.ARMED;
                break;
            case FIRED:
                this.hangServoLeft.setPosition(FiredPosLeft);
                this.hangServoRight.setPosition(FiredPosRight);
                this.actualState = hangState.FIRED;
                break;
        }
    }

    @Override
    public void reset() {
        this.switchState(hangState.ARMED);
    }

    public hangState getTargetState() {
        return targetState;
    }

    public hangState getActualState() {
        return actualState;
    }

    public Servo getHangServoLeft() {
        return hangServoLeft;
    }

    public Servo getHangServoRight() {
        return hangServoRight;
    }
}
