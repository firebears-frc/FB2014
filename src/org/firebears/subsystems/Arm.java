package org.firebears.subsystems;

import org.firebears.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Arm extends PIDSubsystem {
    //TODO setup ARM PID for tuning using preferences, on the fly
    public final static double MAX_SPEED = .35;
    public final static double Kp = 0.02;//0.012  0.016
    public final static double Ki = 0.00;
    public final static double Kd = 0.0;
    
    CANJaguar armJagOne = RobotMap.armArmJagOne;
    CANJaguar armJagTwo = RobotMap.armArmJagTwo;
    AnalogChannel angleSensor = RobotMap.armAngleSensor;
    public double straightUpVoltage = (RobotMap.armVolts270 + RobotMap.armVolts90) / 2.0; //voltage of angle sensor perpendicular to the ground
    //double m_voltsEma = straightUpVoltage;
    double m_AngleVoltRatio; 
    public double max_speed = MAX_SPEED;
    public double m_angleSetpoint;
    
    final String MIN_STR = "Min_Angle";
    final String MAX_STR = "Max_Angle";
    
//    public static double min_angle = 118;
//    public static double max_angle = 255;
    public static double min_angle;
    public static double max_angle;
   // private Preferences preferences;
    
    public Arm() {
        super("Arm", Kp, Ki, Kd);
        getPIDController().setContinuous(false);//Don't we need a min and max setting for the PID if not continuous?
        LiveWindow.addActuator("Arm", "PIDSubsystem Controller", getPIDController());
        if (RobotMap.DEBUG) { System.out.println("Start Voltage: " + straightUpVoltage); }
        getPIDController().setAbsoluteTolerance(3.0);//With cleaner angle sensor we may be able to retune PID
        getPIDController().enable();
        angleSensor.setAverageBits(5);
        m_AngleVoltRatio = 180/(RobotMap.armVolts270 - RobotMap.armVolts90);

        
        min_angle = RobotMap.fwAngleLo;
        max_angle = RobotMap.bkAngleLo;
        
//        preferences = Preferences.getInstance();
//        if(!preferences.containsKey(MIN_STR)) {
//            preferences.putDouble(MIN_STR, min_angle);
//            preferences.putDouble(MAX_STR, max_angle);
//            preferences.save();
//        }
    }
    
    public void resetMinMax() {
//        min_angle = preferences.getDouble(MIN_STR, min_angle);
//        max_angle = preferences.getDouble(MAX_STR, max_angle);
    }

    public void initDefaultCommand() {
    }

    protected double returnPIDInput() {
        return getAngle();
    }

    protected void usePIDOutput(double output) {
        if(output < -max_speed) {
            output = -max_speed;
        }
        if(output > max_speed) {
            output = max_speed;
        }
        if(RobotMap.isArmCalibrateMode)output = 0.0;
        try {
            armJagOne.setX(output);
            armJagTwo.setX(output);
        } catch (CANTimeoutException e) {
            e.printStackTrace();
        } 
        SmartDashboard.putNumber("Arm output", output);
    }

    public void gotoAngle(double gotoAngle) {
        if(gotoAngle < min_angle) {
            gotoAngle = min_angle;
        }else if(gotoAngle > max_angle) {
            gotoAngle = max_angle;
        }
        m_angleSetpoint = gotoAngle;
//        double gComp = 14 * Math.sin((gotoAngle - 180)* 3.14159/180);//Compensate for gravity on the arm when extended
        //System.out.println("gcomp " + gComp + "  Setpoint " + gotoAngle + "  Sin "  + Math.sin((gotoAngle - 180)* 3.14159/180) + "  Final SP  " + (gotoAngle - gComp));
        SmartDashboard.putNumber("Arm Setpoint:", gotoAngle);
//        setSetpoint(gotoAngle - gComp);
        setSetpoint(gotoAngle);
    }

    private double voltsToDegrees(double smoothVoltage) {   
        //double angle = ((smoothVoltage - straightUpVoltage) * m_AngleVoltRatio) + 180; //voltage to degree
        double angle = (smoothVoltage - RobotMap.armVolts90) * m_AngleVoltRatio + 90.0;     
//        System.out.println(RobotMap.armVolts90 + ", " + RobotMap.armVolts270 + ", " + smoothVoltage);
        return angle;
    }

    public double getAngle() {
        double voltage = angleSensor.getAverageVoltage();
        SmartDashboard.putNumber("Arm averaged Volts", voltage); 
        double angle = voltsToDegrees(voltage);
        SmartDashboard.putNumber("Arm Angle", angle);     
        return angle;
    }
    
    public double getAngleVolts(){
        return angleSensor.getAverageVoltage();
    }
        
  
    
}