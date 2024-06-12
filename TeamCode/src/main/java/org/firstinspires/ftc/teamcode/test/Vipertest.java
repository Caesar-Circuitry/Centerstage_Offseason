package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Config
public class Vipertest extends LinearOpMode {
    Motor.Encoder liftLeftEncoder, liftRightEncoder;
    DcMotorEx liftMotorLeft, liftMotorRight;
    PIDFController LeftLiftController;
    PIDFController RightLiftController;
    public static double LP = .03, LI = 0, LD = 0, LF = 0, RP = .03, RI = 0, RD = 0, RF = 0;
    Motor.ZeroPowerBehavior ZERO_POWER_BEHAVIOR = Motor.ZeroPowerBehavior.BRAKE;
    double liftLeftTargetPos_ticks = 0, liftRightTargetPos_ticks = 0, LIFT_LEFT_TICKS_PER_IN = 113.285714, LIFT_Right_TICKS_PER_IN = 113.8 , LeftLiftPower = 0, RightLiftPower = 0, liftLeftLastPos_ticks = 0,liftRightLastPos_ticks = 0, prevLeftLiftPower = 0, prevRightLiftPower;
    public static double heightIN = 0;

    @Override
    public void runOpMode() throws InterruptedException {
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
            waitForStart();
            while (opModeIsActive()){
                    liftRunToPosition(heightIN, 1);
            }
    }
    public void liftRunToPosition(double pos_in, double speed_0to1) {
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

        prevLeftLiftPower = LeftLiftPower;
        prevRightLiftPower = RightLiftPower;
    }

}
