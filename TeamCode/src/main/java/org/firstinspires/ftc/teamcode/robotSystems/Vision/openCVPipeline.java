package org.firstinspires.ftc.teamcode.robotSystems.Vision;

import org.firstinspires.ftc.teamcode.robotSystems.GlobalVars.GlobalVars;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class openCVPipeline extends OpenCvPipeline{
    public static double
            MINIMUM_Value = 100,
            MAXIMUM_Value = 255,
            MINIMUM_BLUE_HUE = 100,
            MAXIMUM_BLUE_HUE = 115,
            MINIMUM_RED_LOW_HUE = 0,
            MAXIMUM_RED_LOW_HUE = 25,
            MINIMUM_RED_HIGH_HUE = 160,
            MAXIMUM_RED_HIGH_HUE = 255;

    Mat mat = new Mat();

    static final Rect Left_ROI = new Rect(new Point(10,100), new Point(105,200));
    static final Rect Middle_ROI = new Rect(new Point(120,100), new Point(205,200));
    static final Rect Right_ROI = new Rect(new Point(220,100), new Point(310,200));
    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        Scalar
                MINIMUM_BLUE = new Scalar(MINIMUM_BLUE_HUE,MINIMUM_Value,MINIMUM_Value),
                MAXIMUM_BLUE = new Scalar(MAXIMUM_BLUE_HUE, MAXIMUM_Value, MAXIMUM_Value),
                MINIMUM_RED_LOW = new Scalar(MINIMUM_RED_LOW_HUE, MINIMUM_Value,MINIMUM_Value),
                MAXIMUM_RED_LOW = new Scalar(MAXIMUM_RED_LOW_HUE, MAXIMUM_Value, MAXIMUM_Value),
                MINIMUM_RED_HIGH = new Scalar(MINIMUM_RED_HIGH_HUE, MINIMUM_Value,MINIMUM_Value),
                MAXIMUM_RED_HIGH = new Scalar(MAXIMUM_RED_HIGH_HUE, MAXIMUM_Value, MAXIMUM_Value);

        if(GlobalVars.COLOR == GlobalVars.Color.BLUE){
            Core.inRange(mat,MINIMUM_BLUE,MAXIMUM_BLUE, mat);
        }
        else{
            Mat mat1 = mat.clone(),
                    mat2 = mat.clone();
            Core.inRange(mat1,MINIMUM_RED_LOW, MAXIMUM_RED_LOW, mat1);
            Core.inRange(mat2, MINIMUM_RED_HIGH, MAXIMUM_RED_HIGH, mat2);
            Core.bitwise_or(mat1,mat2,mat);
        }
        Mat left = mat.submat(Left_ROI);
        Mat right = mat.submat(Right_ROI);
        Mat middle = mat.submat(Middle_ROI);

        double
                leftValue  = Core.sumElems(left).val[0],
                rightValue  = Core.sumElems(right).val[0],
                middleValue  = Core.sumElems(middle).val[0];

        if(leftValue >= rightValue && leftValue >= middleValue){
            GlobalVars.TEAMPROPLOC = GlobalVars.teamPropLoc.LEFT;
        } else if (rightValue>= middleValue) {
            GlobalVars.TEAMPROPLOC = GlobalVars.teamPropLoc.RIGHT;
        } else{
            GlobalVars.TEAMPROPLOC = GlobalVars.teamPropLoc.CENTER;
        }


        // draws rectangles

        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_GRAY2RGB);
        Scalar
                pixelColor = new Scalar(255,255,255),
                propColor = new Scalar(0,0,255);

        //ternary operator ? is a mini if statement
        Imgproc.rectangle(mat,Left_ROI, GlobalVars.TEAMPROPLOC == GlobalVars.teamPropLoc.LEFT? pixelColor:propColor);
        Imgproc.rectangle(mat,Middle_ROI, GlobalVars.TEAMPROPLOC == GlobalVars.teamPropLoc.CENTER? pixelColor:propColor);
        Imgproc.rectangle(mat,Right_ROI, GlobalVars.TEAMPROPLOC == GlobalVars.teamPropLoc.RIGHT? pixelColor:propColor);

        return mat;
    }
}