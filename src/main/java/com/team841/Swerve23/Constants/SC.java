package com.team841.Swerve23.Constants;

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
}
