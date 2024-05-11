package org.firstinspires.ftc.teamcode.robotSystems.SubSystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Outtake extends SubSystemParent{
    public enum ViperPos{
        ZERO,
        ONE,
        TWO,
        THREE,
        Hang    //Hang is a separate system but needs a specific center of mass so the viper at positionHang shifts center of mass for hang system
    }
    public enum boxClipPos{
        OPEN,
        CLOSE
    }
    private ViperPos TargetViperPos;
    private ViperPos ActualViperPos;
    private boxClipPos clipPos;
    public static int positionZERO = 0, positionONE = 1000, positionTWO = 2000, positionTHREE = 3000, positionHang = 3000;
    public static int Power = 1;
    public static double boxRotateHome = 0.0, boxRotateBoard = .75, BoxClipOpen = 1.0, BoxClipClose = 0.0;
    private DcMotor Viper;
    private Servo BoxRotate,BoxClip;
    public Outtake(HardwareMap hardwareMap){
        this.TargetViperPos = ViperPos.ZERO;
        this.Viper = hardwareMap.get(DcMotor.class, "Viper");
        this.BoxRotate = hardwareMap.get(Servo.class,"BoxRotate");
        this.BoxClip = hardwareMap.get(Servo.class,"BoxClip");
        this.Viper.setPower(0);
        this.Viper.setTargetPosition(positionZERO);
        this.Viper.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void switchViperState(ViperPos viperPos){
        this.TargetViperPos = viperPos;
    }
    public void switchBoxClipPos(boxClipPos clipPos){
        this.clipPos = clipPos;
    }
    @Override
    public void periodic() {
        this.Viper.setPower(Power);
        switch (TargetViperPos){
            case ZERO:
                this.BoxRotate.setPosition(boxRotateHome);
                this.BoxClip.setPosition(BoxClipOpen);
                this.switchBoxClipPos(boxClipPos.OPEN);
                this.Viper.setTargetPosition(positionZERO);
                if(this.Viper.getCurrentPosition() == positionZERO){
                    this.ActualViperPos = ViperPos.ZERO;
                    break;
                }
            case ONE:
                this.Viper.setTargetPosition(positionONE);
                this.BoxClip.setPosition(BoxClipClose);
                this.switchBoxClipPos(boxClipPos.CLOSE);
                if(this.Viper.getCurrentPosition() == positionONE){
                    this.BoxRotate.setPosition(boxRotateBoard);
                    this.ActualViperPos = ViperPos.ONE;
                    break;
                }
            case TWO:
                this.Viper.setTargetPosition(positionTWO);
                this.BoxClip.setPosition(BoxClipClose);
                this.switchBoxClipPos(boxClipPos.CLOSE);
                if(this.Viper.getCurrentPosition() == positionTWO){
                    this.BoxRotate.setPosition(boxRotateBoard);
                    this.ActualViperPos = ViperPos.TWO;
                    break;
                }
            case THREE:
                this.Viper.setTargetPosition(positionTHREE);
                this.BoxClip.setPosition(BoxClipClose);
                this.switchBoxClipPos(boxClipPos.CLOSE);
                if(this.Viper.getCurrentPosition() == positionTHREE){
                    this.BoxRotate.setPosition(boxRotateBoard);
                    this.ActualViperPos = ViperPos.THREE;
                    break;
                }
            case Hang:
                this.Viper.setTargetPosition(positionHang);
                this.BoxClip.setPosition(BoxClipClose);
                this.switchBoxClipPos(boxClipPos.CLOSE);
                if(this.Viper.getCurrentPosition() == positionTHREE) {
                    this.BoxRotate.setPosition(boxRotateHome);
                    this.ActualViperPos = ViperPos.Hang;
                    break;
                }
        }
        switch (clipPos){
            case OPEN:
                this.BoxClip.setPosition(BoxClipOpen);
                break;
            case CLOSE:
                this.BoxClip.setPosition(BoxClipClose);
                break;
        }
    }

    @Override
    public void reset() {
        this.switchViperState(ViperPos.ZERO);
    }

    public ViperPos getTargetViperPos() {
        return TargetViperPos;
    }

    public ViperPos getActualViperPos() {
        return ActualViperPos;
    }

    public static int getPositionZERO() {
        return positionZERO;
    }

    public static int getPositionONE() {
        return positionONE;
    }

    public static int getPositionTWO() {
        return positionTWO;
    }

    public static int getPositionTHREE() {
        return positionTHREE;
    }

    public DcMotor getViper() {
        return Viper;
    }

    public Servo getBoxRotate() {
        return BoxRotate;
    }

    public Servo getBoxClip() {
        return BoxClip;
    }

    public static int getPower() {
        return Power;
    }

    public static void setPositionZERO(int positionZERO) {
        Outtake.positionZERO = positionZERO;
    }

    public static void setPositionONE(int positionONE) {
        Outtake.positionONE = positionONE;
    }

    public static void setPositionTWO(int positionTWO) {
        Outtake.positionTWO = positionTWO;
    }

    public static void setPositionTHREE(int positionTHREE) {
        Outtake.positionTHREE = positionTHREE;
    }

    public static void setPower(int power) {
        Power = power;
    }

    public boxClipPos getClipPos() {
        return clipPos;
    }
}
