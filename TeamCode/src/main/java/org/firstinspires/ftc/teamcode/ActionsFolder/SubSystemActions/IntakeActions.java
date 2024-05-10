package org.firstinspires.ftc.teamcode.ActionsFolder.SubSystemActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class IntakeActions {
    Intake intake;

    public IntakeActions(HardwareMap hardwareMap){
        intake = new Intake(hardwareMap);
    }

    public class switchIntaking implements Action {
        Intake.intakePow pow;
        Intake.intakePow targetPow;
        boolean changed = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            switch (pow){
                case OFF:
                    targetPow = Intake.intakePow.ON;
                    break;
                case ON:
                    targetPow = Intake.intakePow.OFF;
            }
            intake.switchState(targetPow);
            changed = intake.getPowerState()==targetPow;
            if(changed){
                intake.periodic();
            }
            return changed;
        }
    }
    public Action switchIntaking(){
        return new switchIntaking();
    }
    public class switchDir implements Action{
        Intake.intakeDIR dir;
        Intake.intakeDIR targetDir;
        boolean changed = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            dir = intake.getDirection();
            switch (dir) {
                case REVERSE:
                    targetDir = Intake.intakeDIR.FORWARD;
                    break;
                case FORWARD:
                    targetDir = Intake.intakeDIR.REVERSE;
                    break;
            }
            intake.switchState(targetDir);
            changed = intake.getDirection() == targetDir;
            if(changed){
                intake.periodic();
            }
            return changed;
        }
    }
    public Action switchDir(){
        return new switchDir();
    }
    public class switchDirAndPow implements Action{
        boolean changed = false;
        Intake.intakeDIR dir;
        Intake.intakeDIR targetDir;
        Intake.intakePow pow;
        Intake.intakePow targetPow;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            dir = intake.getDirection();
            pow = intake.getPowerState();
            switch (dir) {
                case REVERSE:
                    targetDir = Intake.intakeDIR.FORWARD;
                    break;
                case FORWARD:
                    targetDir = Intake.intakeDIR.REVERSE;
                    break;
            }
            switch (pow){
                case OFF:
                    targetPow = Intake.intakePow.ON;
                    break;
                case ON:
                    targetPow = Intake.intakePow.OFF;
            }
            intake.switchState(targetDir,targetPow);
            changed = ((intake.getDirection() == targetDir) && (intake.getPowerState()==targetPow));
            if(changed){
                intake.periodic();
            }
            return changed;
        }
    }
    public Action switchDirAndPow(){
        return new switchDirAndPow();
    }
    public class AutoDown implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            return false;
        }
    }
}
