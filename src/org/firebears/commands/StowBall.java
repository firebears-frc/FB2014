
package org.firebears.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.RobotMap;
/**
 *
 */
public class StowBall extends Command {
    //double targetAngle;
    String hiLo;
    
    public StowBall(String setAngle) {
        requires(Robot.arm);
        hiLo = setAngle;
    }
    protected void initialize() {
//        if(!Robot.vacuum.isBallAttached()){
//            Robot.vacuum.vacuumOn(false);
//        }
        if(Robot.chassis.getGoingForward()){
            //Robot.arm.gotoAngle(RobotMap.);
            if(hiLo == "hi")Robot.arm.gotoAngle(RobotMap.angleStowHi);
            else Robot.arm.gotoAngle(RobotMap.fwAngleStowLo);
            
        }
        else {
            //double change = 180 - targetAngle;
            //Robot.arm.gotoAngle(180 + change);
            if(hiLo == "hi")Robot.arm.gotoAngle(RobotMap.angleStowHi);
            else Robot.arm.gotoAngle(RobotMap.bkAngleStowLo);
        }
        setTimeout(2);
    }
    protected void execute() {
     //   if (!Robot.vacuum.isBallAttached())Robot.vacuum.vacuumOn(false);
    }
    protected boolean isFinished() {
        //return Robot.arm.onTarget();
        return (Math.abs(Robot.arm.m_angleSetpoint - Robot.arm.getAngle()) <= 3 )|| isTimedOut() ;//accounts for gravity comp
    }
 
    protected void end() {
    }
    protected void interrupted() {
    }
}

