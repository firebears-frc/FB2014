package org.firebears.commands;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

public class DefaultLights extends Command {
    
    DriverStation ds;
    boolean setAuto = false;
    
    public DefaultLights() {
        requires(Robot.lights);
        ds = DriverStation.getInstance();
    }

    protected void initialize() {
    }
    
    private void normal_mode() {
        if(ds.getMatchTime() > 130) { //120(teleop len)+15(autonomous len)-5(5 sec before done do:
            Robot.lights.sendPacket(Robot.lights.LT_SET_ALL, Robot.lights.LT_ANIM_BL);
        }else{
            Robot.lights.sendPacket(Robot.lights.LT_SET_LOG, Robot.lights.LT_ANIM_GL);
            if(Robot.chassis.getMoving()) {
                Robot.lights.sendPacket(Robot.lights.LT_SET_UND, Robot.lights.LT_ANIM_MV);
            }else{
                Robot.lights.sendPacket(Robot.lights.LT_SET_UND, Robot.lights.LT_ANIM_FB);
            }
            if(Robot.vacuum.isVacuumOn()) {
                if(Robot.vacuum.isBallAttached()) { //if ball atached bounce light
                    Robot.lights.sendPacket(Robot.lights.LT_SET_ARM, Robot.lights.LT_ANIM_BN);                
                }else{ //if vaccuum on then do moving animation
                    Robot.lights.sendPacket(Robot.lights.LT_SET_ARM, Robot.lights.LT_ANIM_MV);
                }
            }else{ //vacuum off
                Robot.lights.sendPacket(Robot.lights.LT_SET_ARM, Robot.lights.LT_ANIM_FB);
            }
        }
    }

    protected void execute() {
        if(Robot.lights.arduinoIsConnected() == false) { //if not connected
            DigitalOutput doo = new DigitalOutput(6);
            doo.set(Robot.vacuum.isVacuumOn());
            return;
        }
        if(ds.getMatchTime() < 1) {
            Robot.lights.sendPacket(Robot.lights.LT_SET_ALL, Robot.lights.LT_ANIM_GR);
        }else if(ds.getMatchTime() > 134) {
            Robot.lights.sendPacket(Robot.lights.LT_SET_ALL, Robot.lights.LT_ANIM_SW);
        }else if(ds.isAutonomous()) {
            if(!setAuto) {
                Robot.lights.sendPacket(Robot.lights.LT_SET_UND, Robot.lights.LT_ANIM_BN);
                Robot.lights.sendPacket(Robot.lights.LT_SET_COL, Robot.lights.LT_COL_GREEN);
                Robot.lights.sendPacket(Robot.lights.LT_SET_LOG, Robot.lights.LT_ANIM_OC);
                Robot.lights.sendPacket(Robot.lights.LT_SET_ARM, Robot.lights.LT_ANIM_BL);
            }
            if(Robot.chassis.getMoving()) {
                Robot.lights.sendPacket(Robot.lights.LT_SET_UND, Robot.lights.LT_ANIM_BL);
            }else{
                Robot.lights.sendPacket(Robot.lights.LT_SET_COL, Robot.lights.LT_COL_ALIEN_BLUE);
                Robot.lights.sendPacket(Robot.lights.LT_SET_UND, Robot.lights.LT_ANIM_OC);
            }
            if(Robot.vacuum.isVacuumOn()) {
                if(Robot.vacuum.isBallAttached()) { //if ball atached bounce light
                    Robot.lights.sendPacket(Robot.lights.LT_SET_COL, Robot.lights.LT_COL_GREEN);
                    Robot.lights.sendPacket(Robot.lights.LT_SET_ARM, Robot.lights.LT_ANIM_OC);                
                }else{ //if vaccuum on then do moving animation
                    Robot.lights.sendPacket(Robot.lights.LT_SET_COL, Robot.lights.LT_COL_ALIEN_BLUE);   
                    Robot.lights.sendPacket(Robot.lights.LT_SET_ARM, Robot.lights.LT_ANIM_OC);
                }
            }else{ //vacuum off
                Robot.lights.sendPacket(Robot.lights.LT_SET_ARM, Robot.lights.LT_ANIM_BL);
            }
        }else{
            normal_mode();
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() { }

    protected void interrupted() { }
}
