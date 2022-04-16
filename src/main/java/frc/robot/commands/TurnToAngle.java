/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.lib.SmartSupplier;
import frc.robot.subsystems.ArcadeDrive;

public class TurnToAngle extends PIDCommand {

  public TurnToAngle(RobotContainer container, ArcadeDrive arcadeDrive, double degrees) {
    super(
      new PIDController(container.kP.getAsDouble(), container.kI.getAsDouble(), container.kD.getAsDouble()), 
      //What angle the robot is currently at
      arcadeDrive::getHeading,
      //Sets the target angle we want to rotate to
      degrees,
      //Pipes output to drive the robot
      output -> arcadeDrive.driveRobot(0, output, false),
      //Requires the drive to function
      arcadeDrive);

      getController().enableContinuousInput(-180, 180);
      getController().setTolerance(Constants.PIDConstants.positionTolerance, Constants.PIDConstants.velocityTolerance);

      new SmartSupplier("PID/Position Error", getController().getPositionError());
      new SmartSupplier("PID/Velocity Error", getController().getVelocityError());
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }
}
