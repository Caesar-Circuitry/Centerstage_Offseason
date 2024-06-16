package org.firstinspires.ftc.teamcode.rrPaths.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ViperAuto {
    GlobalVars vars = new GlobalVars();
    private Motor.Encoder liftLeftEncoder, liftRightEncoder;
    private Servo boxRotate, boxClip;
    private double boxRotateHome = .39, boxRotateBoard = .9,boxClipOpen = .65,boxClipClose = .4, threshold = 5;
    private DcMotorEx liftMotorLeft, liftMotorRight;
    private PIDFController LeftLiftController;
    private PIDFController RightLiftController;
    private final double LP = .005, LI = 0, LD = 0, LF = 0, RP = .005, RI = 0, RD = 0, RF = 0;
    private Motor.ZeroPowerBehavior ZERO_POWER_BEHAVIOR = Motor.ZeroPowerBehavior.BRAKE;
    private double liftLeftTargetPos_ticks = 0, liftRightTargetPos_ticks = 0, LIFT_LEFT_TICKS_PER_IN = 111.05263, LIFT_Right_TICKS_PER_IN = 113.47368 , LeftLiftPower = 0, RightLiftPower = 0, liftLeftLastPos_ticks = 0,liftRightLastPos_ticks = 0, prevLeftLiftPower = 0, prevRightLiftPower;
    public ViperAuto(HardwareMap hardwareMap){
        LeftLiftController = new PIDFController(LP, LI, LD, LF);
        RightLiftController = new PIDFController(RP,RI,RD,RF);
        liftLeftEncoder = new Motor(hardwareMap,"leftViper", Motor.GoBILDA.RPM_312).encoder;
        liftRightEncoder = new Motor(hardwareMap, "rightViper", Motor.GoBILDA.RPM_312).encoder;

        liftMotorLeft = hardwareMap.get(DcMotorEx.class, "leftViper");
        liftMotorLeft.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR.getBehavior());
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        liftMotorRight = hardwareMap.get(DcMotorEx.class, "rightViper");
        liftMotorRight.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR.getBehavior());
        liftMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        boxRotate = hardwareMap.get(Servo.class,"boxRotate");
        boxClip = hardwareMap.get(Servo.class,"boxClip");
    }
    private class moveViper implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            return liftRunToPosition(vars.getInches(), vars.getSpeed());
        }
    }
    public Action moveViper(){
        return new moveViper();
    }
    private class boxClipOpen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                boxClip.setPosition(boxClipOpen);
            return false;
        }
    }
    public Action boxClipOpen(){
        return new boxClipOpen();
    }
    private class boxClipClose implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            boxClip.setPosition(boxClipClose);
            return false;
        }
    }
    public Action boxClipClose(){
        return new boxClipClose();
    }
    private class Viper0 implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            vars.setViperPos(GlobalVars.ViperPos.ZERO);
            return false;
        }
    }
    public Action viper0(){
        return new Viper0();
    }
    private class ViperYellow implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            vars.setViperPos(GlobalVars.ViperPos.YELLOW);
            return false;
        }
    }
    public Action viperYellow(){
        return new ViperYellow();
    }
    private class boxRotateHome implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            boxRotate.setPosition(boxRotateHome);
            return false;
        }
    }
    public Action boxRotateHome(){
        return new boxRotateHome();
    }
    private class boxRotateBoard implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            boxRotate.setPosition(boxRotateBoard);
            return false;
        }
    }
    public Action boxRotateBoard(){
        return new boxRotateBoard();
    }
    private boolean liftRunToPosition(double pos_in, double speed_0to1) {
        liftLeftTargetPos_ticks = vars.getInches() * LIFT_LEFT_TICKS_PER_IN;
        liftRightTargetPos_ticks = vars.getInches() * LIFT_Right_TICKS_PER_IN;

        LeftLiftPower = LeftLiftController.calculate(liftLeftLastPos_ticks, liftLeftTargetPos_ticks) * speed_0to1;
        RightLiftPower = RightLiftController.calculate(liftRightLastPos_ticks, liftRightTargetPos_ticks) * speed_0to1;

        try {
            liftLeftLastPos_ticks = liftLeftEncoder.getPosition();
            liftRightLastPos_ticks = liftRightEncoder.getPosition();
        } catch (Exception e) {
            liftLeftLastPos_ticks = 0;
            liftRightLastPos_ticks = 0;
        }

        if (LeftLiftPower != prevLeftLiftPower) {
            liftMotorLeft.setPower(LeftLiftPower);
        }
        if(RightLiftPower != prevRightLiftPower){
            liftMotorRight.setPower(RightLiftPower);
        }
        if (liftLeftEncoder.getPosition() == liftLeftTargetPos_ticks) {
            return true;
        }
            prevLeftLiftPower = LeftLiftPower;
            prevRightLiftPower = RightLiftPower;
            return true;
    }
}
