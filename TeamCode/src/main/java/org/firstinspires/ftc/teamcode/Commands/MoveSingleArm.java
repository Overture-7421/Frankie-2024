package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.SingleArm;

public class MoveSingleArm extends CommandBase {

    private final SingleArm singleArm;

    private final double targetPosition;

    public MoveSingleArm(SingleArm subsystem, double targetPosition) {
        this.singleArm = subsystem;
        this.targetPosition = targetPosition / 360;
        addRequirements(singleArm);
    }

    @Override
    public void initialize() {
        singleArm.setTarget(targetPosition);
    }

    @Override
    public boolean isFinished() {
        double currentPosition = singleArm.getPosition();
        return Math.abs(targetPosition - currentPosition) < 0.05;
    }
}
