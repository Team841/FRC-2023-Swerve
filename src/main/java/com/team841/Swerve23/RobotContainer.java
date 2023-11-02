package com.team841.Swerve23;

import com.team841.Swerve23.Constants.SubsystemManifest;
import com.team841.Swerve23.Drive.Drivetrain;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class RobotContainer {

  private final Drivetrain drivetrain = SubsystemManifest.drivetrain;

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
