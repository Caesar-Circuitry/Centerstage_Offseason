package org.firstinspires.ftc.teamcode.TeleOpCode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotSystems.ActionsFolder.SubSystemActions.HangActions;
import org.firstinspires.ftc.teamcode.robotSystems.ActionsFolder.SubSystemActions.IntakeActions;
import org.firstinspires.ftc.teamcode.robotSystems.ActionsFolder.SubSystemActions.OuttakeActions;
import org.firstinspires.ftc.teamcode.robotSystems.ActionsFolder.SubSystemActions.PlaneActions;

import java.util.ArrayList;
import java.util.List;

public class TeleOpWithRR extends LinearOpMode {
    HangActions hang = new HangActions(hardwareMap);
    IntakeActions intake = new IntakeActions(hardwareMap);
    OuttakeActions outtake = new OuttakeActions(hardwareMap);
    PlaneActions plane = new PlaneActions(hardwareMap);
    private List<Action> runningActions = new ArrayList<>();
    private FtcDashboard dash = FtcDashboard.getInstance();
    @Override
    public void runOpMode() throws InterruptedException {
    ActionUpdate();
    }
    public void ActionUpdate(){
        TelemetryPacket packet = new TelemetryPacket();
        List<Action> newActions = new ArrayList<>();
        for (Action action : runningActions) {
            action.preview(packet.fieldOverlay());
            if (action.run(packet)) {
                newActions.add(action);
            }
        }
        runningActions = newActions;
        dash.sendTelemetryPacket(packet);
    }
}
