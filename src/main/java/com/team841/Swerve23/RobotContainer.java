package com.team841.Swerve23;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.team841.Swerve23.Constants.ConstantsIO;
import com.team841.Swerve23.Constants.SubsystemManifest;
import com.team841.Swerve23.Drive.Drivetrain;
import com.team841.Swerve23.Superstructure.Arm;
import com.team841.Swerve23.Superstructure.Intake;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
// import edu.wpi.first.wpilibj2.command.button.Trigger;
// import edu.wpi.first.wpilibj2.command.button.InstantCommand;
// import edu.wpi.first.wpilibj2.command.button.RunCommand;


public class RobotContainer {

  final double MaxSpeed = 6; // 6 meters per second desired top speed
  final double MaxAngularRate = Math.PI; // Half a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  CommandPS4Controller joystick = new CommandPS4Controller(ConstantsIO.OI.driverPortLeft); // My joystick
  Drivetrain drivetrain = SubsystemManifest.drivetrain; // My drivetrain
  /*
  SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric().withIsOpenLoop(true); // I want field-centric
  SwerveRequest.RobotCentric rdrive = new SwerveRequest.RobotCentric().withIsOpenLoop(true);
  */
 SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
 SwerveRequest.RobotCentric rdrive = new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  // driving in open loop
  SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  Telemetry logger = new Telemetry(MaxSpeed);

  CommandPS4Controller coDriverJoystick = new CommandPS4Controller(ConstantsIO.OI.codriverPort); 
  Arm arm = SubsystemManifest.arm;
  Intake intake = SubsystemManifest.intake;

  private void configureBindings() {
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(
            () ->
                rdrive
                    .withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with
                    // negative Y (forward)
                    .withVelocityY(
                        -joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(
                        -joystick.getRightX()
                            * MaxAngularRate) // Drive counterclockwise with negative X (left)
            ));

    joystick.cross().whileTrue(drivetrain.applyRequest(() -> brake));
    joystick
        .circle()
        .whileTrue(
            drivetrain.applyRequest(
                () ->
                    point.withModuleDirection(
                        new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);
  }

  private void configureCoBindings() {
   coDriverJoystick.cross().whileTrue(new InstantCommand(intake::intake, intake));
   coDriverJoystick.circle().onTrue( new InstantCommand(intake::StopTake,intake));
    coDriverJoystick.triangle().whileTrue(new InstantCommand(intake::outTake, intake));

    coDriverJoystick.L1().onTrue(new InstantCommand(arm::armOut, arm));
    coDriverJoystick.L1().onFalse(new InstantCommand(arm::armStop, arm));

    coDriverJoystick.R1().onTrue(new InstantCommand(arm::armIn, arm));
    coDriverJoystick.R1().onFalse(new InstantCommand(arm::armStop, arm));


  }




  public RobotContainer() {
    configureBindings();
    configureCoBindings();
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
