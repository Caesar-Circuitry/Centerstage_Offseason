package org.firstinspires.ftc.teamcode.rrPaths.red;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.GlobalVars;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.ViperAuto;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.placer;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.plane;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.teamcode.NewBlueRightProcessor;


@Config
@Autonomous(name = "Red Back Park:Inside", group = "Red")
public class redBackInside extends LinearOpMode {
    private VisionPortal visionPortal;
    private NewBlueRightProcessor colorMassDetectionProcessor;
    placer Placer;
    plane plane;
    ViperAuto viperAuto;
    GlobalVars vars;





    @Override
    public void runOpMode() {
        colorMassDetectionProcessor = new NewBlueRightProcessor();

        colorMassDetectionProcessor.setDetectionColor(true); //false is blue, true is red
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "camera"))
                .addProcessors(colorMassDetectionProcessor)
                .build();

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(12, -64, Math.toRadians(90)));
        Placer = new placer(hardwareMap);
        plane = new plane(hardwareMap);
        viperAuto = new ViperAuto(hardwareMap);
        vars.setViperPos(GlobalVars.ViperPos.ZERO);

        // vision here that outputs position
        int visionOutputPosition = 2;

        Action trajectoryLeftPurple;
        Action trajectoryLeftYellow;
        Action trajectoryMiddlePurple;
        Action trajectoryMiddleYellow;
        Action trajectoryRightPurple;
        Action trajectoryRightYellow;
        Action wait15;
        Action wait1;
        Action wait05;
        Action trajectoryLeftCloseOut;
        Action trajectoryMiddleCloseOut;
        Action trajectoryRightCloseOut;

        trajectoryRightPurple = drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(32,-29,Math.PI),Math.toRadians(90))
                .build();
        trajectoryRightYellow = drive.actionBuilder(new Pose2d(32,-29,Math.PI))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(49.5,-43),0)
                .build();

        trajectoryMiddlePurple = drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(19,-18.5,Math.PI), Math.toRadians(90))
                .build();
        trajectoryMiddleYellow = drive.actionBuilder(new Pose2d(17,-18.5,Math.PI))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(49,-35.5), 0)
                .build();

        trajectoryLeftPurple = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(14,-37),Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(6,-37))
                .build();
        trajectoryLeftYellow = drive.actionBuilder(new Pose2d(6,-37,Math.toRadians(90)))
                .strafeToConstantHeading(new Vector2d(14,-37))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(48.5,-29,Math.PI),Math.toRadians(90))

                .build();

        wait15 = drive.actionBuilder(drive.pose)
                .waitSeconds(1.5)
                .build();
        wait1 = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .build();
        wait05 = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();

        trajectoryRightCloseOut = drive.actionBuilder(new Pose2d(49.5,-43,Math.PI))
                .waitSeconds(1)
                .lineToX(45)
                .strafeToConstantHeading(new Vector2d(45,-12))
                .build();
        trajectoryMiddleCloseOut = drive.actionBuilder(new Pose2d(49,-35.5,Math.PI))
                .waitSeconds(1)
                .lineToX(45)
                .strafeToConstantHeading(new Vector2d(45,-12))
                .build();
        trajectoryLeftCloseOut = drive.actionBuilder(new Pose2d(48.5,-29,Math.PI))
                .waitSeconds(1)
                .lineToX(45)
                .strafeToConstantHeading(new Vector2d(45,-12))
                .build();


        while (!isStopRequested() && !opModeIsActive()) {
            int position = visionOutputPosition;
            telemetry.addData("Currently Recorded Position: ", colorMassDetectionProcessor.getPropLocation());
            telemetry.addData("Camera State: ", visionPortal.getCameraState());
            telemetry.addData("Position during Init", position);
            FtcDashboard.getInstance().startCameraStream(colorMassDetectionProcessor, 30);
            telemetry.update();
        }
        NewBlueRightProcessor.PropPositions recordedPropPosition = colorMassDetectionProcessor.getPropLocation();
        switch (recordedPropPosition) {
            case LEFT:
                visionOutputPosition = 1;
                break;
            case MIDDLE:
                visionOutputPosition = 2;
                break;
            case RIGHT:
                visionOutputPosition = 3;
                break;
        }

        int startPosition = visionOutputPosition;
        telemetry.addData("Starting Position", startPosition);
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryPurpleChosen;
        Action trajectoryYellowChosen;
        Action trajectoryCloseOutChosen;

        if (startPosition == 2) {
            trajectoryPurpleChosen = trajectoryMiddlePurple;
            trajectoryYellowChosen = trajectoryMiddleYellow;
            trajectoryCloseOutChosen = trajectoryMiddleCloseOut;
        } else if (startPosition == 3) {
            trajectoryPurpleChosen = trajectoryRightPurple;
            trajectoryYellowChosen = trajectoryRightYellow;
            trajectoryCloseOutChosen = trajectoryRightCloseOut;
        } else {
            trajectoryPurpleChosen = trajectoryLeftPurple;
            trajectoryYellowChosen = trajectoryLeftYellow;
            trajectoryCloseOutChosen = trajectoryLeftCloseOut;
        }
        
        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                plane.planeInit(),
                                Placer.placerDown(),
                                viperAuto.boxClipClose(),
                                trajectoryPurpleChosen,
                                Placer.placerUp(),
                                viperAuto.viperYellow(),
                                trajectoryYellowChosen,
                                viperAuto.boxRotateBoard(),
                                wait1,
                                viperAuto.boxClipOpen(),
                                wait1,
                                trajectoryCloseOutChosen,
                                viperAuto.boxRotateHome(),
                                wait1,
                                viperAuto.viper0()

                        ),
                        viperAuto.moveViper()));

        telemetry.addLine("Didnt Closed Camera.");
        telemetry.update();
    }
}