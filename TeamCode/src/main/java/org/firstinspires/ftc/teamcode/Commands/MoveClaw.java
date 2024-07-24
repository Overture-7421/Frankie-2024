package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.util.Timing;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;

import java.util.concurrent.TimeUnit;

public class MoveClaw extends CommandBase {
    private Claw claw;
    private double clawPosition;

    public MoveClaw(Claw subsystem, double clawPosition) {
        this.clawPosition = clawPosition;
        claw = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        claw.setPosition(clawPosition);
    }


    @Override
    public boolean isFinished() {
        double leftPosition = claw.getLeftPosition();
        double rightPosition = claw.getRightPosition();
        return (Math.abs(clawPosition - leftPosition) < 0.01) && (Math.abs(clawPosition - rightPosition) < 0.01);
    }
}

