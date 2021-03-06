package org.firebears.subsystems;
import org.firebears.RobotMap;
import org.firebears.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import InsightLT.*;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * This subsystem organizes self testing within the robot, and reports back
 * to the LCD panel and the Driver's station.
 */
public class Diagnostics extends Subsystem {
    InsightLT display = new InsightLT(InsightLT.FOUR_ZONES);
    DecimalData decimal_volts;
    DriverStation driverStation;
    StringData status;
    StringData error;
    StringData diag;
    DecimalData diagn;
    
    
    public Diagnostics() {
        driverStation = DriverStation.getInstance();
    }
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public void initDefaultCommand() {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
         
       display.startDisplay();//delete?
    }
    
    /**
     * Add one message indicating that there is an error.
     * The LCD will show a new error message every 1.5 seconds.
     * If there are no errors at all, the LCD will show dashes.
     * 
     * @param msg Message of ten characters or less.
     */
    public void addErrorMessage(String msg, int zone) {

        error = new StringData();
        error.setData(msg);
        display.registerData(error,zone);
        display.startDisplay();
    }
    
    /**
     * Set a message in the lower left zone on the LCD.
     * 
     * @param msg Message of ten characters or less.
     */
    public void setStatus(String msg,int zone) {
        status = new StringData();
        status.setData(msg);
        display.registerData(status, zone);
        display.startDisplay();
    }
    public void setInt(double msg, int zone){
        diagn = new DecimalData();
        diagn.setData(msg);
        display.registerData(diagn, zone);
        display.startDisplay();
        
    }
    
    /**
     * Called about once per second to query the system and update the displays.
     */
    public void updateDisplay() {
        decimal_volts = new DecimalData("Volts: ");
        decimal_volts.setData(driverStation.getBatteryVoltage());
        display.registerData(decimal_volts, 1);
        display.startDisplay();
        // TODO query system and update the LCD.
        // For instance, get the battery voltage and display.
    }
}
