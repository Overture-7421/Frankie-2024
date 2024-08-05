package org.firstinspires.ftc.teamcode;
/*
    ______                 __   _         ____        __          __     ______          __
   / ____/________ _____  / /__(_)__     / __ \____  / /_  ____  / /_   / ____/___  ____/ /__
  / /_  / ___/ __ `/ __ \/ //_/ / _ \   / /_/ / __ \/ __ \/ __ \/ __/  / /   / __ \/ __  / _ \
 / __/ / /  / /_/ / / / / ,< / /  __/  / _, _/ /_/ / /_/ / /_/ / /_   / /___/ /_/ / /_/ /  __/
/_/   /_/   \__,_/_/ /_/_/|_/_/\___/  /_/ |_|\____/_.___/\____/\__/   \____/\____/\__,_/\___/

This is the code to control team Overture 23619's robot "FRANKIE".
Future iterations may change the overall functionality, though it will be all used for the 2024 FIRST GLOBALS competition.
All rights reserved. Copyright Overture 23619. Overture holds the right to modify and distribute this code.
*/

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

// Commands Import
import org.firstinspires.ftc.teamcode.Commands.MoveClaw;
import org.firstinspires.ftc.teamcode.Commands.MoveChassis;

// Subsystems Import
import org.firstinspires.ftc.teamcode.Commands.MoveSingleArm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Chassis;
import org.firstinspires.ftc.teamcode.Subsystems.SingleArm;


@TeleOp
public class MainSystem extends LinearOpMode {

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().reset();

        Chassis chassis         = new Chassis(hardwareMap);     // Create an instance of Chassis
        SingleArm singleArm     = new SingleArm(hardwareMap);   // Create an instance of SingleArm
        Claw claw               = new Claw(hardwareMap);        // Create an instance of Claw
        GamepadEx driverOp      = new GamepadEx(gamepad1);      // Create an instance of DriverGamepad

        // -- CHASSIS MOVEMENT -- //
        chassis.setDefaultCommand(new MoveChassis(chassis,gamepad1));

        // -- ARM MOVEMENT -- //
        Button driverDpadDOWN= driverOp.getGamepadButton(GamepadKeys.Button.DPAD_DOWN);
        driverDpadDOWN.whenPressed(new MoveSingleArm(singleArm, -53));

        Button driverDpadUP= driverOp.getGamepadButton(GamepadKeys.Button.DPAD_UP);
        driverDpadUP.whenPressed(new MoveSingleArm(singleArm, -10));

        Button driverDpadLEFT = driverOp.getGamepadButton(GamepadKeys.Button.DPAD_LEFT);
        driverDpadLEFT.whenPressed(new MoveSingleArm(singleArm, -90));

        // -- CLAW MOVEMENT -- //
        Button driverButtonX= driverOp.getGamepadButton(GamepadKeys.Button.X);
        driverButtonX.whenPressed(new MoveClaw(claw, 0.7));

        Button driverButtonB= driverOp.getGamepadButton(GamepadKeys.Button.B);
        driverButtonB.whenPressed(new MoveClaw(claw, 0.45));

        waitForStart();
        chassis.resetPose(new Pose2d(0,0, Rotation2d.fromDegrees(0)));

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
            Pose2d pose = chassis.getPose();

            // -- ODOMETRY TELEMETRY -- //
            telemetry.addData("X", pose.getX());
            telemetry.addData("Y", pose.getY());
            telemetry.addData("Heading", pose.getRotation().getDegrees());
            telemetry.addData("RightDistance", chassis.rightDistance());
            telemetry.addData("LeftDistance", chassis.leftDistance());
            telemetry.addData("Position", singleArm.getPosition() * 360);

            // -- UPDATE TELEMETRY -- //
            telemetry.update();
        }
    }
}