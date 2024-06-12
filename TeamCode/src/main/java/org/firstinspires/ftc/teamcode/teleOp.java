package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

//@TeleOp
public class teleOp extends LinearOpMode {
    private DcMotorEx
            leftFront,leftBack,rightFront,rightBack,//drive motors
            liftMotorLeft, liftMotorRight, //viper Motors
            Intake;
    private Servo placer, boxRot, boxClip, Plane;
    private double placer0 = 0, placer1 = 1, boxRot0 = 0,boxRot1 = 1,boxClip0 = 0, boxclip1 = 1,Plane0 = 0,Plane1 = 1;
    private Motor.Encoder liftLeftEncoder, liftRightEncoder; // viper encoders
    private PIDFController LeftLiftController, RightLiftController; //PIDF controllers
    private final double LP = .03, LI = 0, LD = 0, LF = 0, RP = .03, RI = 0, RD = 0, RF = 0;
    private final Motor.ZeroPowerBehavior ZERO_POWER_BEHAVIOR = Motor.ZeroPowerBehavior.BRAKE;
    private double liftLeftTargetPos_ticks = 0, liftRightTargetPos_ticks = 0, LIFT_LEFT_TICKS_PER_IN = 113.285714 ,
    LIFT_Right_TICKS_PER_IN = 113.8 , LeftLiftPower = 0, RightLiftPower = 0, liftLeftLastPos_ticks = 0,liftRightLastPos_ticks = 0,
    prevLeftLiftPower = 0, prevRightLiftPower = 0;

    private double lvl0 = 0, lvl1 = 3, lvl2 = 6, lvl3 = 9, hang = 15, threshold = 2;
    private enum ViperPos{
        ZERO,
        ONE,
        TWO,
        Three,
        HANG
    }
    private ViperPos viperPos = ViperPos.ZERO;
    @Override
    public void runOpMode() throws InterruptedException {
        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
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
            drive();
            if(gamepad2.b || gamepad1.b){
                viperPos = ViperPos.ZERO;
            } else if (gamepad2.a) {
                viperPos = ViperPos.ONE;
            } else if (gamepad2.x) {
                viperPos = ViperPos.TWO;
            } else if (gamepad2.y) {
                viperPos = ViperPos.Three;
            } else if (gamepad1.a) {
                viperPos = ViperPos.HANG;
            }
            switch (viperPos){
                case ZERO:
                    liftRunToPosition(lvl0,1);
                    boxRot.setPosition(boxRot0);
                    boxClip.setPosition(boxclip1);
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
                case HANG:
                    liftRunToPosition(hang,1);
            }
            if(liftLeftEncoder.getPosition()>= threshold){
                boxRot.setPosition(boxRot1);
            } else if (liftLeftEncoder.getPosition()< threshold) {
                boxRot.setPosition(boxRot0);
            }

        }
    }
    public void drive(){
        double x = -gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double turn = -gamepad1.right_stick_x;

        double theta = (float)Math.atan2(y,x);
        double power = (float)Math.hypot(x,y);

        double sin = Math.sin(theta -Math.PI/4);
        double cos = Math.cos(theta -Math.PI/4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        double lf_power = power * cos/max + turn;
        double lb_power = power * sin/max + turn;
        double rf_power = power * sin/max - turn;
        double rb_power = power * cos/max - turn;

        if((power + Math.abs(turn)) > 1) {
            lf_power /= power + turn;
            lb_power /= power + turn;
            rf_power /= power + turn;
            rb_power /= power + turn;
        }

        leftFront.setPower(lf_power);
        leftBack.setPower(lb_power);
        rightFront.setPower(rf_power);
        rightBack.setPower(rb_power);

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
