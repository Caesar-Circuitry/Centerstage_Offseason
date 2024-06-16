package org.firstinspires.ftc.teamcode.rrPaths.red;

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
import org.firstinspires.ftc.teamcode.rrPaths.Actions.GlobalVars;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.ViperAuto;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.placer;
import org.firstinspires.ftc.teamcode.rrPaths.Actions.plane;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.teamcode.NewBlueRightProcessor;


@Config
@Autonomous(name = "Red Front Truss Park:Outside", group = "Red")
public class redFrontTrussOutside extends LinearOpMode {
    private VisionPortal visionPortal;
    private NewBlueRightProcessor colorMassDetectionProcessor;
    private placer Placer;
    private plane plane;
    private ViperAuto viperAuto;
    private GlobalVars vars;



    @Override
    public void runOpMode() {
        colorMassDetectionProcessor = new NewBlueRightProcessor();

        colorMassDetectionProcessor.setDetectionColor(true); //false is blue, true is red
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "camera"))
                .addProcessors(colorMassDetectionProcessor)
                .build();

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-36, -64, Math.toRadians(90)));
        Placer = new placer(hardwareMap);
        plane = new plane(hardwareMap);
        viperAuto = new ViperAuto(hardwareMap);
        vars.setViperPos(GlobalVars.ViperPos.ZERO);

        // vision here that outputs position
        int visionOutputPosition = 2;

        Action trajectoryLeftPurple;
        Action trajectoryLeftYellowMove;
        Action trajectoryLeftYellowPlace;
        Action trajectoryMiddlePurple;
        Action trajectoryMiddleYellowMove;
        Action trajectoryMiddleYellowPlace;
        Action trajectoryRightPurple;
        Action trajectoryRightYellowMove;
        Action trajectoryRightYellowPlace;
        Action wait15;
        Action wait1;
        Action wait05;
        Action trajectoryLeftCloseOut;
        Action trajectoryMiddleCloseOut;
        Action trajectoryRightCloseOut;

        trajectoryRightPurple = drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(-45,-34,0),Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-34,-34),0)
                .build();
        trajectoryRightYellowMove = drive.actionBuilder(new Pose2d(-34,-34,0))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-45,-34),180)
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-36,-57.5,Math.PI),Math.toRadians(0))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(16,-57.5),0)
                .build();
        trajectoryRightYellowPlace = drive.actionBuilder(new Pose2d(16,-57.5,Math.toRadians(180)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(49,-36),0)
                .build();

        trajectoryMiddlePurple = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(-38,-34),Math.toRadians(90))
                .build();
        trajectoryMiddleYellowMove = drive.actionBuilder(new Pose2d(-36,-34,Math.toRadians(90)))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-36,-57.5,Math.PI),Math.toRadians(270))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(16,-57.5),0)
                .build();
        trajectoryMiddleYellowPlace = drive.actionBuilder(new Pose2d(16,-57.5,Math.toRadians(180)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(49,-32),0)
                .build();

        trajectoryLeftPurple = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(-41,-41),Math.toRadians(90))
                .build();
        trajectoryLeftYellowMove = drive.actionBuilder(new Pose2d(-41,-41,Math.toRadians(90)))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-36,-57.5,Math.PI),Math.toRadians(270))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(16,-57.5),0)
                .build();
        trajectoryLeftYellowPlace = drive.actionBuilder(new Pose2d(16,-57.5,Math.PI))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(49,-23.5),0)
                .build();


        wait15 = drive.actionBuilder(drive.pose)
                .waitSeconds(1.5)
                .build();
        wait1 = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .build();
        wait05 = drive.actionBuilder(drive.pose)
                .waitSeconds(10)
                .build();

        trajectoryRightCloseOut = drive.actionBuilder(new Pose2d(49.5,-36,Math.PI))
                .waitSeconds(1)
                .lineToX(45)
                .strafeToConstantHeading(new Vector2d(44,-55))
                .build();
        trajectoryMiddleCloseOut = drive.actionBuilder(new Pose2d(49.5,-32,Math.PI))
                .waitSeconds(1)
                .lineToX(45)
                .strafeToConstantHeading(new Vector2d(44,-55))
                .build();
        trajectoryLeftCloseOut = drive.actionBuilder(new Pose2d(49.5,-23.5,Math.PI))
                .waitSeconds(1)
                .lineToX(45)
                .strafeToConstantHeading(new Vector2d(44,-55))
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
            trajectoryYellowPlaceChosen = trajectoryMiddleYellowPlace;
            trajectoryCloseOutChosen = trajectoryMiddleCloseOut;
        } else if (startPosition == 3) {
            trajectoryPurpleChosen = trajectoryRightPurple;
            trajectoryYellowMoveChosen = trajectoryRightYellowMove;
            trajectoryYellowPlaceChosen = trajectoryRightYellowPlace;
            trajectoryCloseOutChosen = trajectoryRightCloseOut;
        } else {
            trajectoryPurpleChosen = trajectoryLeftPurple;
            trajectoryYellowMoveChosen = trajectoryLeftYellowMove;
            trajectoryYellowPlaceChosen = trajectoryLeftYellowPlace;
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
                        viperAuto.moveViper()));
        telemetry.addLine("Didnt Closed Camera.");
        telemetry.update();
    }
}