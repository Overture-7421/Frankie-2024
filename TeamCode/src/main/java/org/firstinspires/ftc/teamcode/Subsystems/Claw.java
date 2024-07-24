package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Claw extends SubsystemBase {

    // Motor Declaration
    private ServoEx rightServo; // Declare left claw servo
    private ServoEx leftServo; //Declare right claw servo

    public Claw (HardwareMap hardwareMap) {
        //Servos IDs
        rightServo = new SimpleServo(hardwareMap, "grab_RightServo", 0, 180);
        leftServo = new SimpleServo(hardwareMap, "grab_LeftServo", 0, 180);

        leftServo.setInverted(true);
        //grab_RightServo.setInverted(true);
    }

    public double getRightPosition(){
        return rightServo.getPosition();
    }

    public double getLeftPosition(){
        return leftServo.getPosition();
    }

    public void setPosition(double clawPosition) {
        rightServo.setPosition(clawPosition);
        leftServo.setPosition(clawPosition);

    }

}