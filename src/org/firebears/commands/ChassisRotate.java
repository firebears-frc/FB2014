/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.RobotMap;

/**
 *
 * @author javad
 */
public class ChassisRotate extends Command {

    String side;

    public ChassisRotate(String getSide) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        side = getSide;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        RobotMap.chassisGyro.reset();
        setTimeout(2);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        if (side == "right") {
            if (RobotMap.chassisGyro.getAngle() <= 90) {
                Robot.chassis.move(0.0, 0.5);
            }

        }
        if (side == "left") {
            if (RobotMap.chassisGyro.getAngle() >= -90) {
                Robot.chassis.move(0.0, -0.5);
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
        
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.chassis.move(0.0,0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
