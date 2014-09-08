package org.firebears.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.RobotMap;
/**
 *
 */
public class ArmJoystickCommand extends Command {
    public ArmJoystickCommand() {
        requires(Robot.arm);
    }

    public double getJoystickAngle() {
        double joyStickAngle = Robot.oi.joystickArm.getY();
        double percentAngle = (joyStickAngle + 1.0)/ 2.0;
        //double angle = percentAngle * (Robot.arm.max_angle-Robot.arm.min_angle);
        double angle = percentAngle * (RobotMap.bkAngleLo-RobotMap.fwAngleLo);
        //double setArmAngle = angle + Robot.arm.min_angle;
        double setArmAngle = angle + RobotMap.fwAngleLo;
        System.out.println("angle should be " + setArmAngle);
        return setArmAngle;
    }
    
    protected void initialize() {
    }
    protected void execute() {
        Robot.arm.gotoAngle(getJoystickAngle());
    }
    protected boolean isFinished() {
        return false;
    }
    protected void end() {
    Robot.arm.gotoAngle(Robot.arm.getAngle());
    }
    protected void interrupted() {
    Robot.arm.gotoAngle(Robot.arm.getAngle());
    }
}