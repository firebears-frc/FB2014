/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.RobotMap;

/**
 *
 * @author paul
 */
public class ArmPassAngleCommand extends Command {
    String hiLo;
    public ArmPassAngleCommand(String setAngle) {
       requires(Robot.arm);
        hiLo = setAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        if (!Robot.vacuum.isBallAttached()) Robot.vacuum.vacuumOn(false);
        if(Robot.chassis.getGoingForward()){
            
            if(hiLo == "hi")Robot.arm.gotoAngle(RobotMap.fwAnglePassHi);
            else if (hiLo == "med")Robot.arm.gotoAngle(RobotMap.fwAngleLoScore);
            }
        else {
            
            if(hiLo == "hi")Robot.arm.gotoAngle(RobotMap.bkAnglePassHi);
            else if (hiLo == "med")Robot.arm.gotoAngle(RobotMap.bkAngleLoScore);
        }
        setTimeout(2);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (!Robot.vacuum.isBallAttached())Robot.vacuum.vacuumOn(false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(Robot.arm.m_angleSetpoint - Robot.arm.getAngle()) <= 3 )|| isTimedOut() ;//accounts for gravity comp
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
