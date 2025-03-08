package hardware;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;

@Config
public class Globals {
    public enum OpModeType {
        AUTO,
        TELEOP
    }

    public enum AllianceColor {
        RED,
        BLUE
    }

    public enum PoseLocationName {
        BLUE_BUCKET,
        BLUE_OBSERVATION,
        RED_BUCKET,
        RED_OBSERVATION
    }

    public enum SCORING {
        HIGH_BUCKET,
        LOW_BUCKET,
        HIGH_SPECIMEN
    }

    public enum INTAKE {
        TAKING,
        EJECTING
    }

    public static SCORING scoring;

    public static OpModeType opModeType;
    public static AllianceColor allianceColor;
    public static PoseLocationName poseLocationName;

    public static Pose subSample1 = new Pose(62.000, 93.700, Math.toRadians(90));
    public static Pose subSample2 = new Pose(62.000, 93.700, Math.toRadians(90));
    public static Pose autoEndPose = new Pose(0, 0, Math.toRadians(0));

    // Robot Width and Length (in inches)
//    public static double ROBOT_WIDTH = 13.4;
//    public static double ROBOT_LENGTH = 14.56;

    public static double MID_SERVO = 0.5;

    // Intake Motor
//    public static double INTAKE_FORWARD_SPEED = 1.0;
//    public static double INTAKE_REVERSE_SPEED = -0.5;
//    public static double INTAKE_STOP = 0;
//    public static double INTAKE_HOLD_SPEED = 0.15;
//    public static int REVERSE_TIME_MS = 300;

    // Intake Color Sensor
    public static double MIN_DISTANCE_THRESHOLD = 1.0;
    public static double MAX_DISTANCE_THRESHOLD = 1.5;
    public static int YELLOW_THRESHOLD = 800;
    public static int RED_THRESHOLD = 0;
    public static int BLUE_THRESHOLD = 0;

    public static int YELLOW_EDGE_CASE_THRESHOLD = 1450;
    public static int RED_EDGE_CASE_THRESHOLD = 700;
    public static int BLUE_EDGE_CASE_THRESHOLD = 675;

    // Intake Pivot
    public static double INTAKE_PIVOT_TRANSFER_POS = 0.05;
    public static double INTAKE_PIVOT_READY_TRANSFER_POS = 0.32;
    public static double INTAKE_PIVOT_INTAKE_POS = 0.735;
    public static double INTAKE_PIVOT_READY_INTAKE_POS = 0.54;
    public static double INTAKE_PIVOT_HOVER_INTAKE_POS = 0.71;
    public static double INTAKE_PIVOT_TAKING = 0.6;
    public static double INTAKE_PIVOT_EJECTING = -0.4;
    public static double INTAKE_SLIDER_EJECTING = 450;

    //Intake Motor
    public static double INTAKE_TAKING_SAMPLE_POWER = 0.8;
    public static double INTAKE_STOP_TAKING_POWER = -0.8;

    // Intake Extendo
    public static int MAX_SLIDER_EXTENSION = 1440;
    public static int MIN_SLIDER_EXTENSION = -5;

    // Deposit Pivot
    public static double DEPOSIT_PIVOT_TRANSFER_POS = 0.29;
    public static double DEPOSIT_PIVOT_READY_TRANSFER_POS = 0.94;
    public static double DEPOSIT_PIVOT_MIDDLE_POS = 0.985;
    public static double DEPOSIT_PIVOT_AUTO_BAR_POS = 0.35;
    public static double DEPOSIT_PIVOT_SCORING_POS = 0.4351;
    public static double DEPOSIT_PIVOT_SPECIMEN_FRONT_INTAKE_POS = 0.03;
    public static double DEPOSIT_PIVOT_SPECIMEN_BACK_INTAKE_POS = 0.83;
    public static double DEPOSIT_PIVOT_SPECIMEN_FRONT_SCORING_POS = 0.35;
    public static double DEPOSIT_PIVOT_SPECIMEN_BACK_SCORING_POS = 0.71;

    // 0.84 sec/360° -> 0.828 sec/355° -> 828 milliseconds/355°
    public static double DEPOSIT_PIVOT_MOVEMENT_TIME = 828 + 200; // 200 milliseconds of buffer
    // 0.84 sec/360° -> 0.828 sec/355° -> (gear ratio of 48:80) 0.497 sec/355° -> 497 milliseconds/355°
    public static double INTAKE_PIVOT_MOVEMENT_TIME = 497 + 200; // 200 milliseconds of buffer

    // Deposit Claw
    public static double DEPOSIT_CLAW_OPEN_POS = 1;
    public static double DEPOSIT_CLAW_CLOSE_POS = 0;

    // Deposit Wrist
    public static double WRIST_SCORING = 0.40;
    public static double WRIST_AUTO_BAR = 0.3;
    public static double WRIST_FRONT_SPECIMEN_SCORING = 0.60;
    public static double WRIST_BACK_SPECIMEN_SCORING = 0.43;
    public static double WRIST_FRONT_SPECIMEN_INTAKE = 0.3;
    public static double WRIST_BACK_SPECIMEN_INTAKE = 0.485;
    public static double WRIST_TRANSFER = 0.495;
    public static double WRIST_MIDDLE_HOLD = 0.33;
    public static double WRIST_READY_TRANSFER = 0.22;

    // Deposit Slides
    public static int MAX_SLIDES_EXTENSION = 2300;
    public static int SLIDES_PIVOT_READY_EXTENSION = 450;
    public static int LOW_BUCKET_HEIGHT = 610;
    public static int HIGH_BUCKET_HEIGHT = 2215;
    public static int FRONT_HIGH_SPECIMEN_HEIGHT = 0;
    public static int BACK_HIGH_SPECIMEN_HEIGHT = 850;
    public static int BACK_HIGH_SPECIMEN_ATTACH_HEIGHT = 1400;
    public static int AUTO_ASCENT_HEIGHT = 800;
    public static int ENDGAME_ASCENT_HEIGHT = 1300;
    public static double SLIDER_POWER = 0.8;
    // Hang Servos
    public static double LEFT_HANG_FULL_POWER = 0.9;
    public static double RIGHT_HANG_FULL_POWER = 1.0;


    // command timeout
    public final static int MAX_COMMAND_RUN_TIME_MS = 3000;


    public final static double INTAKE_GET_SPEED = 1;
    public final static double INTAKE_OUT_SPEED = -0.5;
    public final static double INTAKE_STOP = 1;
    public final static double INTAKE_HOLD_SPEED = 1;

    public static int REVERSE_TIME_MS = 300;
    public final static double INTAKE_GYRO_TRANSFER_POS = 0.32;
    public final static double INTAKE_GYRO_GET_POS = 0.6;

    public final static double DEPOSIT_SHOULDER_TRANSFER_POS = 0.94;
    public final static double DEPOSIT_GYRO_TRANSFER_POS = 0.495;

    public final static double DEPOSIT_GYRO_LOW_BASKET_POS = 0.2811;
    public final static double DEPOSIT_SHOULDER_LOW_BASKET_POS = 0.42;
    public final static double DEPOSIT_GYRO_HIGH_BASKET_POS = 0.4;
    public final static double DEPOSIT_SHOULDER_HIGH_BASKET_POS = 0.4478;
    public final static double DEPOSIT_GYRO_HIGH_SPECIMEN_POS = 0.5122;
    public final static double DEPOSIT_SHOULDER_HIGH_SPECIMEN_POS = 0.5806;
    public final static double DEPOSIT_GYRO_HUMAN_POS = 0.55;

}
