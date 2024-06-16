package org.firstinspires.ftc.teamcode.rrPaths.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class placer {
    private Servo placer;
    private double placerUp = .2, placerDown = .57;

    public placer(HardwareMap hardwareMap){
        placer = hardwareMap.get(Servo.class, "placer");
    }
    private class placerUP implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            placer.setPosition(placerUp);
            return false;
        }
    }
    public Action placerUp(){
        return new placerUP();
    }
    private class placerDown implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            placer.setPosition(placerDown);
            return false;
        }
    }
    public Action placerDown(){
        return new placerDown();
    }
}
