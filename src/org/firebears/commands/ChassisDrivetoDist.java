/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

/**
 *
 * @author javad
 */
public class ChassisDrivetoDist extends Command {
    double dist;
    public ChassisDrivetoDist(double setDist) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        dist = setDist;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(2);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
         Robot.chassis.move(0.5, 0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.chassis.getDistance() <= dist){
           return true;
        }
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
