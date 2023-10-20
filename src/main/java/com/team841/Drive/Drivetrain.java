package com.team841.Drive;

import java.util.function.Supplier;

import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.*;

public class Drivetrain extends SwerveDrivetrain implements Subsystem{
    
    public Drivetrain(SwerveDrivetrainConstants driveTrainConstants, double OdometryUpdateFrequency, SwerveModuleConstants... modules){
        super(driveTrainConstants, OdometryUpdateFrequency, modules);
    }

    public Command applyRequest(Supplier<SwerveRequest> requestSupplier) {
        return new RunCommand(()->{this.setControl(requestSupplier.get());}, this);
    }
}
