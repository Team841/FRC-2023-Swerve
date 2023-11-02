package com.team841.Swerve23.Constants;

public final class ConstantsIO {

    public enum robotStates{
        disabled,
        teleop,
        autonomous
    };

    public static robotStates robotCurrentState;

    public static boolean inTune = false;

    public static final class CANID {

        // Swerve falcons
        public static final int kFrontRightDriveFalcon = 0;
        public static final int kFrontRightTurnFalcon = 0;
        public static final int kFrontLeftDriveFalcon = 0;
        public static final int kFrontLeftTurnFalcon = 0;
        
        public static final int kBackRightTurnFalcon = 0;
        public static final int kBackRightDriveFalcon = 0;
        public static final int kBackLeftDriveFalcon = 0;
        public static final int kBackLeftTurnFalcon = 0;
        
        // Canivore
        public static final int kCanivore = 0;

        // Cancoder 
        public static final int kFrontRightCancoder = 0;
        public static final int kFrontLeftCancoder = 0;
        public static final int kBackRightCancoder = 0;
        public static final int kBackLeftCancoder = 0;

        // Pigeon
        public static final int kPigeon = 0;
    }

    
}