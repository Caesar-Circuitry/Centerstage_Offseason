package org.firstinspires.ftc.teamcode.SubSystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Intake extends SubSystemParent{
    public enum intakePow{
        ON,
        OFF
    }
    public enum intakeDIR{
        FORWARD,
        REVERSE
    }
    public enum autoLiftStates{
        UP,
        DOWN
    }
    public enum autoClawStates{
        CLOSED,
        OPEN
    }
    private intakeDIR Direction;
    private intakePow PowerState;
    private autoLiftStates LiftState;
    private autoClawStates ClawState;
    public static double speedMultiplier = 1;
    private double directionMultiplier = 1;
    private double power=0;
    private DcMotor IntakeMotor;
    private Servo clawLeft, clawRight, whitePixelLift;
    private double clawLeftOpen = 1, clawLeftClose = 0, clawRightOpen = 1, clawRightClose = 0, whitePixelLiftUp = 1, whitePixelLiftDown = 0;
    public Intake(HardwareMap hardwareMap){
        this.IntakeMotor = hardwareMap.get(DcMotor.class,"IntakeMotor");
        this.clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        this.clawRight = hardwareMap.get(Servo.class, "clawRight");
        this.whitePixelLift = hardwareMap.get(Servo.class, "whitePixelLift");
        this.Direction = intakeDIR.FORWARD;
        this.PowerState = intakePow.OFF;
        this.whitePixelLift.setPosition(whitePixelLiftUp);
        this.LiftState = autoLiftStates.UP;
        this.clawLeft.setPosition(clawLeftClose);
        this.clawRight.setPosition(clawRightClose);
        this.ClawState = autoClawStates.CLOSED;
    }
    public void switchState(intakeDIR direction, intakePow powerState){
        this.Direction = direction;
        this.PowerState = powerState;
        }
    public void switchState(intakeDIR direction){
        this.Direction = direction;
    }
    public void switchState(intakePow powerState){
        this.PowerState = powerState;
    }
    @Override
    public void periodic() {
        switch (Direction){
            case FORWARD:
                directionMultiplier = 1;
                break;
            case REVERSE:
                directionMultiplier = -1;
                break;
        }
        switch (PowerState){
            case ON:
                this.power= this.speedMultiplier * this.directionMultiplier;
                break;
            case OFF:
                this.power = 0;
                break;
        }
        IntakeMotor.setPower(power);
    }
    public void switchAutoState(autoClawStates claw, autoLiftStates lift){
        this.LiftState = lift;
        this.ClawState = claw;
    }
    public void autoPeriodic(){
        switch (LiftState){
            case UP:
                this.whitePixelLift.setPosition(whitePixelLiftUp);
                break;
            case DOWN:
                this.whitePixelLift.setPosition(whitePixelLiftDown);
                break;
        }
        switch (ClawState){
            case OPEN:
                this.clawLeft.setPosition(clawLeftOpen);
                this.clawRight.setPosition(clawRightOpen);
                break;
            case CLOSED:
                this.clawLeft.setPosition(clawLeftClose);
                this.clawRight.setPosition(clawRightClose);
                break;
        }
    }

    @Override
    public void reset() {
        this.switchState(intakeDIR.FORWARD, intakePow.OFF);
    }

    public intakeDIR getDirection() {
        return Direction;
    }

    public intakePow getPowerState() {
        return PowerState;
    }

    public static double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public double getDirectionMultiplier() {
        return directionMultiplier;
    }

    public double getPower() {
        return power;
    }

    public DcMotor getIntakeMotor() {
        return IntakeMotor;
    }
}
