package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Controllers.FRCProfiledPIDController;
import org.firstinspires.ftc.teamcode.Controllers.FRCTrapezoidProfile;

public class SingleArm extends SubsystemBase {

    private DcMotorEx motor;
    private FRCProfiledPIDController armPID;

    public static final double COUNTS_PER_REV = 288;

    public static final double MOTOR_GEAR_RATIO = 0.36;

    private double motorOffset = 56;

    public SingleArm(HardwareMap hardwareMap) {
        motor = (DcMotorEx) hardwareMap.get(DcMotor.class, "ArmMotor");

        armPID = new FRCProfiledPIDController(18, 0, 0.0, new FRCTrapezoidProfile.Constraints(2, 3));

        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // resetZero();

        armPID.reset(getPosition());
        armPID.setGoal(getPosition());
    }

    public void resetZero() {
        motorOffset = motor.getCurrentPosition();
    }
    public double getPosition() {
        double currentTicks = motor.getCurrentPosition();
        double currentPosition = (currentTicks / COUNTS_PER_REV * MOTOR_GEAR_RATIO)  - (motorOffset/360);
        return currentPosition;
    }

    public void setTarget(double targetHeight) {
        if (armPID.getGoal().position != targetHeight) {
            armPID.reset(getPosition());
            armPID.setGoal(targetHeight);
        }
    }
    @Override
    public void periodic() {
        double motorOutput = armPID.calculate(getPosition());
        motor.setPower(motorOutput);

    }
}