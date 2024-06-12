package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Config
public class ServoTest extends LinearOpMode {
    public static String servo = "";
    private Servo Servo;
    public static double pos1 = 0,pos2 = 0,pos3 = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        Servo = hardwareMap.get(Servo.class, servo);
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.b){
                Servo.setPosition(pos1);
            } else if (gamepad1.a) {
                Servo.setPosition(pos2);
            } else if (gamepad1.x) {
                Servo.setPosition(pos3);
            }
        }
    }
}
