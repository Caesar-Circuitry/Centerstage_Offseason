package org.firstinspires.ftc.teamcode.robotSystems.ActionsFolder.SubSystemActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robotSystems.SubSystems.Hang;

public class HangActions {
    Hang hang;
    public HangActions(HardwareMap hardwareMap){
        hang = new Hang(hardwareMap);
    }
    public class Fire implements Action{
        boolean changed = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            hang.switchState(Hang.hangState.FIRED);
            changed = (hang.getTargetState() == Hang.hangState.FIRED);
            if(changed){
                hang.periodic();
            }
            return hang.getActualState() == Hang.hangState.FIRED;
        }
    }
    public Action Fire(){
        return new Fire();
    }
    public class Arm implements Action{
        boolean changed = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            hang.switchState(Hang.hangState.ARMED);
            changed = (hang.getTargetState() == Hang.hangState.ARMED);
            if(changed){
                hang.periodic();
            }
            return hang.getActualState() == Hang.hangState.ARMED;
        }
    }
    public Action Arm(){
        return new Arm();
    }
}
