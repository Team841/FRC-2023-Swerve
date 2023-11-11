package com.team841.Swerve23.Constants;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants.SwerveModuleSteerFeedbackType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstantsFactory;
import com.team841.Swerve23.Drive.Drivetrain;
import edu.wpi.first.math.util.Units;

public class Swerve {
  static class CustomSlotGains extends Slot0Configs {
    public CustomSlotGains(double kP, double kI, double kD, double kV, double kS) {
      this.kP = kP;
      this.kI = kI;
      this.kD = kD;
      this.kV = kV;
      this.kS = kS;
    }
  }

  private static final CustomSlotGains steerGains = new CustomSlotGains(100, 0, 0.05, 0, 0);
  private static final CustomSlotGains driveGains = new CustomSlotGains(3, 0, 0, 0, 0);

  private static final double kCoupleRatio = 0.0;

  private static final double kDriveGearRatio = 6.12;
  private static final double kSteerGearRatio = 21.42;
  private static final double kWheelRadiusInches = 3.5;
  private static final int kPigeonId = 0;
  private static final boolean kSteerMotorReversed = true;
  private static final String kCANbusName = "Default Name";
  private static final boolean kInvertLeftSide = false;
  private static final boolean kInvertRightSide = false;

  private static double kSteerInertia = 0.00001;
  private static double kDriveInertia = 0.001;

  private static final SwerveDrivetrainConstants DrivetrainConstants =
      new SwerveDrivetrainConstants().withPigeon2Id(kPigeonId).withCANbusName(kCANbusName);

  private static final SwerveModuleConstantsFactory ConstantCreator =
      new SwerveModuleConstantsFactory()
          .withDriveMotorGearRatio(kDriveGearRatio)
          .withSteerMotorGearRatio(kSteerGearRatio)
          .withWheelRadius(kWheelRadiusInches)
          .withSlipCurrent(800)
          .withSteerMotorGains(steerGains)
          .withDriveMotorGains(driveGains)
          .withSpeedAt12VoltsMps(
              6) // Theoretical free speed is 10 meters per second at 12v applied output
          .withSteerInertia(kSteerInertia)
          .withDriveInertia(kDriveInertia)
          .withFeedbackSource(SwerveModuleSteerFeedbackType.FusedCANcoder)
          .withCouplingGearRatio(
              kCoupleRatio) // Every 1 rotation of the azimuth results in couple ratio drive turns
          .withSteerMotorInverted(kSteerMotorReversed);

  private static final int kFrontLeftDriveMotorId = 3;
  private static final int kFrontLeftSteerMotorId = 4;
  private static final int kFrontLeftEncoderId = 2;
  private static final double kFrontLeftEncoderOffset = -0.604248046875;

  private static final double kFrontLeftXPosInches = 10.375;
  private static final double kFrontLeftYPosInches = 10.375;
  private static final int kFrontRightDriveMotorId = 1;
  private static final int kFrontRightSteerMotorId = 2;
  private static final int kFrontRightEncoderId = 0;
  private static final double kFrontRightEncoderOffset = -0.4658203125;


  private static final double kFrontRightXPosInches = 10.375;
  private static final double kFrontRightYPosInches = -10.375;
  private static final int kBackLeftDriveMotorId = 5;
  private static final int kBackLeftSteerMotorId = 6;
  private static final int kBackLeftEncoderId = 3;
  private static final double kBackLeftEncoderOffset = -0.4462890625;


  private static final double kBackLeftXPosInches = -10.375;
  private static final double kBackLeftYPosInches = 10.375;
  private static final int kBackRightDriveMotorId = 7;
  private static final int kBackRightSteerMotorId = 8;
  private static final int kBackRightEncoderId = 4;
  private static final double kBackRightEncoderOffset = -0.0517578125;

  private static final double kBackRightXPosInches = -10.375;
  private static final double kBackRightYPosInches = -10.375;

  private static final SwerveModuleConstants FrontLeft =
      ConstantCreator.createModuleConstants(
          kFrontLeftSteerMotorId,
          kFrontLeftDriveMotorId,
          kFrontLeftEncoderId,
          kFrontLeftEncoderOffset,
          Units.inchesToMeters(kFrontLeftXPosInches),
          Units.inchesToMeters(kFrontLeftYPosInches),
          kInvertLeftSide);
  private static final SwerveModuleConstants FrontRight =
      ConstantCreator.createModuleConstants(
          kFrontRightSteerMotorId,
          kFrontRightDriveMotorId,
          kFrontRightEncoderId,
          kFrontRightEncoderOffset,
          Units.inchesToMeters(kFrontRightXPosInches),
          Units.inchesToMeters(kFrontRightYPosInches),
          kInvertRightSide);
  private static final SwerveModuleConstants BackLeft =
      ConstantCreator.createModuleConstants(
          kBackLeftSteerMotorId,
          kBackLeftDriveMotorId,
          kBackLeftEncoderId,
          kBackLeftEncoderOffset,
          Units.inchesToMeters(kBackLeftXPosInches),
          Units.inchesToMeters(kBackLeftYPosInches),
          kInvertLeftSide);
  private static final SwerveModuleConstants BackRight =
      ConstantCreator.createModuleConstants(
          kBackRightSteerMotorId,
          kBackRightDriveMotorId,
          kBackRightEncoderId,
          kBackRightEncoderOffset,
          Units.inchesToMeters(kBackRightXPosInches),
          Units.inchesToMeters(kBackRightYPosInches),
          kInvertRightSide);

  protected static final Drivetrain drivetrain =
      new Drivetrain(DrivetrainConstants, FrontLeft, FrontRight, BackLeft, BackRight);
}
