package com.team841.Swerve23.Constants;

import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Translation2d;

public class SC {
  public static class Intake {
    public static final int currentLimit = 60; // in amps
  }

  public static class Arm {
    public static final int currentLimit = 60; // in amps

    public static class pidGains {
      public static final Double kP = 0.0;
      public static final Double kI = 0.0;
      public static final Double kD = 0.0;
      public static final Double tolerance = 0.0;
      public static final Double kIz = 0.0;
    }
  }

  public static class Swerve {

    public static final Translation2d flModuleOffset = new Translation2d(0.4, 0.4);
    public static final Translation2d frModuleOffset = new Translation2d(0.4, -0.4);
    public static final Translation2d blModuleOffset = new Translation2d(-0.4, 0.4);
    public static final Translation2d brModuleOffset = new Translation2d(-0.4, -0.4);

    public static final double maxModuleSpeed = 4.5; // M/S
    public static final HolonomicPathFollowerConfig pathFollowerConfig = new HolonomicPathFollowerConfig(
        new PIDConstants(5.0, 0, 0), // Translation constants
        new PIDConstants(5.0, 0, 0), // Rotation constants
        maxModuleSpeed,
        flModuleOffset.getNorm(), // Drive base radius (distance from center to furthest module)
        new ReplanningConfig());

  }
}
