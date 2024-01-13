package com.team841.Swerve23.Constants;

import com.team841.Swerve23.Drive.Drivetrain;
import com.team841.Swerve23.Superstructure.Arm;
import com.team841.Swerve23.Superstructure.Intake;

public final class SubsystemManifest {
  public static final Drivetrain drivetrain = Swerve.drivetrain;
  public static final Intake intake = new Intake();
  public static final Arm arm = new Arm();
}
