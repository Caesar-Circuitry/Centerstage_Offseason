package org.firstinspires.ftc.teamcode.SubSystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class Plane extends SubSystemParent{
    public enum planeState{
        ARMED,
        FIRED,
    }
    private planeState targetState,actualState;
    private final Servo planeServo;
    public static double ArmedPos = 0,FiredPos = 1;
    public Plane(HardwareMap hardwareMap){
        this.targetState = planeState.ARMED;
        this.planeServo = hardwareMap.get(Servo.class,"PlaneServo");
        this.planeServo.setPosition(ArmedPos);
        this.actualState = planeState.ARMED;
    }
    public void switchState(planeState planeState){
                this.targetState = planeState;
    }
    @Override
    public void periodic() {
    switch(targetState){
        case ARMED:
            this.planeServo.setPosition(ArmedPos);
            this.actualState = planeState.ARMED;
            break;
        case FIRED:
            this.planeServo.setPosition(FiredPos);
            this.actualState = planeState.FIRED;
            break;
    }
    }

    @Override
    public void reset() {
        this.switchState(planeState.ARMED);
    }

    public planeState getTargetState() {
        return this.targetState;
    }

    public planeState getActualState() {
        return this.actualState;
    }

    public Servo getPlaneServo() {
        return this.planeServo;
    }
}
