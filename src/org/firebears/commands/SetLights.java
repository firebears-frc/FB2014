package org.firebears.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firebears.Robot;

/**
 * Sets an animation on a strip of lights.
 */
public class SetLights extends Command {
    byte anim;
    byte strips;
    
    public SetLights(byte p_stripsToSet, byte p_anim) {
        strips = p_stripsToSet;
        anim = p_anim;
    }

    protected void initialize() {
        Robot.lights.sendPacket(strips,anim);
    }

    protected void execute() { }

    protected boolean isFinished() {
        return true;
    }

    protected void end() { }

    protected void interrupted() { }
}
