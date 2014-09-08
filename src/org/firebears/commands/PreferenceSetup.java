/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.firebears.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;
import org.firebears.RobotMap;

/**
 *
 * @author paul
 */
public class PreferenceSetup extends Command{
    
    double m_armAngle;
    double m_angleVolts;
    double m_VacuumAmps;
    private Preferences preferences;
    private String m_key;
    
    public PreferenceSetup(String key) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm); 
        m_key = key;
         preferences = Preferences.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() { 
        
       
        
        //Get Arm angle and store in preferences keyed by SD button, RobotMap "constants" and update SD value displayed  
        m_armAngle = Robot.arm.getAngle();
        m_angleVolts = Robot.arm.getAngleVolts();
        m_VacuumAmps  = Robot.vacuum.getVacuumCurrent();

        
        //System.out.println("Angle Volts = " + m_angleVolts + " Key = " + m_key);
               
       
        
        if(m_key.equals(RobotMap.FW_ANGLE_LO)){
         preferences.putDouble(m_key, m_armAngle);
         RobotMap.fwAngleLo = m_armAngle;
         //SmartDashboard.putNumber("FW_ANGLE_LO:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.FW_ANGLE_CATCH)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.fwAngleCatch = m_armAngle;
         //SmartDashboard.putNumber("FW_ANGLE_PICKUP_HI:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.FW_ANGLE_STOW_LO)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.fwAngleStowLo = m_armAngle;
         //SmartDashboard.putNumber("FW_ANGLE_STOW_LO:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.ANGLE_STOW_HI)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.angleStowHi = m_armAngle;
         //SmartDashboard.putNumber("ANGLE_STOW_HI:", m_armAngle);
        }
         else if (m_key.equals(RobotMap.FW_ANGLE_LO_SCORE)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.fwAngleLoScore = m_armAngle;
         //SmartDashboard.putNumber("FW_ANGLE_RELEASE_MED:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.FW_ANGLE_PASS_HI)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.fwAnglePassHi = m_armAngle;
         //SmartDashboard.putNumber("FW_ANGLE_RELEASE_HI:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.ANGLE_THROW_END)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.angleThrowEnd = m_armAngle;
         //SmartDashboard.putNumber("ANGLE_THROW_END:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.ANGLE_THROW_RELEASE)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.angleThrowRelease = m_armAngle;
         //SmartDashboard.putNumber("ANGLE_THROW_RELEASE:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.BK_ANGLE_LO)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.bkAngleLo = m_armAngle;
         //SmartDashboard.putNumber("BK_ANGLE_LO:", m_armAngle);
        }
         else if (m_key.equals(RobotMap.BK_ANGLE_CATCH)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.bkAngleCatch = m_armAngle;
         //SmartDashboard.putNumber("BK_ANGLE_PICKUP_HI:", m_armAngle);
        }
         else if (m_key.equals(RobotMap.BK_ANGLE_STOW_LO)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.bkAngleStowLo = m_armAngle;
         //SmartDashboard.putNumber("BK_ANGLE_STOW_LO:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.BK_ANGLE_LO_SCORE)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.bkAngleLoScore = m_armAngle;
         //SmartDashboard.putNumber("BK_ANGLE_RELEASE_MED:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.BK_ANGLE_PASS_HI)){
           preferences.putDouble(m_key, m_armAngle);
         RobotMap.bkAnglePassHi = m_armAngle;
         //SmartDashboard.putNumber("BK_ANGLE_RELEASE_HI:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.ARM_VOLTS_90)){
           preferences.putDouble(m_key, m_angleVolts);
         RobotMap.armVolts90 = m_angleVolts;
         //SmartDashboard.putNumber("BK_ANGLE_RELEASE_HI:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.ARM_VOLTS_270)){
           preferences.putDouble(m_key, m_angleVolts);
         RobotMap.armVolts270 = m_angleVolts;
         //SmartDashboard.putNumber("BK_ANGLE_RELEASE_HI:", m_armAngle);
        }
        else if (m_key.equals(RobotMap.NO_BALL_VACUUM_CURENT)){
           preferences.putDouble(m_key, m_VacuumAmps);
         RobotMap.noBallVacuumCurrent = m_VacuumAmps;
        }
         else if (m_key.equals(RobotMap.ATTACHED_BALL_VACUUM_CURENT)){
           preferences.putDouble(m_key, m_VacuumAmps);
         RobotMap.attachedBallVacuumCurrent = m_VacuumAmps;
        }
        
        
      preferences.save();  
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
