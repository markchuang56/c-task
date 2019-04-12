package org.bluetooth.bledemo;

import java.util.UUID;

public class BleDefinedUUIDs {
	
	public static class Service {
		final static public UUID HEART_RATE               	= UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");
		final static public UUID GLS_H2_SERVICE_UUID = UUID.fromString("AE618100-3266-4BBA-9626-06CBE7657213");
		//final static public UUID GLS_H2_SERVICE_UUID = UUID.fromString("ECBE3980-C9A2-11E1-B1BD-0002A5D5C51B");
		final static public UUID CURRENT_TIME_SERVICE		= UUID.fromString("00001805-0000-1000-8000-00805f9b34fb");

        final static public UUID ROCHE_OTHER_SERVICE_UUID	= UUID.fromString("00000000-0000-1000-1000-000000000000");
        final static public UUID BLE_BGM_SERVICE_UUID		= UUID.fromString("00001808-0000-1000-8000-00805f9b34fb");
		final static public UUID BLE_DEV_INFO_UUID		= UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb");
	};

	public static class Characteristic {
		/** Glucose Measurement characteristic UUID */
		final static public UUID H2BT_CABLE_CHARACTERISTIC = UUID.fromString("AE618101-3266-4BBA-9626-06CBE7657213");
		/** Glucose Measurement Context characteristic UUID */
		final static public UUID H2BT_METER_CHARACTERISTIC = UUID.fromString("AE618102-3266-4BBA-9626-06CBE7657213");

		final static public UUID HEART_RATE_MEASUREMENT   = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
		final static public UUID MANUFACTURER_STRING      = UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb");
		final static public UUID MODEL_NUMBER_STRING      = UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb");
		final static public UUID FIRMWARE_REVISION_STRING = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");
		final static public UUID APPEARANCE               = UUID.fromString("00002a01-0000-1000-8000-00805f9b34fb");
		final static public UUID BODY_SENSOR_LOCATION     = UUID.fromString("00002a38-0000-1000-8000-00805f9b34fb");
		final static public UUID BATTERY_LEVEL            = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");

		//final static public UUID CURRENT_TIME            = UUID.fromString("00002a2b-0000-1000-8000-00805f9b34fb");
		final static public UUID LOCAL_TIME_UUID           	 = UUID.fromString("00002a0f-0000-1000-8000-00805f9b34fb");
		final static public UUID CURRENT_TIME_UUID            = UUID.fromString("00002a08-0000-1000-8000-00805f9b34fb");

		final static public UUID BG_CONTROL_UUID           	= UUID.fromString("00002a52-0000-1000-8000-00805f9b34fb");
		final static public UUID BG_MEASUREMENT_UUID 		= UUID.fromString("00002a18-0000-1000-8000-00805f9b34fb");
		final static public UUID BG_CONTEXT_UUID            	= UUID.fromString("00002a34-0000-1000-8000-00805f9b34fb");

		final static public UUID BG_SN_UUID            		= UUID.fromString("00002a25-0000-1000-8000-00805f9b34fb");

		final static public UUID ROCHE_OTHER_UUID            	= UUID.fromString("00000000-0000-1000-1000-000000000001");
	}



	public static class Descriptor {
		final static public UUID CHAR_CLIENT_CONFIG       = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
	}
	
}
