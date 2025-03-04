package hardware;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.function.LongConsumer;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public class RobotHardware {

    /* Declare OpMode members. */
    private LinearOpMode myOpMode = null;   // gain access to methods in the calling OpMode.

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)
//    private DcMotor leftDrive   = null;
//    private DcMotor rightDrive  = null;
//    private DcMotor armMotor = null;
//    private Servo   leftHand = null;
//    private Servo   rightHand = null;
//
//    // Define Drive constants.  Make them public so they CAN be used by the calling OpMode
//    public static final double MID_SERVO       =  0.5 ;
//    public static final double HAND_SPEED      =  0.02 ;  // sets rate to move servo
//    public static final double ARM_UP_POWER    =  0.45 ;
//    public static final double ARM_DOWN_POWER  = -0.45 ;

    // Define a constructor that allows the OpMode to pass a reference to itself.
    public RobotHardware (LinearOpMode opmode) {
        myOpMode = opmode;
    }

    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     * <p>
     * All of the hardware devices are accessed via the hardware map, and initialized.
     */
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);

    public void init()    {
//        // Define and Initialize Motors (note: need to use reference to actual OpMode).
//        leftDrive  = myOpMode.hardwareMap.get(DcMotor.class, "left_drive");
//        rightDrive = myOpMode.hardwareMap.get(DcMotor.class, "right_drive");
//        armMotor   = myOpMode.hardwareMap.get(DcMotor.class, "arm");
//
//        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
//        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
//        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
//        leftDrive.setDirection(DcMotor.Direction.REVERSE);
//        rightDrive.setDirection(DcMotor.Direction.FORWARD);
//
//        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
//        // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        // Define and initialize ALL installed servos.
//        leftHand = myOpMode.hardwareMap.get(Servo.class, "left_hand");
//        rightHand = myOpMode.hardwareMap.get(Servo.class, "right_hand");
//        leftHand.setPosition(MID_SERVO);
//        rightHand.setPosition(MID_SERVO);

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(myOpMode.hardwareMap);
        follower.setStartingPose(startPose);

        myOpMode.telemetry.addData(">", "Hardware Initialized");
        myOpMode.telemetry.update();

        follower.startTeleopDrive();
    }
}
