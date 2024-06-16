package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;

import javax.crypto.ExemptionMechanism;

@TeleOp
public class RRteleOp extends LinearOpMode {
    private DcMotorEx
            liftMotorLeft, liftMotorRight, //viper Motors
            Intake;
    private Servo placer, boxRot, boxClip, Plane;
    private double placerDown = .57, placerUp = .2, BoxRotateHome = .39,BoxRotateBoard = .9,BoxClipClosed = .4, BoxClipOpen = .65,PlaneArmed = .95,PlaneFired = .75;
    private Motor.Encoder liftLeftEncoder, liftRightEncoder; // viper encoders
    private PIDFController LeftLiftController, RightLiftController; //PIDF controllers
    private final double LP = .005, LI = 0, LD = 0, LF = 0, RP = .005, RI = 0, RD = 0, RF = 0;
    private final Motor.ZeroPowerBehavior ZERO_POWER_BEHAVIOR = Motor.ZeroPowerBehavior.BRAKE;
    private double liftLeftTargetPos_ticks = 0, liftRightTargetPos_ticks = 0, LIFT_LEFT_TICKS_PER_IN = 113.285714 ,
    LIFT_Right_TICKS_PER_IN = 113.8 , LeftLiftPower = 0, RightLiftPower = 0, liftLeftLastPos_ticks = 0,liftRightLastPos_ticks = 0,
    prevLeftLiftPower = 0, prevRightLiftPower = 0;

    private double lvl0 = 0, lvl1 = 13, lvl2 = 16, lvl3 = 24, hangUp = 19, hangDown = 5, thresholdUp = 12;
    private boolean bPressed = false;
    private enum ViperPos{
        ZERO,
        ONE,
        TWO,
        Three,
        hangUp,
        hangDown
    }
    private ViperPos viperPos = ViperPos.ZERO;
    
    MecanumDrive drive;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        LeftLiftController = new PIDFController(LP, LI, LD, LF);
        RightLiftController = new PIDFController(RP,RI,RD,RF);
        liftLeftEncoder = new Motor(hardwareMap,"leftViper", Motor.GoBILDA.RPM_312).encoder;
        liftRightEncoder = new Motor(hardwareMap, "rightViper", Motor.GoBILDA.RPM_312).encoder;

        liftMotorLeft = hardwareMap.get(DcMotorEx.class, "leftViper");
        liftMotorLeft.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR.getBehavior());
        liftMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        liftMotorRight = hardwareMap.get(DcMotorEx.class, "rightViper");
        liftMotorRight.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR.getBehavior());
        liftMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        placer = hardwareMap.get(Servo.class, "placer");
        Plane = hardwareMap.get(Servo.class, "plane");
        boxClip = hardwareMap.get(Servo.class, "boxClip");
        boxRot = hardwareMap.get(Servo.class, "boxRotate");
        Intake = hardwareMap.get(DcMotorEx.class, "intake");;

        waitForStart();
        Plane.setPosition(PlaneArmed);
        while (opModeIsActive()){
            drive();
            if(gamepad1.b){
                viperPos = ViperPos.ZERO;
            } else if (gamepad1.a) {
                boxClip.setPosition(BoxClipClosed);
                viperPos = ViperPos.ONE;
            } else if (gamepad1.x) {
                boxClip.setPosition(BoxClipClosed);
                viperPos = ViperPos.TWO;
            } else if (gamepad1.y) {
                boxClip.setPosition(BoxClipClosed);
                viperPos = ViperPos.Three;
            } else if (gamepad2.a) {
                viperPos = ViperPos.hangUp;
            } else if (gamepad2.b) {
                viperPos = ViperPos.hangDown;
            }
            switch (viperPos){
                case ZERO:
                    liftRunToPosition(lvl0,1);
                    boxRot.setPosition(BoxRotateHome);
                    break;
                case ONE:
                    liftRunToPosition(lvl1,1);
                    break;
                case TWO:
                    liftRunToPosition(lvl2, 1);
                    break;
                case Three:
                    liftRunToPosition(lvl3, 1);
                    break;
                case hangUp:
                    liftRunToPosition(hangUp,1);
                    break;
                case hangDown:
                    liftRunToPosition(hangDown, 1);
            }
            if(liftLeftEncoder.getPosition()> thresholdUp * LIFT_LEFT_TICKS_PER_IN){
                boxRot.setPosition(BoxRotateBoard);
            } else if (liftLeftEncoder.getPosition() <= thresholdUp * LIFT_LEFT_TICKS_PER_IN) {
                boxRot.setPosition(BoxRotateHome);
            }
            if(gamepad1.left_trigger>0){
                Intake.setPower(-1);
            } else if (gamepad1.right_trigger>0) {
                Intake.setPower(1);
            }else{
                Intake.setPower(0);
            }
            if(gamepad2.left_bumper){
                Plane.setPosition(PlaneArmed);
            } else if (gamepad2.right_bumper) {
                Plane.setPosition(PlaneFired);
            }
            if (gamepad1.left_bumper){
                boxClip.setPosition(BoxClipClosed);
            } else if (gamepad1.right_bumper) {
                boxClip.setPosition(BoxClipOpen);

            }
        }
    }
    public void drive(){
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x
                ),
                -gamepad1.right_stick_x
        ));
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
