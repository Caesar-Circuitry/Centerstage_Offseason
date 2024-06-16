package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-36, 64, Math.toRadians(270)))
                .splineToConstantHeading(new Vector2d(-38,30),Math.toRadians(270))
                .waitSeconds(1)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-36,59,Math.PI),Math.toRadians(90))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(16,59),0)
                        .waitSeconds(1)
                        .setReversed(true)
                .splineToConstantHeading(new Vector2d(30,59),0)
                .splineToConstantHeading(new Vector2d(45,36),0)
                .waitSeconds(1)
                .strafeToConstantHeading(new Vector2d(45,12))
                .build());

//        BlueFrontLeft.runAction(BlueFrontLeft.getDrive().actionBuilder(new Pose2d(-36, 64, Math.toRadians(270)))
//                .splineToConstantHeading(new Vector2d(-38,37),Math.toRadians(270))
//                .strafeToConstantHeading(new Vector2d(-29,37))
//                .waitSeconds(1)
//                .strafeToConstantHeading(new Vector2d(-38,38))
//                .setReversed(true)
//                .splineToLinearHeading(new Pose2d(-36,59,Math.PI),Math.toRadians(90))
//                .setReversed(true)
//                .splineToConstantHeading(new Vector2d(30,59),0)
//                .splineToConstantHeading(new Vector2d(45,36),0)
//                .waitSeconds(1)
//                .strafeToConstantHeading(new Vector2d(45,12))
//                .build());
//        BlueFrontMiddle.runAction(BlueFrontMiddle.getDrive().actionBuilder(new Pose2d(-36, 64, Math.toRadians(270)))
//                .splineToConstantHeading(new Vector2d(-38,30),Math.toRadians(270))
//                .waitSeconds(1)
//                .setReversed(true)
//                .splineToLinearHeading(new Pose2d(-36,59,Math.PI),Math.toRadians(90))
//                .setReversed(true)
//                .splineToConstantHeading(new Vector2d(30,59),0)
//                .splineToConstantHeading(new Vector2d(45,36),0)
//                .waitSeconds(1)
//                .strafeToConstantHeading(new Vector2d(45,12))
//                .build());
//        BlueFrontRight.runAction(BlueFrontRight.getDrive().actionBuilder(new Pose2d(-36, 64, Math.toRadians(270)))
//                .splineToLinearHeading(new Pose2d(-40,40,Math.PI),Math.toRadians(270))
//                .waitSeconds(1)
//                .setReversed(true)
//                .splineToConstantHeading(new Vector2d(-36,59),Math.toRadians(0))
//                .setReversed(true)
//                .splineToConstantHeading(new Vector2d(30,59),0)
//                .splineToConstantHeading(new Vector2d(45,30),0)
//                .waitSeconds(1)
//                .strafeToConstantHeading(new Vector2d(45,12))
//                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}