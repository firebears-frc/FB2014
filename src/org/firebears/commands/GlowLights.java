package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

/**
 * Sets a non-animated color on a strip of lights.
 */
public class GlowLights extends Command {
    byte color;
    byte strips;
    
    public GlowLights(byte p_stripsToSet, byte p_color) {
        strips = p_stripsToSet;
        color = p_color;
    }

    protected void initialize() {
        Robot.lights.sendPacket(Robot.lights.LT_SET_COL, color);
        Robot.lights.sendPacket(strips,Robot.lights.LT_ANIM_OC); //set lights to one color
    }

    protected void execute() { }

    protected boolean isFinished() {
        return true;
    }

    protected void end() { }

    protected void interrupted() { }
}
