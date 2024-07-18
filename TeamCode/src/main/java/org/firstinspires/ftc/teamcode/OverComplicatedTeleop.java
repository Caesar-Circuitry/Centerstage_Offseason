package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class OverComplicatedTeleop extends LinearOpMode {
    private final double AKp = 0,AKi = 0,AKd = 0, /*Axial pid coefficients*/
    LKp = 0, LKi = 0, LKd = 0,/*lateral pid coefficients*/
    RKp = 0, RKi = 0, RKd = 0 /*rotational pid coefficients*/;
    PIDController xPid = new PIDController(AKp,AKi,AKd);
    PIDController yPid = new PIDController(LKp,LKi,LKd);
    PIDController rxPid = new PIDController(RKp, RKi, RKd);
    MecanumDrive drive;
    public double maxVel = 20; //inches per second
    Pose2d initPose = new Pose2d(0,0,0);
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new MecanumDrive(hardwareMap, initPose);
        waitForStart();
        while (opModeIsActive()){
            DrivePid();
        }
    }
    private void DrivePid(){
        double x = gamepad1.left_stick_x, y = gamepad1.left_stick_y, rx = gamepad1.right_stick_x;
        double desiredX = maxVel*x, desiredY = maxVel*y, desiredRX = maxVel*rx;
        Pose2d desiredPose = new Pose2d(drive.pose.position.x + desiredX,drive.pose.position.y + desiredY,drive.pose.heading.plus(desiredRX).log());
        double xPow, yPow, rxPow;
        xPow = xPid.calculate(drive.pose.position.x,desiredPose.position.x)/maxVel;
        yPow = yPid.calculate(drive.pose.position.y,desiredPose.position.y)/maxVel;
        rxPow = rxPid.calculate(drive.pose.heading.log(), desiredPose.heading.log())/maxVel;
        drive.setDrivePowers(new PoseVelocity2d(new Vector2d(xPow,yPow),rxPow));
    }
}
