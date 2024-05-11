package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot1 = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        RoadRunnerBotEntity myBot2 = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        RoadRunnerBotEntity myBot3 = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
        //pos left field centric
        myBot1.runAction(myBot1.getDrive().actionBuilder(new Pose2d(10, 62, Math.toRadians(-90)))
                .splineTo(new Vector2d(0, 30), Math.toRadians(180))
                .setTangent(Math.toRadians(180))
                .lineToX(5)
                .splineTo(new Vector2d(20,35),0)
                .lineToX(50)
                //loop code
                .lineToX(45)
                .splineTo(new Vector2d(12,10),Math.toRadians(180))
                .lineToX(-60)
                .lineToX(12)
                .splineTo(new Vector2d(50,35),0)
                //loop 2x
                .lineToX(45)
                .splineTo(new Vector2d(12,10),Math.toRadians(180))
                .lineToX(-60)
                .lineToX(12)
                .splineTo(new Vector2d(50,35),0)
                .build());
        //pos middle field centric
        myBot2.runAction(myBot2.getDrive().actionBuilder(new Pose2d(10, 62, Math.toRadians(-90)))
                .lineToY(25)
                .lineToY(30)
                .splineTo(new Vector2d(20,35),0)
                .lineToX(50)
                //loop code
                .lineToX(45)
                .splineTo(new Vector2d(12,10),Math.toRadians(180))
                .lineToX(-60)
                .lineToX(12)
                .splineTo(new Vector2d(50,35),0)
                //loop 2x
                .lineToX(45)
                .splineTo(new Vector2d(12,10),Math.toRadians(180))
                .lineToX(-60)
                .lineToX(12)
                .splineTo(new Vector2d(50,35),0)
                .build());
        myBot3.runAction(myBot3.getDrive().actionBuilder(new Pose2d(10, 62, Math.toRadians(-90)))
                .splineToLinearHeading(new Pose2d(50,35,Math.toRadians(180)),0)
                .lineToX(49)
                .splineToSplineHeading(new Pose2d(35,30,Math.toRadians(180)),0)
                //loop code
                //.lineToX(45)
                .splineToConstantHeading(new Vector2d(12,10),Math.toRadians(180))
                .lineToX(-60)
                .lineToX(12)
                .splineTo(new Vector2d(50,35),0)
                //loop 2x
                .lineToX(45)
                .splineTo(new Vector2d(12,10),Math.toRadians(180))
                .lineToX(-60)
                .lineToX(12)
                .splineTo(new Vector2d(50,35),0)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                //.addEntity(myBot1)
                //.addEntity(myBot2)
                .addEntity(myBot3)
                .start();
    }
}