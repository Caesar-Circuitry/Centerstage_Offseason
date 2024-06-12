package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
@Config
public class TickReader extends LinearOpMode {
    public static String Encoder1 = "leftLiftMotor", Encoder2 = "rightLiftMotor";
    public static Motor encoder1, encoder2;
    public static double tick1 = 0, tick2 = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        encoder1 = new Motor(hardwareMap,Encoder1, Motor.GoBILDA.RPM_312);
        encoder2 = new Motor(hardwareMap,Encoder2, Motor.GoBILDA.RPM_312);
        encoder1.stopAndResetEncoder();
        encoder2.stopAndResetEncoder();
        FtcDashboard dash = FtcDashboard.getInstance();
        Telemetry dashTel =dash.getTelemetry();
        waitForStart();
        while (opModeIsActive()) {
            tick1 = encoder1.getDistance();
            tick2 = encoder2.getDistance();
            dashTel.addData("Ticks traveled enc1", tick1);
            dashTel.addData("Ticks traveled enc2", tick2);
            dashTel.update();
            telemetry.addData("Ticks traveled enc1", tick1);
            telemetry.addData("Ticks traveled enc2", tick2);
            telemetry.update();
        }
    }
}
