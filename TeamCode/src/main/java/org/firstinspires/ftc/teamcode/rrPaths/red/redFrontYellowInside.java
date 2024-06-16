package org.firstinspires.ftc.teamcode.rrPaths.red;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.teamcode.NewBlueRightProcessor;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.placer;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.plane;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.ViperAuto;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.GlobalVars;


@Config
@Autonomous(name = "Red Front Yellow Park:Inside", group = "Red")
public class redFrontYellowInside extends LinearOpMode {
    private VisionPortal visionPortal;
    private NewBlueRightProcessor colorMassDetectionProcessor;
    private placer Placer;
    private plane plane;
    private ViperAuto viperAuto;
    private GlobalVars vars;
    @Override
    public void runOpMode() {
        colorMassDetectionProcessor = new NewBlueRightProcessor();
        colorMassDetectionProcessor.setDetectionColor(false); //false is blue, true is red
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "camera"))
                .addProcessors(colorMassDetectionProcessor)
                .build();

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-36, -64, Math.toRadians(-270)));
        Placer = new placer(hardwareMap);
        plane = new plane(hardwareMap);
        viperAuto = new ViperAuto(hardwareMap);



        // vision here that outputs position
        int visionOutputPosition = 2;

        Action trajectoryLeftPurple;
        Action trajectoryLeftYellowMove;
        Action trajectoryLeftYellowPark;
        Action trajectoryMiddlePurple;
        Action trajectoryMiddleYellowMove;
        Action trajectoryMiddleYellowPark;
        Action trajectoryRightPurple;
        Action trajectoryRightYellowMove;
        Action trajectoryRightYellowPark;
        Action wait15;
        Action wait1;
        Action wait05;
        Action trajectoryLeftCloseOut;
        Action trajectoryMiddleCloseOut;
        Action trajectoryRightCloseOut;

        trajectoryLeftPurple = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(-36,-40),Math.toRadians(-270))
                .strafeToConstantHeading(new Vector2d(-25,-40))
                .build();
        trajectoryLeftYellowMove = drive.actionBuilder(new Pose2d(-25,-40,Math.toRadians(-270)))
                .strafeToConstantHeading(new Vector2d(-36,-40))
                .splineToLinearHeading(new Pose2d(-38,-12,Math.PI), Math.toRadians(-270))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(35,-12),0)
                .build();
        trajectoryLeftYellowPark = drive.actionBuilder(new Pose2d(35,-12,Math.toRadians(0)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(49,-42), 0)
                .build();

        trajectoryMiddlePurple = drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(-37,-15,Math.toRadians(-90)),Math.toRadians(-270))
                .build();
        trajectoryMiddleYellowMove = drive.actionBuilder(new Pose2d(-37,14,Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(-36,-12,-Math.PI),Math.toRadians(-90))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(35,-12),0)
                .build();
        trajectoryMiddleYellowPark = drive.actionBuilder(new Pose2d(35,-12,Math.toRadians(0)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(49,-36), 0)
                .build();

        trajectoryRightPurple = drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(-42,-16,Math.toRadians(-90)),Math.toRadians(-270))
                .build();
        trajectoryRightYellowMove = drive.actionBuilder(new Pose2d(-42,-16,Math.toRadians(-90)))
                .setReversed(true)
                .splineTo(new Vector2d(-30,-12),0)
                .splineToConstantHeading(new Vector2d(35,-12),0)
                .build();
        trajectoryRightYellowPark = drive.actionBuilder(new Pose2d(35,-12,-Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(49,-32),0)
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

        trajectoryLeftCloseOut = drive.actionBuilder(new Pose2d(49,-42,-Math.PI))
                .waitSeconds(1)
                .lineToX(49)
                .strafeToConstantHeading(new Vector2d(49,-14))
                .build();
        trajectoryMiddleCloseOut = drive.actionBuilder(new Pose2d(49,-36,-Math.PI))
                .waitSeconds(1)
                .lineToX(49)
                .strafeToConstantHeading(new Vector2d(49,14))
                .build();
        trajectoryRightCloseOut = drive.actionBuilder(new Pose2d(49,-30,-Math.PI))
                .waitSeconds(1)
                .lineToX(49)
                .strafeToConstantHeading(new Vector2d(49,-14))
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
        Action trajectoryYellowMoveChosen;
        Action trajectoryYellowPlaceChosen;
        Action trajectoryCloseOutChosen;

        if (startPosition == 2) {
            trajectoryPurpleChosen = trajectoryMiddlePurple;
            trajectoryYellowMoveChosen = trajectoryMiddleYellowMove;
            trajectoryYellowPlaceChosen = trajectoryMiddleYellowPark;
            trajectoryCloseOutChosen = trajectoryMiddleCloseOut;
        } else if (startPosition == 3) {
            trajectoryPurpleChosen = trajectoryRightPurple;
            trajectoryYellowMoveChosen = trajectoryRightYellowMove;
            trajectoryYellowPlaceChosen = trajectoryRightYellowPark;
            trajectoryCloseOutChosen = trajectoryRightCloseOut;
        } else {
            trajectoryPurpleChosen = trajectoryLeftPurple;
            trajectoryYellowMoveChosen = trajectoryLeftYellowMove;
            trajectoryYellowPlaceChosen = trajectoryLeftYellowPark;
            trajectoryCloseOutChosen = trajectoryLeftCloseOut;
        }
        Actions.runBlocking(new SequentialAction(plane.planeInit(), Placer.placerDown()));

        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                plane.planeInit(),
                                Placer.placerDown(),
                                viperAuto.boxClipClose(),
                                trajectoryPurpleChosen,
                                Placer.placerUp(),
                                trajectoryYellowMoveChosen,
                                new ParallelAction(
                                        trajectoryYellowPlaceChosen,
                                        new SequentialAction(
                                                viperAuto.viperYellow(),
                                                wait1,
                                                viperAuto.boxRotateBoard()
                                        )),
                                wait1,
                                viperAuto.boxClipOpen(),
                                wait15,
                                trajectoryCloseOutChosen,
                                viperAuto.boxRotateHome(),
                                viperAuto.viper0()

                        ),
                        viperAuto.moveViper())
        );
        telemetry.addLine("Didnt Closed Camera.");
        telemetry.update();
    }
}