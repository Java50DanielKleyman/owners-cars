package telran.cars.api;

import java.util.Calendar;

public interface ValidationConstants {
	String MISSING_CAR_NUMBER_MESSAGE = "Missing car number";
	String CAR_NUMBER_REGEXP = "(\\d{3}-\\d{2}-\\d{3})|(\\d{2}-\\d{3}-\\d{2})";
	String WRONG_CAR_NUMBER_MESSAGE = "Incorrect Car Number";
	String MISSING_CAR_MODEL_MESSAGE = "Missing car model";
	String MISSING_PERSON_ID_MESSAGE = "Missing person ID";
	String MISSING_MODEL_MESSAGE = "Missing model";
	String MISSING_YEAR_MESSAGE = "Missing year";
	String MISSING_COMPANY_MESSAGE = "Missing company";
	String MISSING_ENGINE_POWER_MESSAGE = "Missing engine power";
	String MISSING_ENGINE_CAPACITY_MESSAGE = "Missing engine capacity";
	String MISSING_COLOR_MESSAGE = "Missing color";
	String MISSING_KILOMETERS_MESSAGE = "Missing kilometers";
	String MISSING_CAR_STATE_MESSAGE = "Missing car state";
	long MAX_KILOMETERS = 0;
	long MIN_KILOMETERS = 1500000;
	long MIN_PERSON_ID_VALUE = 100000l;
	long MAX_PERSON_ID_VALUE = 999999l;
	long max_year = Calendar.getInstance().get(Calendar.YEAR);
	long MAX_YEAR = 2024;
	long MIN_YEAR = 1900;
	long MIN_ENGINE_POWER = 50;
	long MAX_ENGINE_POWER = 5500;
	long MIN_ENGINE_CAPACITY = 500;
	long MAX_ENGINE_CAPACITY = 10000;
	String INVALID_COLOR_MESSAGE = "car state must be one of: RED, SILVER, WHITE";
	String INVALID_CAR_STATE_MESSAGE = "car state must be one of: OLD, NEW, GOOD, MIDDLE, BAD";
	String WRONG_MIN_KILOMETERS_MESSAGE = "Kilometers must not be less then " + MIN_KILOMETERS;
	String WRONG_MAX_KILOMETERS_MESSAGE = "Kilometers must not be greater then " + MAX_KILOMETERS;
	String WRONG_MIN_ENGINE_CAPACITY_MESSAGE = "Engine capacity must not be less then " + MIN_ENGINE_CAPACITY;
	String WRONG_MAX_ENGINE_CAPACITY_MESSAGE = "Engine capacity must not be greater then " + MAX_ENGINE_CAPACITY;
	String WRONG_MIN_ENGINE_POWER_MESSAGE = "Engine power must not be less then " + MIN_ENGINE_POWER;
	String WRONG_MAX_ENGINE_POWER_MESSAGE = "Engine power must not be greater then " + MAX_ENGINE_POWER;
	String WRONG_MIN_YEAR_MESSAGE = "Year must not be less then " + MIN_YEAR;
	String WRONG_MAX_YEAR_MESSAGE = "Year must not be greater then " + MAX_YEAR;
	String WRONG_MIN_PERSON_ID_VALUE = "Person ID must be greater or equal " + MIN_PERSON_ID_VALUE;
	String WRONG_MAX_PERSON_ID_VALUE = "Person ID must be less or equal " + MAX_PERSON_ID_VALUE;
	String MISSING_PERSON_NAME_MESSAGE = "Missing person name";
	String MISSING_BIRTH_DATE_MESSAGE = "Missing person's birth date";
	String MISSING_DEAL_DATE_MESSAGE = "Missing deal's date";
	String DATE_REGEXP = "\\d{4}-\\d{2}-\\d{2}";	
	String WRONG_DATE_FORMAT = "Wrong date format, must be YYYY-MM-dd";
	String MISSING_PERSON_EMAIL = "Missing email address";
	String WRONG_EMAIL_FORMAT = "Wrong email format";
}