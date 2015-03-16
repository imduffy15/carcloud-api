package ie.ianduffy.carcloud.web.munic.factory;

import ie.ianduffy.carcloud.domain.Field;
import ie.ianduffy.carcloud.domain.FieldBoolean;
import ie.ianduffy.carcloud.domain.FieldInteger;
import ie.ianduffy.carcloud.domain.FieldString;
import ie.ianduffy.carcloud.web.munic.util.DecoderUtil;

import java.util.HashMap;

public class FieldFactory {

    private static HashMap<String, FieldType> fieldDefinations = new HashMap() {{
        put("GPRMC_VALID", FieldType.STRING);
        put("GPS_SPEED", FieldType.INTEGER);
        put("GPS_DIR", FieldType.INTEGER);
        put("DIO_IGNITION", FieldType.BOOLEAN);
        put("ODO_PARTIAL_KM", FieldType.STRING);
        put("ODO_FULL", FieldType.INTEGER);
        put("GPS_PDOP", FieldType.INTEGER);
        put("AREA_LIST", FieldType.STRING);
        put("GPS_FIXED_SAT_NUM", FieldType.INTEGER);
        put("BEHAVE_ID", FieldType.INTEGER);
        put("BEHAVE_LONG", FieldType.INTEGER);
        put("BEHAVE_LAT", FieldType.INTEGER);
        put("BEHAVE_DAY_OF_YEAR", FieldType.INTEGER);
        put("BEHAVE_TIME_OF_DAY", FieldType.INTEGER);
        put("BEHAVE_GPS_SPEED_BEGIN", FieldType.INTEGER);
        put("BEHAVE_GPS_SPEED_PEAK", FieldType.INTEGER);
        put("BEHAVE_GPS_SPEED_END", FieldType.INTEGER);
        put("BEHAVE_GPS_HEADING_BEGIN", FieldType.INTEGER);
        put("BEHAVE_GPS_HEADING_PEAK", FieldType.INTEGER);
        put("BEHAVE_GPS_HEADING_END", FieldType.INTEGER);
        put("BEHAVE_ACC_X_BEGIN", FieldType.INTEGER);
        put("BEHAVE_ACC_X_PEAK", FieldType.INTEGER);
        put("BEHAVE_ACC_X_END", FieldType.INTEGER);
        put("BEHAVE_ACC_Y_BEGIN", FieldType.INTEGER);
        put("BEHAVE_ACC_Y_PEAK", FieldType.INTEGER);
        put("BEHAVE_ACC_Y_END", FieldType.INTEGER);
        put("BEHAVE_ACC_Z_BEGIN", FieldType.INTEGER);
        put("BEHAVE_ACC_Z_PEAK", FieldType.INTEGER);
        put("BEHAVE_ACC_Z_END", FieldType.INTEGER);
        put("BEHAVE_ELAPSED", FieldType.INTEGER);
        put("BEHAVE_UNIQUE_ID", FieldType.INTEGER);
        put("MDI_CRASH_DETECTED", FieldType.STRING);
        put("MDI_EXT_BATT_LOW", FieldType.BOOLEAN);
        put("MDI_EXT_BATT_VOLTAGE", FieldType.INTEGER);
        put("MDI_PANIC_STATE", FieldType.BOOLEAN);
        put("MDI_PANIC_MESSAGE", FieldType.STRING);
        put("MDI_DTC_MIL", FieldType.BOOLEAN);
        put("MDI_DTC_NUMBER", FieldType.INTEGER);
        put("MDI_DTC_LIST", FieldType.STRING);
        put("MDI_RPM_MAX", FieldType.INTEGER);
        put("MDI_RPM_MIN", FieldType.INTEGER);
        put("MDI_RPM_AVERAGE", FieldType.INTEGER);
        put("MDI_RPM_OVER", FieldType.BOOLEAN);
        put("MDI_RPM_AVERAGE_RANGE_1", FieldType.INTEGER);
        put("MDI_RPM_AVERAGE_RANGE_2", FieldType.INTEGER);
        put("MDI_RPM_AVERAGE_RANGE_3", FieldType.INTEGER);
        put("MDI_RPM_AVERAGE_RANGE_4", FieldType.INTEGER);
        put("MDI_SENSORS_RECORDER_DATA", FieldType.STRING);
        put("MDI_SENSORS_RECORDER_CALIBRATION", FieldType.STRING);
        put("ODO_PARTIAL_METER", FieldType.INTEGER);
        put("ODO_FULL_METER", FieldType.INTEGER);
        put("MDI_OBD_PID_1", FieldType.STRING);
        put("MDI_OBD_PID_2", FieldType.STRING);
        put("MDI_OBD_PID_3", FieldType.STRING);
        put("MDI_OBD_PID_4", FieldType.STRING);
        put("MDI_OBD_PID_5", FieldType.STRING);
        put("MDI_DASHBOARD_MILEAGE", FieldType.INTEGER);
        put("MDI_DASHBOARD_FUEL", FieldType.INTEGER);
        put("MDI_DASHBOARD_FUEL_LEVEL", FieldType.INTEGER);
        put("MDI_DIAG_1", FieldType.STRING);
        put("MDI_DIAG_2", FieldType.STRING);
        put("MDI_DIAG_3", FieldType.STRING);
        put("MDI_VEHICLE_STATE", FieldType.STRING);
        put("MDI_OBD_SPEED", FieldType.INTEGER);
        put("MDI_OBD_RPM", FieldType.INTEGER);
        put("MDI_OBD_FUEL", FieldType.INTEGER);
        put("MDI_OBD_VIN", FieldType.STRING);
        put("MDI_OBD_MILEAGE", FieldType.INTEGER);
        put("MDI_JOURNEY_TIME", FieldType.INTEGER);
        put("MDI_IDLE_JOURNEY", FieldType.INTEGER);
        put("MDI_DRIVING_JOURNEY", FieldType.INTEGER);
        put("MDI_OVERSPEED_COUNTER", FieldType.INTEGER);
        put("MDI_TOW_AWAY", FieldType.BOOLEAN);
        put("MDI_ODO_JOURNEY", FieldType.INTEGER);
        put("MDI_OVERSPEED", FieldType.BOOLEAN);
        put("MDI_MAX_SPEED_JOURNEY", FieldType.INTEGER);
        put("MDI_JOURNEY_STATE", FieldType.BOOLEAN);
        put("MDI_RECORD_REASON", FieldType.STRING);
    }};

    public static Field getFieldDTO(String key, String value) {
        switch (fieldDefinations.get(key)) {
            case INTEGER:
                return new FieldInteger(key, (DecoderUtil.decodeToInt(value)));
            case BOOLEAN:
                return new FieldBoolean(key, (DecoderUtil.decodeToBoolean(value)));
            case STRING:
                return new FieldString(key, DecoderUtil.decodeToString(value));
            default:
                throw new IllegalArgumentException();
        }
    }

    private enum FieldType {
        STRING, INTEGER, BOOLEAN;
    }
}
