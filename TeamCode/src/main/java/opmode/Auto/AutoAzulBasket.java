package opmode.Auto;

import static hardware.Globals.opModeType;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import hardware.Globals;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous (name = "João Basket Azul", group = "Carvoeiros")
public class AutoAzulBasket extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public class GeneratedPath {

        public GeneratedPath() {
            PathBuilder builder = new PathBuilder();

            builder
                    .addPath(
                            // Line 1
                            new BezierLine(
                                    new Point(6.215, 78.000, Point.CARTESIAN),
                                    new Point(7.000, 128.000, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            // Line 2
                            new BezierCurve(
                                    new Point(7.000, 128.000, Point.CARTESIAN),
                                    new Point(11.598, 119.927, Point.CARTESIAN),
                                    new Point(30.638, 119.927, Point.CARTESIAN)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                    .addPath(
                            // Line 3
                            new BezierCurve(
                                    new Point(30.638, 119.927, Point.CARTESIAN),
                                    new Point(27.793, 119.489, Point.CARTESIAN),
                                    new Point(12.693, 132.620, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            // Line 4
                            new BezierCurve(
                                    new Point(12.693, 132.620, Point.CARTESIAN),
                                    new Point(24.072, 130.650, Point.CARTESIAN),
                                    new Point(30.200, 130.600, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .setReversed(true)
                    .addPath(
                            // Line 5
                            new BezierCurve(
                                    new Point(30.200, 130.600, Point.CARTESIAN),
                                    new Point(17.069, 128.680, Point.CARTESIAN),
                                    new Point(12.693, 132.620, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .addPath(
                            // Line 6
                            new BezierLine(
                                    new Point(12.693, 132.620, Point.CARTESIAN),
                                    new Point(21.884, 140.936, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .setReversed(true)
                    .addPath(
                            // Line 7
                            new BezierLine(
                                    new Point(21.884, 140.936, Point.CARTESIAN),
                                    new Point(33.920, 140.936, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .setReversed(true)
                    .addPath(
                            // Line 8
                            new BezierCurve(
                                    new Point(33.920, 140.936, Point.CARTESIAN),
                                    new Point(25.386, 120.802, Point.CARTESIAN),
                                    new Point(12.474, 132.401, Point.CARTESIAN)
                            )
                    )
                    .setTangentHeadingInterpolation();
        }
    }

    @Override
    public void loop() {

        // These loop the movements of the robot
        follower.update();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {
    }
}
