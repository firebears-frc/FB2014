package org.firebears.subsystems;

import org.firebears.RobotMap;
import org.firebears.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Vacuum extends Subsystem {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    CANJaguar vacuumJag = RobotMap.vacuumVacuumJag;
    Solenoid wasteGateOpen = RobotMap.vacuumWasteGateOpen;
    Solenoid wasteGateClose = RobotMap.vacuumWasteGateClose;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    }
        private void wasteGateOpen(boolean open) {
        if (open) {
            wasteGateOpen.set(true);
            wasteGateClose.set(false);
        } else {
            wasteGateClose.set(true);
            wasteGateOpen.set(false);
        }
    }

    public void vacuumOn(boolean on) {
        wasteGateOpen(!on);
        if (on) {
            try {
                vacuumJag.setX(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                vacuumJag.setX(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    
    public boolean isVacuumOn() {
        try {
            return vacuumJag.getX() > 0.1;
        } catch (CANTimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isBallAttached() {
        try {
            if (! isVacuumOn()) { return false; }
            double current = vacuumJag.getOutputCurrent();
            double attachedSetpoint = (RobotMap.noBallVacuumCurrent + RobotMap.attachedBallVacuumCurrent) /2.0;          
            return (current < attachedSetpoint);
        } catch (CANTimeoutException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public double getVacuumCurrent(){
       try {
           return vacuumJag.getOutputCurrent();       
       }
       catch (CANTimeoutException e) {
            e.printStackTrace();
            return 0.0;
        }
           
       }
}
