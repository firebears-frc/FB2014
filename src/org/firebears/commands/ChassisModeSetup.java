/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.firebears.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.firebears.RobotMap;

/**
 *
 * @author paul
 */
public class ChassisModeSetup extends Command {

    private Preferences preferences;
    private String m_key;
    private boolean m_bTrueFalse;

    public ChassisModeSetup(String key, boolean bTrueFalse) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_key = key;
        m_bTrueFalse = bTrueFalse;
        preferences = Preferences.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        if (m_key.equals(RobotMap.IS_PID_DRIVE_MODE)) {
            preferences.putBoolean(m_key, m_bTrueFalse);
            RobotMap.isPIDDrive = m_bTrueFalse;
            //System.out.println("PID Drive Status  " + m_bTrueFalse);
        }

        if (m_key.equals(RobotMap.IS_ARM_CALIBRATE_MODE)) {
            //preferences.putBoolean(m_key, m_bTrueFalse);
            RobotMap.isArmCalibrateMode = m_bTrueFalse;
            //System.out.println("PID Drive Status  " + m_bTrueFalse);
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
