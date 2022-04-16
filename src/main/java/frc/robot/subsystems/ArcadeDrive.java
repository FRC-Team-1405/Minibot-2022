/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArcadeDrive extends SubsystemBase {
  
  private AHRS gyro = new AHRS(Port.kMXP); 

 
  DifferentialDrive driveBase = new DifferentialDrive( new CANSparkMax(1 , MotorType.kBrushless),
                                                       new CANSparkMax(2 , MotorType.kBrushless) );
  public ArcadeDrive() {

  }

  @Override
  public void periodic() {
  }

  public void driveRobot(double xSpeed, double zRotation, boolean squareInputs)   
  {
    driveBase.arcadeDrive(xSpeed, zRotation, squareInputs);
  }

  public double getHeading()
  {
    return Math.IEEEremainder(gyro.getAngle(), 360);
  }
}
