package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous
public class OpenCvTest extends LinearOpMode {
    private VisionPortal visionPortal;
    private NewBlueRightProcessor colorMassDetectionProcessor;

    @Override
    public void runOpMode() throws InterruptedException {
        colorMassDetectionProcessor = new NewBlueRightProcessor();
        colorMassDetectionProcessor.setDetectionColor(false); //false is blue, true is red
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "camera"))
                .addProcessors(colorMassDetectionProcessor)
                .build();
        waitForStart();
        while (opModeIsActive()) {
            NewBlueRightProcessor.PropPositions recordedPropPosition = colorMassDetectionProcessor.getPropLocation();
            telemetry.addData("position", recordedPropPosition);
            telemetry.update();
        }

    }
}
