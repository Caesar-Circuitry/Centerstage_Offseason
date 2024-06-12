package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp
@Config
public class IntakeTest extends LinearOpMode {
    private DcMotor intake;
    public static boolean Reversed = false, gamepad = true, movFow = false,movRev = false;
    public static double powRev = -1,powFow = 1;
    @Override
    public void runOpMode() throws InterruptedException {
        intake = hardwareMap.get(DcMotor.class,"intake");
        waitForStart();
        while (opModeIsActive()) {
            if (Reversed) {
                intake.setDirection(DcMotorSimple.Direction.REVERSE);
            }else{
                intake.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            if(gamepad){
                if (gamepad1.right_trigger>0){
                    intake.setPower(powFow);
                } else if (gamepad1.left_trigger>0) {
                    intake.setPower(powRev);
                }else{
                    intake.setPower(0);
                }
            }else{
                if (movFow){
                    intake.setPower(powFow);
                } else if (movRev) {
                    intake.setPower(powRev);
                }else{
                    intake.setPower(0);
                }
            }

        }
    }
}
