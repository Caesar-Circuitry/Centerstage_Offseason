package org.firstinspires.ftc.teamcode.rrPaths.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class plane {
    private Servo plane;
    private double planeInit = .95;
    public plane(HardwareMap hardwareMap){
        plane = hardwareMap.get(Servo.class,"plane");
    }
    private class planeInit implements Action{
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            plane.setPosition(planeInit);
            return false;
        }
    }
    public Action planeInit(){
        return new planeInit();
    }
}
