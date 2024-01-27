package com.team841.Swerve23.Drive;
import com.team841.Swerve23.Constants.SC;

import com.ctre.phoenix6.mechanisms.swerve.SimSwerveDrivetrain.SimSwerveModule;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.PathPlannerLogging;
import com.team841.Swerve23.Constants.SC;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Translation2d;

public class Drivetrain extends SwerveDrivetrain implements Subsystem {

   // for path planning
    private SimSwerveModule[] modules;
    private SwerveDriveKinematics kinematics;
    private SwerveDriveOdometry odometry;

    private SimGyro gyro;
    private Field2d field = new Field2d();
  public Drivetrain(
      SwerveDrivetrainConstants driveTrainConstants,
      double OdometryUpdateFrequency,
      SwerveModuleConstants... modules) {
    super(driveTrainConstants, OdometryUpdateFrequency, modules);
    Swerve();
  }

  public Drivetrain(
      SwerveDrivetrainConstants driveTrainConstants, SwerveModuleConstants... modules) {
    super(driveTrainConstants, modules);
    Swerve();
  }

 public void Swerve() {
        gyro = new SimGyro();
        modules = new SimSwerveModule[] {
                new SimSwerveModule(),
                new SimSwerveModule(),
                new SimSwerveModule(),
                new SimSwerveModule()
        };
        kinematics = new SwerveDriveKinematics(
                SC.Swerve.flModuleOffset,
                SC.Swerve.frModuleOffset,
                SC.Swerve.blModuleOffset,
                SC.Swerve.brModuleOffset);
        odometry = new SwerveDriveOdometry(kinematics, gyro.getRotation2d(), getPositions());

        // Configure AutoBuilder
        AutoBuilder.configureHolonomic(
                this::getPose,
                this::resetPose,
                this::getSpeeds,
                this::driveRobotRelative,
                SC.Swerve.pathFollowerConfig,
                () -> {
                    // Boolean supplier that controls when the path will be mirrored for the red
                    // alliance
                    // This will flip the path being followed to the red side of the field.
                    // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                    var alliance = DriverStation.getAlliance();
                    if (alliance.isPresent()) {
                        return alliance.get() == DriverStation.Alliance.Red;
                    }
                    return false;
                },
                this);

        // Set up custom logging to add the current path to a field 2d widget
        PathPlannerLogging.setLogActivePathCallback((poses) -> field.getObject("path").setPoses(poses));

        SmartDashboard.putData("Field", field);
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void resetPose(Pose2d pose) {
        odometry.resetPosition(gyro.getRotation2d(), getPositions(), pose);
    }

    public ChassisSpeeds getSpeeds() {
        return kinematics.toChassisSpeeds(getModuleStates());
    }

    public void driveFieldRelative(ChassisSpeeds fieldRelativeSpeeds) {
        driveRobotRelative(ChassisSpeeds.fromFieldRelativeSpeeds(fieldRelativeSpeeds, getPose().getRotation()));
    }

    public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds) {
        ChassisSpeeds targetSpeeds = ChassisSpeeds.discretize(robotRelativeSpeeds, 0.02);

        SwerveModuleState[] targetStates = kinematics.toSwerveModuleStates(targetSpeeds);
        setStates(targetStates);
    }

    public void setStates(SwerveModuleState[] targetStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(targetStates, SC.Swerve.maxModuleSpeed);

        for (int i = 0; i < modules.length; i++) {
            modules[i].setTargetState(targetStates[i]);
        }
    }

    public SwerveModuleState[] getModuleStates() {
        SwerveModuleState[] states = new SwerveModuleState[modules.length];
        for (int i = 0; i < modules.length; i++) {
            states[i] = modules[i].getState();
        }
        return states;
    }

    public SwerveModulePosition[] getPositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[modules.length];
        for (int i = 0; i < modules.length; i++) {
            positions[i] = modules[i].getPosition();
        }
        return positions;
    }

    /**
     * Basic simulation of a swerve module, will just hold its current state and not
     * use any hardware
     */
    class SimSwerveModule {
        private SwerveModulePosition currentPosition = new SwerveModulePosition();
        private SwerveModuleState currentState = new SwerveModuleState();

        public SwerveModulePosition getPosition() {
            return currentPosition;
        }

        public SwerveModuleState getState() {
            return currentState;
        }

        public void setTargetState(SwerveModuleState targetState) {
            // Optimize the state
            currentState = SwerveModuleState.optimize(targetState, currentState.angle);

            currentPosition = new SwerveModulePosition(
                    currentPosition.distanceMeters + (currentState.speedMetersPerSecond * 0.02), currentState.angle);
        }
    }

  public Command applyRequest(Supplier<SwerveRequest> requestSupplier) {
    return new RunCommand(
        () -> {
          this.setControl(requestSupplier.get());
        },
        this);
  }
  /**
     * Basic simulation of a gyro, will just hold its current state and not use any
     * hardware
     */
    class SimGyro {
      private Rotation2d currentRotation = new Rotation2d();

      public Rotation2d getRotation2d() {
          return currentRotation;
      }

      public void updateRotation(double angularVelRps) {
          currentRotation = currentRotation.plus(new Rotation2d(angularVelRps * 0.02));
      }
  }
}
