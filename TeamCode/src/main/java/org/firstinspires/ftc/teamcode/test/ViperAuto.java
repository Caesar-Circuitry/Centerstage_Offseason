package org.firstinspires.ftc.teamcode.test;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ViperAuto {
    private double lvl0,lvl1,lvl2,lvl3; // inches
    private double speed; // 0to1
    private Motor.Encoder liftLeftEncoder, liftRightEncoder;
    private DcMotorEx liftMotorLeft, liftMotorRight;
    private PIDFController LeftLiftController;
    private PIDFController RightLiftController;
    private final double LP = .03, LI = 0, LD = 0, LF = 0, RP = .03, RI = 0, RD = 0, RF = 0;
    private Motor.ZeroPowerBehavior ZERO_POWER_BEHAVIOR = Motor.ZeroPowerBehavior.BRAKE;
    private double liftLeftTargetPos_ticks = 0, liftRightTargetPos_ticks = 0, LIFT_LEFT_TICKS_PER_IN = 113.285714, LIFT_Right_TICKS_PER_IN = 113.8 , LeftLiftPower = 0, RightLiftPower = 0, liftLeftLastPos_ticks = 0,liftRightLastPos_ticks = 0, prevLeftLiftPower = 0, prevRightLiftPower;
    private static double heightIN = 0;
    public ViperAuto(HardwareMap hardwareMap, double lvl0, double lvl1, double lvl2, double lvl3, double speed){
        LeftLiftController = new PIDFController(LP, LI, LD, LF);
        RightLiftController = new PIDFController(RP,RI,RD,RF);
        liftLeftEncoder = new Motor(hardwareMap,"leftLiftMotor", Motor.GoBILDA.RPM_312).encoder;
        liftRightEncoder = new Motor(hardwareMap, "rightLiftMotor", Motor.GoBILDA.RPM_312).encoder;

        liftMotorLeft = hardwareMap.get(DcMotorEx.class, "leftLiftMotor");
        liftMotorLeft.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR.getBehavior());
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        liftMotorRight = hardwareMap.get(DcMotorEx.class, "rightLiftMotor");
        liftMotorRight.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR.getBehavior());
        liftMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.lvl0 = lvl0;
        this.lvl1 = lvl1;
        this.lvl2 = lvl2;
        this.lvl3 = lvl3;
        this.speed = speed;
    }
    public class level0 implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            return liftRunToPosition(lvl0, speed);
        }
    }
    public Action level0(){
        return new level0();
    }
    public class level1 implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            return liftRunToPosition(lvl1, speed);
        }
    }
    public Action level1(){
        return new level1();
    }
    public class level2 implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            return liftRunToPosition(lvl2, speed);
        }
    }
    public Action level2(){
        return new level2();
    }
    public class level3 implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            return liftRunToPosition(lvl3, speed);
        }
    }
    public Action level3(){
        return new level3();
    }
    private boolean liftRunToPosition(double pos_in, double speed_0to1) {
        liftLeftTargetPos_ticks = pos_in * LIFT_LEFT_TICKS_PER_IN;
        liftRightTargetPos_ticks = pos_in * LIFT_Right_TICKS_PER_IN;

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
        if (LeftLiftPower == prevLeftLiftPower && RightLiftPower == prevRightLiftPower){
            return true;
        }
        else {
            prevLeftLiftPower = LeftLiftPower;
            prevRightLiftPower = RightLiftPower;
            return false;
        }
    }
}
