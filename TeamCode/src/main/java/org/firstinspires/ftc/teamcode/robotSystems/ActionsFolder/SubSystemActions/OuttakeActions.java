package org.firstinspires.ftc.teamcode.robotSystems.ActionsFolder.SubSystemActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robotSystems.SubSystems.Outtake;

public class OuttakeActions {
    Outtake outtake;
    public OuttakeActions(HardwareMap hardwareMap){
        outtake = new Outtake(hardwareMap);
    }
    public class bringToZero implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtake.switchViperState(Outtake.ViperPos.ZERO);
            outtake.periodic();
           return outtake.getActualViperPos()== Outtake.ViperPos.ZERO;
        }
    }
    public Action bringToZero(){
        return new bringToZero();
    }
    public class bringToOne implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtake.switchViperState(Outtake.ViperPos.ONE);
            outtake.periodic();
            return outtake.getActualViperPos() == Outtake.ViperPos.ONE;
        }
    }
    public Action bringToOne(){
        return new bringToOne();
    }
    public class bringToTwo implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtake.switchViperState(Outtake.ViperPos.TWO);
            outtake.periodic();
            return outtake.getActualViperPos() == Outtake.ViperPos.ONE;
        }
    }
    public Action bringToTwo(){
        return new bringToTwo();
    }
    public class bringToThree implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtake.switchViperState(Outtake.ViperPos.ONE);
            outtake.periodic();
            return outtake.getActualViperPos() == Outtake.ViperPos.ONE;
        }
    }
    public Action bringToThree(){
        return new bringToThree();
    }
    public class openBox implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtake.switchBoxClipPos(Outtake.boxClipPos.OPEN);
            return outtake.getClipPos() == Outtake.boxClipPos.OPEN;
        }
    }
    public Action openBox(){
        return new openBox();
    }
    public class closeBox implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtake.switchBoxClipPos(Outtake.boxClipPos.CLOSE);
            return outtake.getClipPos() == Outtake.boxClipPos.CLOSE;
        }
    }
    public Action closeBox(){
        return new closeBox();
    }
    public class HangCounterBalence implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            outtake.switchViperState(Outtake.ViperPos.Hang);
            outtake.periodic();
            return outtake.getActualViperPos() == Outtake.ViperPos.Hang;
        }
    }
}
