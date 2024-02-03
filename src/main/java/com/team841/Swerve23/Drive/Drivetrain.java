package com.team841.Swerve23.Drive;
import com.team841.Swerve23.Constants.SC;
import com.team841.Swerve23.Constants.Swerve;

import com.ctre.phoenix6.mechanisms.swerve.SimSwerveDrivetrain.SimSwerveModule;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.PathPlannerLogging;
import com.pathplanner.lib.util.ReplanningConfig;
import com.team841.Swerve23.Constants.SC;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.sendable.Sendable;
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
    
    private final SwerveRequest.ApplyChassisSpeeds autoRequest = new SwerveRequest.ApplyChassisSpeeds();
    
  public Drivetrain(
      SwerveDrivetrainConstants driveTrainConstants,
      double OdometryUpdateFrequency,
      SwerveModuleConstants... modules) {
    super(driveTrainConstants, OdometryUpdateFrequency, modules);
    
    configurePathPlanner();
    //pidgey = new Pigeon2(0);

  }

  public Drivetrain(
      SwerveDrivetrainConstants driveTrainConstants, SwerveModuleConstants... modules) {
    super(driveTrainConstants, modules);
    configurePathPlanner();
  }

 public void configurePathPlanner() {
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

        double driveBaseRadius = 0.3302;
        for (var moduleLocation : m_moduleLocations) {
            driveBaseRadius = Math.max(driveBaseRadius, moduleLocation.getNorm());
        }
        // Configure AutoBuilder
        /*AutoBuilder.configureHolonomic(
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
*/
 AutoBuilder.configureHolonomic(
            ()->this.getState().Pose, // Supplier of current robot pose
            this::seedFieldRelative,  // Consumer for seeding pose against auto
            this::getCurrentRobotChassisSpeeds,
            (speeds)->this.setControl(autoRequest.withSpeeds(speeds)), // Consumer of ChassisSpeeds to drive the robot
            new HolonomicPathFollowerConfig(new PIDConstants(10, 0, 0),
                                            new PIDConstants(10, 0, 0),
                                            Swerve.kSpeedAt12VoltsMps,
                                            driveBaseRadius,
                                            new ReplanningConfig()),
            ()->false, // Change this if the path needs to be flipped on red vs blue
            this); // Subsystem for requirements
    

        // Set up custom logging to add the current path to a field 2d widget
        PathPlannerLogging.setLogActivePathCallback((poses) -> field.getObject("path").setPoses(poses));

        SmartDashboard.putData("Field", field);
    }
    public ChassisSpeeds getCurrentRobotChassisSpeeds() {
        return m_kinematics.toChassisSpeeds(getState().ModuleStates);
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
    //public void periodic(){
       // double yaw;
       // yaw = pidgey.getAngle();
       // SmartDashboard.putNumber("YawinDegrees", yaw);
    }

