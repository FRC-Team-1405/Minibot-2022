/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveBaseController;
import frc.robot.subsystems.ArcadeDrive;
/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private ArcadeDrive driveBase = new ArcadeDrive();

  private XboxController driver = new XboxController(Constants.pilot);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    driveBase.setDefaultCommand( new DriveBaseController( this::driveSpeed, this::driveRotation, driveBase) );
    
  }

  SlewRateLimiter driveSpeedFilter = new SlewRateLimiter(.125);
  private double driveSpeed(){ 
    double speed = driver.getRightX(); 
    if(Math.abs(speed) < Constants.deadBand)
      speed = 0.0;
    
    return speed; //driveSpeedFilter.calculate(speed);
  }

  SlewRateLimiter driveRotationFilter = new SlewRateLimiter(.125);
  private double driveRotation(){
    double rotation = driver.getLeftY();
    if(Math.abs(rotation) < Constants.deadBand)
      rotation = 0.0;

    return rotation; //driveRotationFilter.calculate(rotation);
  }

  
  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // new JoystickButton(driver, XboxController.Button.kX.value)
    //   .whenPressed(new TurnToAngle(this, driveBase, 90)); 

    Trigger enableClimb = new Trigger( () -> {
      return driver.getStartButton() && driver.getBackButton();
    }); 

    enableClimb.whenActive(new InstantCommand(driveBase::setDriveMode)); 
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
