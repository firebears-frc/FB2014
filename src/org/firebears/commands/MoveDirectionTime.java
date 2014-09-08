/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.firebears.commands;

import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math.*;
import org.firebears.Robot;
import org.firebears.RobotMap;

/**
 *
 * @author javad
 */
public class MoveDirectionTime extends Command {

    double time;
    int dir;
    int tele;

    public MoveDirectionTime(double getTime, int getDir, int getHooblob) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        time = getTime;
        dir = getDir;
        tele = getHooblob;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(time);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        try {
            double current = RobotMap.chassisFrontRight.getOutputCurrent() + RobotMap.chassisRearLeft.getOutputCurrent() + RobotMap.chassisFrontLeft.getOutputCurrent() + RobotMap.chassisRearRight.getOutputCurrent();
            double avg = current / 4;
            double AbsoluteAvg = Math.abs(avg);
            if (RobotMap.DEBUG) { SmartDashboard.putNumber("AverageCurrent", AbsoluteAvg); }
            if (AbsoluteAvg >= 10){
                end();
            }
            if (dir < 0.0) {
                Robot.chassis.move(-0.3, 0.0);
            }
            if (dir > 0.0) {
                Robot.chassis.move(0.3, 0.0);
            }
        } 
        catch (CANTimeoutException e) {
            e.printStackTrace();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.chassis.getDistance() <= 20) {
            return true;
        }
        if (tele == 1) {
            return isTimedOut() || Robot.vacuum.isBallAttached();
        } else if (tele != 1) {
            return isTimedOut();
        }
        return isTimedOut();

    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.chassis.move(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
