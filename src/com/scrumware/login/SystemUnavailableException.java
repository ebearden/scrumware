package com.scrumware.login;

/*
 * https://code.google.com/p/crypto2/source/browse/src/main/java/edu/crypto2/exception/SystemUnavailableException.java?
 * r=87814e22f0ebe04e5c6d1eb5ca4a30c6ae229e66
 */

public class SystemUnavailableException extends Exception{
    /****/
    private static final long serialVersionUID = 1L;
    String _symptom;
   
    /**
     * Throw this exception when the system becomes unavailable eg. due to database connection failure.
     */
    public SystemUnavailableException(Throwable throwable) {
            super(throwable);
    }

    /**
     * Throw this exception when the system becomes unavailable eg. due to database connection failure.
     */
    public SystemUnavailableException(String symptom, Throwable throwable) {
            super(throwable);
            _symptom = symptom;
    }
   
    /**
     * Throw this exception when the system becomes unavailable eg. due to database connection failure.
     */
    public SystemUnavailableException(String symptom) {
            _symptom = symptom;
    }
   
    @Override
    public String getMessage() {
            String msg = _symptom;
            return msg;
    }

    public String getSymptom() {
            return _symptom;
    }
}

