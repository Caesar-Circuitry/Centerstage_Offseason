package org.firstinspires.ftc.teamcode.ActionsFolder.SubSystemActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.SubSystems.Plane;

public class PlaneActions {
    Plane plane;
    public PlaneActions(HardwareMap hardwareMap){
        this.plane = new Plane(hardwareMap);
    }
    public class Fire implements Action{
        boolean changed = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            plane.switchState(Plane.planeState.FIRED);
            changed = plane.getTargetState() == Plane.planeState.FIRED;
            if(changed){
                plane.periodic();
            }
            return plane.getActualState() == Plane.planeState.FIRED;
        }
    }
    public Action Fire(){
        return new Fire();
    }
    public class Arm implements Action{
        boolean changed = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            plane.switchState(Plane.planeState.ARMED);
            changed = plane.getTargetState() == Plane.planeState.ARMED;
            if(changed){
                plane.periodic();
            }
            return plane.getActualState() == Plane.planeState.ARMED;
        }
    }
    public Action Arm(){
        return new Arm();
    }
}
