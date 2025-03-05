package SubSystems;

import static hardware.Globals.*;

import com.pedropathing.util.CustomPIDFCoefficients;
import com.pedropathing.util.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import hardware.Robot;

public class Delivery {
    public boolean clawOpen;
    public double target;

    public boolean slidesReached;

    // Between retracted and extended
    public boolean slidesRetracted;
    public enum DepositPivotState {
        SCORING,
        FRONT_SPECIMEN_SCORING,
        FRONT_SPECIMEN_INTAKE,
        TRANSFER,
        READY_TRANSFER,
        MIDDLE_HOLD,
        AUTO_TOUCH_BAR
    }
    public static DepositPivotState depositPivotState;

    OpModeType opMODE = opModeType;

    private static final PIDFController slidePIDF = new PIDFController(new CustomPIDFCoefficients(0.007,0,0.00017,0.00023));

    public void init() {
        setSliderTarget(0);


//        if (opMODE.equals(OpModeType.TELEOP)){
//            setSlideTarget(100);
//            setClawOpen(true);
//        } else {
//            setClawOpen(false);
//        }
    }

    public void setClawOpen(boolean open) {
        if (open) {
            Robot.deliveryClaw.setPosition(DEPOSIT_CLAW_OPEN_POS);
        } else {
            Robot.deliveryClaw.setPosition(DEPOSIT_CLAW_CLOSE_POS);
        }

        this.clawOpen = open;
    }
    public void setSliderTarget(int target) {
        this.target = Range.clip(target, 0, MAX_SLIDES_EXTENSION);

        Robot.rightElevatorDrive.setTargetPosition(target);
        Robot.leftElevatorDrive.setTargetPosition(target);

        Robot.rightElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.leftElevatorDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Robot.rightElevatorDrive.setPower(SLIDER_POWER);
        Robot.leftElevatorDrive.setPower(SLIDER_POWER);
//        slidePIDF.setTargetPosition(target);
    }

    public void autoUpdateSlides() {
        if (this.target == BACK_HIGH_SPECIMEN_ATTACH_HEIGHT && !slidesReached) {
            slidePIDF.setP(0.01);
        } else {
            slidePIDF.setP(0.007);
        }

        double power = SLIDER_POWER;
//        double power = slidePIDF.calculate(getLiftScaledPosition(), target);
//        slidesReached = slidePIDF.atSetPoint()
//                || (target == 0 && getLiftScaledPosition() < 15)
//                || (getLiftScaledPosition() >= target && target == HIGH_BUCKET_HEIGHT)
//                || (target == SLIDES_PIVOT_READY_EXTENSION + 50 && getLiftScaledPosition() > SLIDES_PIVOT_READY_EXTENSION && getLiftScaledPosition() < SLIDES_PIVOT_READY_EXTENSION + 65);
//        slidesRetracted = (target <= 0) && slidesReached;

        // Just make sure it gets to fully retracted if target is 0
        if (target == 0 && !slidesReached) {
            power -= 0.1;
        }

        if (slidesRetracted) {
            Robot.leftElevatorDrive.setPower(0);
            Robot.rightElevatorDrive.setPower(0);
        } else {
            Robot.leftElevatorDrive.setPower(power);
            Robot.rightElevatorDrive.setPower(power);
        }


    }

    public void setPivot(DepositPivotState depositPivotState){
        switch (depositPivotState) {
            case SCORING:
                Robot.deliveryGyroLeft.setPosition(DEPOSIT_PIVOT_SCORING_POS);
                Robot.deliveryGyroRight.setPosition(DEPOSIT_PIVOT_SCORING_POS);
                Robot.deliveryGyro.setPosition(WRIST_SCORING);
                break;
            case FRONT_SPECIMEN_SCORING:
                Robot.deliveryGyroLeft.setPosition(DEPOSIT_PIVOT_SPECIMEN_FRONT_SCORING_POS);
                Robot.deliveryGyroRight.setPosition(DEPOSIT_PIVOT_SPECIMEN_FRONT_SCORING_POS);
                Robot.deliveryGyro.setPosition(WRIST_FRONT_SPECIMEN_SCORING);
                break;
            case TRANSFER:
                Robot.deliveryGyroLeft.setPosition(DEPOSIT_PIVOT_TRANSFER_POS);
                Robot.deliveryGyroRight.setPosition(DEPOSIT_PIVOT_TRANSFER_POS);
                Robot.deliveryGyro.setPosition(WRIST_TRANSFER);
                break;
            case READY_TRANSFER:
                Robot.deliveryGyroLeft.setPosition(DEPOSIT_PIVOT_READY_TRANSFER_POS);
                Robot.deliveryGyroRight.setPosition(DEPOSIT_PIVOT_READY_TRANSFER_POS);
                Robot.deliveryGyro.setPosition(WRIST_READY_TRANSFER);
                break;
            case FRONT_SPECIMEN_INTAKE:
                Robot.deliveryGyroLeft.setPosition(DEPOSIT_PIVOT_SPECIMEN_FRONT_INTAKE_POS);
                Robot.deliveryGyroRight.setPosition(DEPOSIT_PIVOT_SPECIMEN_FRONT_INTAKE_POS);
                Robot.deliveryGyro.setPosition(WRIST_FRONT_SPECIMEN_INTAKE);
                break;
            case MIDDLE_HOLD:
                Robot.deliveryGyroLeft.setPosition(DEPOSIT_PIVOT_MIDDLE_POS);
                Robot.deliveryGyroRight.setPosition(DEPOSIT_PIVOT_MIDDLE_POS);
                Robot.deliveryGyro.setPosition(WRIST_MIDDLE_HOLD);
                break;
            case AUTO_TOUCH_BAR:
                Robot.deliveryGyroLeft.setPosition(DEPOSIT_PIVOT_AUTO_BAR_POS);
                Robot.deliveryGyroRight.setPosition(DEPOSIT_PIVOT_AUTO_BAR_POS);
                Robot.deliveryGyro.setPosition(WRIST_AUTO_BAR);
                break;
        }
        Delivery.depositPivotState = depositPivotState;
    }

}
