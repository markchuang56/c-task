package org.bluetooth.bledemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import org.bluetooth.idata.IData;

//import android.content.Context;


public class BleWrapper {
    private Handler mHandler = new Handler();
	/* defines (in milliseconds) how often RSSI should be updated */
    private static final int RSSI_UPDATE_TIME_INTERVAL = 1500; // 1.5 seconds

    /* callback object through which we are returning results to the caller */
    private BleWrapperUiCallbacks mUiCallback = null;

    /* */
    public  Encrypter mEncrypter = null;
    //public Context context;
    /* define NULL object for UI callbacks */
    private static final BleWrapperUiCallbacks NULL_CALLBACK = new BleWrapperUiCallbacks.Null();

    /* H2BT COMMAND SELECT */
    static int h2CmdSel = 0;
    static int h2RocheCmdFlow = 1;
    static int rocheCurrentTimeFlow = 0;
    static int rocheGetCurrentTimeFlow = 0;

    /* Arkray Test ...*/
    public static final byte SYN = (byte) 22;
    public static final byte S_ASCII = (byte) 83;
    public static final byte T_ASCII = (byte) 84;
    public static final byte W_ASCII = (byte) 87;
    public static final byte e_ASCII = (byte) 101;
    private final int CommonK1 = 170;
    private final int CommonK2 = 85;
    private final int CommonK3 = 20;
    private final int CommonK4 = 146;
    private int _K1;
    private int _K2;
    private int _K3;
    private int _K4;


    private int rocheLoop = 3;

    /* ROCHE SERVICES */
    public BluetoothGattService mRocheServiceOther = null;
    public BluetoothGattService mRocheServiceBgm = null;
    public BluetoothGattService mRocheServiceDevInfo = null;
    public BluetoothGattService mH2BTServiceCable = null;

    /* H2BT Characteristic */
    public BluetoothGattCharacteristic mH2BTCharacteristicCable = null;
    public BluetoothGattCharacteristic mH2BTCharacteristicMeter = null;

    public BluetoothGattCharacteristic mRoCharacteristicCurrentTime = null;
    public BluetoothGattCharacteristic mRoCharacteristicControl = null;
    public BluetoothGattCharacteristic mRoCharacteristicMeasurement = null;
    public BluetoothGattCharacteristic mRoCharacteristicContext = null;
    public BluetoothGattCharacteristic mRoCharacteristicSerialNumber = null;

    public BluetoothGattCharacteristic mRoCharacteristicOthers = null;


    public BluetoothGattCharacteristic mH2BTCharacteristic = null;

    /* creates BleWrapper object, set its parent activity and callback object */
    public BleWrapper(Activity parent, BleWrapperUiCallbacks callback) {
    	this.mParent = parent;
    	mUiCallback = callback;
    	if(mUiCallback == null) mUiCallback = NULL_CALLBACK;
    }

    public BluetoothManager           getManager() { return mBluetoothManager; }
    public BluetoothAdapter           getAdapter() { return mBluetoothAdapter; }
    public BluetoothDevice            getDevice()  { return mBluetoothDevice; }
    public BluetoothGatt              getGatt()    { return mBluetoothGatt; }
    public BluetoothGattService       getCachedService() { return mBluetoothSelectedService; }
    public List<BluetoothGattService> getCachedServices() { return mBluetoothGattServices; }
    public boolean                    isConnected() { return mConnected; }

	/* run test and check if this device has BT and BLE hardware available */
	public boolean checkBleHardwareAvailable() {
		// First check general Bluetooth Hardware:
		// get BluetoothManager...
		final BluetoothManager manager = (BluetoothManager) mParent.getSystemService(Context.BLUETOOTH_SERVICE);
		if(manager == null) return false;
		// .. and then get adapter from manager
		final BluetoothAdapter adapter = manager.getAdapter();
		if(adapter == null) return false;

		// and then check if BT LE is also available
		boolean hasBle = mParent.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
		return hasBle;
	}


	/* before any action check if BT is turned ON and enabled for us
	 * call this in onResume to be always sure that BT is ON when Your
	 * application is put into the foreground */
	public boolean isBtEnabled() {
		final BluetoothManager manager = (BluetoothManager) mParent.getSystemService(Context.BLUETOOTH_SERVICE);
		if(manager == null) return false;

		final BluetoothAdapter adapter = manager.getAdapter();
		if(adapter == null) return false;

		return adapter.isEnabled();
	}

	/* start scanning for BT LE devices around */
	public void startScanning() {

        //GenerateEncrypter(Context context) {
        //BleWrapper.context = ;
        //mEncrypter.AndroidGenerateEncrypter();

        //AppContext.
        //context = Context.getContext();
        //mEncrypter = new

        Log.e("H2BT","H2 DEBUG ..... SCAN");


        ScanSettings.Builder builder = new ScanSettings.Builder();
        mBluetoothAdapter.getBluetoothLeScanner().startScan(null, builder.build(), mDeviceFoundCallback);
	/* Add Bye Jason, for Arkray */
        byte val = Encrypt(22);

        final StringBuilder stringBuilder = new StringBuilder(1);
        //for(byte byteChar : mRawValue)
        //stringBuilder.append(String.format("%02X", byteChar));
        stringBuilder.append(String.format("%02X", val));
        //mAsciiValue = "0x" + stringBuilder.toString();
        String value = stringBuilder.toString();
        Log.i("H2BT", "CMD = " + value);

        RotateLeft(5, 4);
	}

	/* stops current scanning */
	public void stopScanning() {
		mBluetoothAdapter.getBluetoothLeScanner().stopScan(mDeviceFoundCallback);
	}

    /* initialize BLE and get BT Manager & Adapter */
    public boolean initialize() {
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) mParent.getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                return false;
            }
        }

        if(mBluetoothAdapter == null) mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            return false;
        }
        return true;
    }

    /* connect to the device with specified address */
    public boolean connect(final String deviceAddress) {
        if (mBluetoothAdapter == null || deviceAddress == null) return false;
        mDeviceAddress = deviceAddress;

        // check if we need to connect from scratch or just reconnect to previous device
        if(mBluetoothGatt != null && mBluetoothGatt.getDevice().getAddress().equals(deviceAddress)) {
        	// just reconnect
        	return mBluetoothGatt.connect();
        }
        else {
        	// connect from scratch
            // get BluetoothDevice object for specified address
            mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
            if (mBluetoothDevice == null) {
                // we got wrong address - that device is not available!
                return false;
            }
            // connect with remote device
        	mBluetoothGatt = mBluetoothDevice.connectGatt(mParent, false, mBleCallback);
        }
        return true;
    }

    /* disconnect the device. It is still possible to reconnect to it later with this Gatt client */
    public void diconnect() {
    	if(mBluetoothGatt != null) mBluetoothGatt.disconnect();
    	 mUiCallback.uiDeviceDisconnected(mBluetoothGatt, mBluetoothDevice);
    }

    /* close GATT client completely */
    public void close() {
    	if(mBluetoothGatt != null) mBluetoothGatt.close();
    	mBluetoothGatt = null;
    }

    /* request new RSSi value for the connection*/
    public void readPeriodicalyRssiValue(final boolean repeat) {
    	mTimerEnabled = repeat;
    	// check if we should stop checking RSSI value
    	if(mConnected == false || mBluetoothGatt == null || mTimerEnabled == false) {
    		mTimerEnabled = false;
    		return;
    	}

    	mTimerHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if(mBluetoothGatt == null ||
				   mBluetoothAdapter == null ||
				   mConnected == false)
				{
					mTimerEnabled = false;
					return;
				}

				// request RSSI value
				mBluetoothGatt.readRemoteRssi();
				// add call it once more in the future
				readPeriodicalyRssiValue(mTimerEnabled);
			}
    	}, RSSI_UPDATE_TIME_INTERVAL);
    }

    /* starts monitoring RSSI value */
    public void startMonitoringRssiValue() {
    	readPeriodicalyRssiValue(true);
    }

    /* stops monitoring of RSSI value */
    public void stopMonitoringRssiValue() {
    	readPeriodicalyRssiValue(false);
    }

    /* request to discover all services available on the remote devices
     * results are delivered through callback object */
    public void startServicesDiscovery() {
    	if(mBluetoothGatt != null) mBluetoothGatt.discoverServices();
    }

    /* gets services and calls UI callback to handle them
     * before calling getServices() make sure service discovery is finished! */
    public void getSupportedServices() {
    	if(mBluetoothGattServices != null && mBluetoothGattServices.size() > 0) mBluetoothGattServices.clear();
    	// keep reference to all services in local array:
        if(mBluetoothGatt != null) mBluetoothGattServices = mBluetoothGatt.getServices();

        mUiCallback.uiAvailableServices(mBluetoothGatt, mBluetoothDevice, mBluetoothGattServices);
    }

    /* get all characteristic for particular service and pass them to the UI callback */
    public void getCharacteristicsForService(final BluetoothGattService service) {
    	if(service == null) return;
    	List<BluetoothGattCharacteristic> chars = null;

    	chars = service.getCharacteristics();
    	mUiCallback.uiCharacteristicForService(mBluetoothGatt, mBluetoothDevice, service, chars);
    	// keep reference to the last selected service
    	mBluetoothSelectedService = service;
    }

    /* request to fetch newest value stored on the remote device for particular characteristic */
    public void requestCharacteristicValue(BluetoothGattCharacteristic ch) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) return;

        mBluetoothGatt.readCharacteristic(ch);
        // new value available will be notified in Callback Object
    }

    /* get characteristic's value (and parse it for some types of characteristics)
     * before calling this You should always update the value by calling requestCharacteristicValue() */
    public void getCharacteristicValue(BluetoothGattCharacteristic ch) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null || ch == null) return;

        byte[] rawValue = ch.getValue();
        String strValue = null;
        int intValue = 0;

        // lets read and do real parsing of some characteristic to get meaningful value from it
        UUID uuid = ch.getUuid();

        Log.e("H2BT","BACK--- ... HAHA" + uuid);

        if(uuid.equals(BleDefinedUUIDs.Characteristic.HEART_RATE_MEASUREMENT)) { // heart rate
        	// follow https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
        	// first check format used by the device - it is specified in bit 0 and tells us if we should ask for index 1 (and uint8) or index 2 (and uint16)
        	int index = ((rawValue[0] & 0x01) == 1) ? 2 : 1;
        	// also we need to define format
        	int format = (index == 1) ? BluetoothGattCharacteristic.FORMAT_UINT8 : BluetoothGattCharacteristic.FORMAT_UINT16;
        	// now we have everything, get the value
        	intValue = ch.getIntValue(format, index);
        	strValue = intValue + " bpm"; // it is always in bpm units
        }
        else if (uuid.equals(BleDefinedUUIDs.Characteristic.HEART_RATE_MEASUREMENT) || // manufacturer name string
        		 uuid.equals(BleDefinedUUIDs.Characteristic.MODEL_NUMBER_STRING) || // model number string)
        		 uuid.equals(BleDefinedUUIDs.Characteristic.FIRMWARE_REVISION_STRING)) // firmware revision string
        {
        	// follow https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.manufacturer_name_string.xml etc.
        	// string value are usually simple utf8s string at index 0
        	strValue = ch.getStringValue(0);
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.APPEARANCE)) { // appearance
        	// follow: https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.gap.appearance.xml
        	intValue  = ((int)rawValue[1]) << 8;
        	intValue += rawValue[0];
        	strValue = BleNamesResolver.resolveAppearance(intValue);
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.BODY_SENSOR_LOCATION)) { // body sensor location
        	// follow: https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.body_sensor_location.xml
        	intValue = rawValue[0];
        	strValue = BleNamesResolver.resolveHeartRateSensorLocation(intValue);
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.BATTERY_LEVEL)) { // battery level
        	// follow: https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.battery_level.xml
        	intValue = rawValue[0];
        	strValue = "" + intValue + "% battery level";
        }
        //

        else if (uuid.equals(BleDefinedUUIDs.Characteristic.CURRENT_TIME_UUID)) {
            Log.e("H2BT","CURRENT TIME ... HAHA");
            rocheReadSNTimeout();
        }

        else if(uuid.equals(BleDefinedUUIDs.Characteristic.LOCAL_TIME_UUID)) {
            Log.e("H2BT","BACK-TIME ... HAHA");
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.BG_CONTROL_UUID)) {
            Log.e("H2BT","BACK-CTRL ... HAHA");

        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.BG_MEASUREMENT_UUID)) {
            Log.e("H2BT","BACK-MEASUREMENT ... HAHA");
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.BG_CONTEXT_UUID)) {
            Log.e("H2BT","BACK-CONTEXT ... HAHA");
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.BG_SN_UUID)) {
            Log.e("H2BT","BACK-SN ... HAHA");
            rocheWriteCtrlTimeout();
        }

        else if(uuid.equals(BleDefinedUUIDs.Characteristic.ROCHE_OTHER_UUID)) {
            Log.e("H2BT","BACK-OTHERS ... GOOD!!");
            //rocheWriteCtrlTimeout();
        }

        else {
        	// not known type of characteristic, so we need to handle this in "general" way
        	// get first four bytes and transform it to integer
            Log.e("H2BT","BACK-ERROR ... FAIL");
        	intValue = 0;
        	if(rawValue.length > 0) intValue = (int)rawValue[0];
        	if(rawValue.length > 1) intValue = intValue + ((int)rawValue[1] << 8);
        	if(rawValue.length > 2) intValue = intValue + ((int)rawValue[2] << 8);
        	if(rawValue.length > 3) intValue = intValue + ((int)rawValue[3] << 8);

            if (rawValue.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(rawValue.length);
                for(byte byteChar : rawValue) {
                    stringBuilder.append(String.format("%c", byteChar));
                }
                strValue = stringBuilder.toString();

                // H2BT CYCLE
                Log.i("H2BT","write cycle ... DATA IN" + h2CmdSel + " " + strValue);
                if (strValue.equals("EOC") || strValue.equals("EOM")){
                    addCharWriteTimeout();
                    Log.i("H2BT","DATA IN - EOC OR EOM");
                }
            }
        }

        Log.e("---++---", "H2BT VALUE ...." + " " );

        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS").format(new Date());
        mUiCallback.uiNewValueForCharacteristic(mBluetoothGatt,
                                                mBluetoothDevice,
                                                mBluetoothSelectedService,
        		                                ch,
        		                                strValue,
        		                                intValue,
        		                                rawValue,
        		                                timestamp);
    }

    // SN
    private void rocheReadSNTimeout() {
        Log.e("---++---", "H2BT ROCHE READ SN ...." + " ");
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                requestCharacteristicValue(mRoCharacteristicSerialNumber);
            }
        };
        mHandler.postDelayed(timeout, 200);//SCANNING_TIMEOUT);
    }

    // CONTROL
    private void rocheWriteCtrlTimeout() {

        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                Log.e("---++---", "H2BT ROCHE WRITE CTRL ...." + " " + mRoCharacteristicControl);
                //byte[] dataToWriteBg = { 0x01, 0x03, 0x01, 0x01, 0x00};
                byte[] dataToWriteBg = { 0x01, 0x03, 0x01, 0x00, 0x00};
                writeDataToCharacteristic(mRoCharacteristicControl, dataToWriteBg);
            }
        };
        mHandler.postDelayed(timeout, 1000);//SCANNING_TIMEOUT);
    }

    // ROCHE TIME OUT
    private void rocheCharWriteTimeout() {
        Log.e("---++---", "H2BT ROCHE WRITE CYCLE ...." + " ");
        Runnable timeout = new Runnable() {
            @Override
            public void run() {

                Log.i("H2BT","write cycle ... HAHA" + " "+ h2CmdSel);

                byte[] dataToWriteBg = { 0x01, 0x03, 0x01, 0x07 , 0x02};
                writeDataToCharacteristic(mRoCharacteristicControl, dataToWriteBg);
            }
        };
        mHandler.postDelayed(timeout, 1000);//SCANNING_TIMEOUT);
    }

// H2BT TIME OUT
    private void addCharWriteTimeout() {
        Log.e("---++---", "H2BT WRITE CYCLE ...." + " ");
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                /*
                if(mBleWrapper == null) return;
                mScanning = false;
                mBleWrapper.stopScanning();
                invalidateOptionsMenu();
                */
                System.out.println("write cycle ... " + mH2BTCharacteristicCable);
                System.out.println("write cycle ... " + mH2BTCharacteristicMeter);
                //DebugLogger.d(TAG, "Response result for:");
     //           h2CmdSel +=2;
                Log.i("H2BT","write cycle ... HAHA" + " "+ h2CmdSel);
                byte[] dataToWriteEOH = { 'E', 'O', 'H'};
                byte[] dataToWriteVersion = { 0x00, 0x00, 0x0b, 0x00 , 0x00, 0x00, 0x00, (byte) 0x96,
                        0x00, 0x00, 0x00, (byte) 0x9d };

                byte[] dataToWriteUart = { 0x00, 0x00, 0x11, 0x00, 0x00, 0x00, 0x00, (byte) 0x93,
                        0x00, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x81
                };

                byte[] dataToWriteUltr2Init = {0x0c, 0x06, 0x0b, 0x00 , 0x00, 0x00, 0x11, 0x0d,
                        0x44, 0x4d, 0x40, 0x54};

                byte[] dataToWriteUltra2CurrentTime = {0x0e, 0x06, 0x0b, 0x00, 0x00, 0x00, 0x11, 0x0d,
                        0x44, 0x4d, 0x46, 0x50};

                byte[] dataToWriteUltra2 = {0x0f, 0x06, 0x0b, 0x00, 0x00, 0x00, 0x11, 0x0d,
                        0x44, 0x4d, 0x3f, 0x28};

                byte[] dataToWriteUltra2Unit = {0x03, 0x06, 0x0d, 0x00, 0x00, 0x00, 0x11, 0x0d,
                        0x44, 0x4d, 0x53, 0x55 , 0x3f, 0x24};
                byte[] dataToWriteUltra2Record = { 0x08, 0x06, 0x0b, 0x00, 0x00, 0x00, 0x11, 0x0d,
                        0x44, 0x4d, 0x50, 0x40
                };
/*
				byte[] dataToWriteUart = { 0x00, 0x00, 0x11, 0x00, 0x00, 0x00, 0x00, (byte) 0x93,
							(byte) 0x80, 0x03, 0x00, (byte) 0xc0, 0x00, 0x00, 0x00, 0x00,
							0x00, (byte) 0xc1};
*/

                switch (h2CmdSel){
                    case 0:
                        break;
                    case 1:
                        writeDataToCharacteristic(mH2BTCharacteristicCable, dataToWriteEOH);
                        break;

                    case 2:
                        writeDataToCharacteristic(mH2BTCharacteristicCable, dataToWriteVersion);
                        mH2BTCharacteristic = mH2BTCharacteristicCable;
                        addH2BTCharWriteTimeoutEOH();
                        break;

                    case 3:
                        writeDataToCharacteristic(mH2BTCharacteristicCable, dataToWriteEOH);
                        break;

                    case 4:
                        writeDataToCharacteristic(mH2BTCharacteristicCable, dataToWriteUart);
                        mH2BTCharacteristic = mH2BTCharacteristicCable;
                        addH2BTCharWriteTimeoutEOH();
                        break;

                    case 5:
                        writeDataToCharacteristic(mH2BTCharacteristicCable, dataToWriteEOH);
                        break;

                    //

                    case 6:
                        writeDataToCharacteristic(mH2BTCharacteristicMeter, dataToWriteUltr2Init);
                        mH2BTCharacteristic = mH2BTCharacteristicMeter;
                        addH2BTCharWriteTimeoutEOH();
                        break;

                    case 7:
                        writeDataToCharacteristic(mH2BTCharacteristicMeter, dataToWriteEOH);
                        break;

                    case 8:
                        writeDataToCharacteristic(mH2BTCharacteristicMeter, dataToWriteUltra2CurrentTime);
                        mH2BTCharacteristic = mH2BTCharacteristicMeter;
                        addH2BTCharWriteTimeoutEOH();
                        break;

                    case 9:
                        writeDataToCharacteristic(mH2BTCharacteristicMeter, dataToWriteEOH);
                        break;


                    case 10:
                        writeDataToCharacteristic(mH2BTCharacteristicMeter, dataToWriteUltra2);
                        mH2BTCharacteristic = mH2BTCharacteristicMeter;
                        addH2BTCharWriteTimeoutEOH();
                        break;

                    case 11:
                        writeDataToCharacteristic(mH2BTCharacteristicMeter, dataToWriteEOH);
                        break;

                    case 12:
                        writeDataToCharacteristic(mH2BTCharacteristicMeter, dataToWriteUltra2Unit);
                        mH2BTCharacteristic = mH2BTCharacteristicMeter;
                        addH2BTCharWriteTimeoutEOH();
                        break;

                    case 13:
                        writeDataToCharacteristic(mH2BTCharacteristicMeter, dataToWriteEOH);
                        break;

                    case 14:
                        writeDataToCharacteristic(mH2BTCharacteristicMeter, dataToWriteUltra2Record);
                        mH2BTCharacteristic = mH2BTCharacteristicMeter;
                        addH2BTCharWriteTimeoutEOH();
                        break;

                    case 15:
                        writeDataToCharacteristic(mH2BTCharacteristicMeter, dataToWriteEOH);
                        break;

                    default:
                        break;
                }
            }
        };
        mHandler.postDelayed(timeout, 1000);//SCANNING_TIMEOUT);
    }

    public void addH2BTCharWriteTimeoutEOH() {
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                /*
                if(mBleWrapper == null) return;
                mScanning = false;
                mBleWrapper.stopScanning();
                invalidateOptionsMenu();
                */
                System.out.println("write EOH ... " + mH2BTCharacteristic + " and " + h2CmdSel);
                byte[] dataToWriteEOH = { 'E', 'O', 'H'};

                writeDataToCharacteristic(mH2BTCharacteristic, dataToWriteEOH);
                h2CmdSel +=2;

            }
        };
        mHandler.postDelayed(timeout, 1000);//SCANNING_TIMEOUT);
    }

    /* reads and return what what FORMAT is indicated by characteristic's properties
     * seems that value makes no sense in most cases */
    public int getValueFormat(BluetoothGattCharacteristic ch) {
    	int properties = ch.getProperties();

    	if((BluetoothGattCharacteristic.FORMAT_FLOAT & properties) != 0) return BluetoothGattCharacteristic.FORMAT_FLOAT;
    	if((BluetoothGattCharacteristic.FORMAT_SFLOAT & properties) != 0) return BluetoothGattCharacteristic.FORMAT_SFLOAT;
    	if((BluetoothGattCharacteristic.FORMAT_SINT16 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_SINT16;
    	if((BluetoothGattCharacteristic.FORMAT_SINT32 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_SINT32;
    	if((BluetoothGattCharacteristic.FORMAT_SINT8 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_SINT8;
    	if((BluetoothGattCharacteristic.FORMAT_UINT16 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_UINT16;
    	if((BluetoothGattCharacteristic.FORMAT_UINT32 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_UINT32;
    	if((BluetoothGattCharacteristic.FORMAT_UINT8 & properties) != 0) return BluetoothGattCharacteristic.FORMAT_UINT8;

    	return 0;
    }

    /* set new value for particular characteristic */
    public void writeDataToCharacteristic(final BluetoothGattCharacteristic ch, final byte[] dataToWrite) {
        Log.e("---++---", "H2BT WRITE DATA TO ...." + " " + dataToWrite);
        Log.e("---++---", "H2BT WRITE DATA TO ...." + " " + ch);
    	if (mBluetoothAdapter == null || mBluetoothGatt == null || ch == null) {
            Log.e("---++---", "H2BT WRITE with NULL OBJECT");
    	    return;
        }

    	// first set it locally....
    	ch.setValue(dataToWrite);
    	// ... and then "commit" changes to the peripheral
        boolean successfullyWritten = mBluetoothGatt.writeCharacteristic(ch);
        if (successfullyWritten) {
            Log.e("---++---", "H2BT WRITE DATA (OK)");
        }
    }

    /* enables/disables notification for characteristic */
    public void setNotificationForCharacteristic(BluetoothGattCharacteristic ch, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) return;

        boolean success = mBluetoothGatt.setCharacteristicNotification(ch, enabled);
        if(!success) {
        	Log.e("------", "Seting proper notification status for characteristic failed!");
        }else {
            UUID uuid = ch.getUuid();
            Log.e("---++---", "H2BT NOTIFICATION" + " " + uuid);
        }

        // This is also sometimes required (e.g. for heart rate monitors) to enable notifications/indications
        // see: https://developer.bluetooth.org/gatt/descriptors/Pages/DescriptorViewer.aspx?u=org.bluetooth.descriptor.gatt.client_characteristic_configuration.xml
        BluetoothGattDescriptor descriptor = ch.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
        if(descriptor != null) {
        	byte[] val = enabled ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
	        descriptor.setValue(val);
	        mBluetoothGatt.writeDescriptor(descriptor);
        }

    }


    private ScanCallback mDeviceFoundCallback = new ScanCallback() {
        public void onScanResult(int callbackType, ScanResult result) {
            mUiCallback.uiDeviceFound(result.getDevice(), result.getRssi(), result.getScanRecord().getBytes());
        }

        /**
         * Callback when batch results are delivered.
         *
         * @param results List of scan results that are previously scanned.
         */
        public void onBatchScanResults(List<ScanResult> results) {
        }

        /**
         * Callback when scan could not be started.
         *
         * @param errorCode Error code (one of SCAN_FAILED_*) for scan failure.
         */
        public void onScanFailed(int errorCode) {
        }

    };

    /* callbacks called for any action on particular Ble Device */
    private final BluetoothGattCallback mBleCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
            	mConnected = true;
            	mUiCallback.uiDeviceConnected(mBluetoothGatt, mBluetoothDevice);

            	// now we can start talking with the device, e.g.
            	mBluetoothGatt.readRemoteRssi();
            	// response will be delivered to callback object!

            	// in our case we would also like automatically to call for services discovery
            	startServicesDiscovery();

            	// and we also want to get RSSI value to be updated periodically
            	startMonitoringRssiValue();
            }
            else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mConnected = false;
                mUiCallback.uiDeviceDisconnected(mBluetoothGatt, mBluetoothDevice);
//                try {
//                    mBluetoothGatt.close();
//                } catch (Exception e) {
//                    Log.d("DDDD", "close ignoring: " + e);
//                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
            	// now, when services discovery is finished, we can call getServices() for Gatt
            	getSupportedServices();
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status)
        {
        	// we got response regarding our request to fetch characteristic value
            if (status == BluetoothGatt.GATT_SUCCESS) {
            	// and it success, so we can get the value
            	//getCharacteristicValue(characteristic);
                getRocheCharacteristicValue(characteristic);
                Log.e("---++---", "ROCHE READ SUCCESS ....");
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic)
        {
            Log.e("---++---", "ROCHE CHANGED SUCCESS ....");
        	// characteristic's value was updated due to enabled notification, lets get this value
        	// the value itself will be reported to the UI inside getCharacteristicValue
        	 //getCharacteristicValue(characteristic);
            getRocheCharacteristicValue(characteristic);
        	// also, notify UI that notification are enabled for particular characteristic
        	 mUiCallback.uiGotNotification(mBluetoothGatt, mBluetoothDevice, mBluetoothSelectedService, characteristic);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        	String deviceName = gatt.getDevice().getName();
        	String serviceName = BleNamesResolver.resolveServiceName(characteristic.getService().getUuid().toString().toLowerCase(Locale.getDefault()));
        	String charName = BleNamesResolver.resolveCharacteristicName(characteristic.getUuid().toString().toLowerCase(Locale.getDefault()));
        	String description = "Device: " + deviceName + " Service: " + serviceName + " Characteristic: " + charName;

            Log.e("---++---", "H2BT WR STATUS -> " + status);
        	// we got response regarding our request to write new value to the characteristic
        	// let see if it failed or not
        	if(status == BluetoothGatt.GATT_SUCCESS) {
        		 mUiCallback.uiSuccessfulWrite(mBluetoothGatt, mBluetoothDevice, mBluetoothSelectedService, characteristic, description);
        	}
        	else {
        		 mUiCallback.uiFailedWrite(mBluetoothGatt, mBluetoothDevice, mBluetoothSelectedService, characteristic, description + " STATUS = " + status);
        	}
        };

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        	if(status == BluetoothGatt.GATT_SUCCESS) {
        		// we got new value of RSSI of the connection, pass it to the UI
        		 mUiCallback.uiNewRssiAvailable(mBluetoothGatt, mBluetoothDevice, rssi);
        	}
        };
    };

    //public void CalcCommonKey(String MasterBDAddress, String SlaveBDAddress) {
    public void CalcCommonKey() {
        try {
            /*
            int i;
            int[] MasterAdd = new int[6];
            int[] SlaveAdd = new int[6];
            String[] strBuffer = MasterBDAddress.split(":", 6);
            for (i = 0; i < MasterAdd.length; i++) {
                MasterAdd[i] = Integer.parseInt(strBuffer[i], 16);
            }
            strBuffer = SlaveBDAddress.split(":", 6);
            for (i = 0; i < SlaveAdd.length; i++) {
                SlaveAdd[i] = Integer.parseInt(strBuffer[i], 16);
            }
            */

            int i;
            int[] MasterAdd = new int[6];
            int[] SlaveAdd = new int[6];
/*
            byte[] slaveSrc = {'0','0',
                    'E', '0',
                    '5', 'C',
                    '0', '8',
                    '4', '4',
                    'A', 'F'
            };

            byte[] masterSrc = {
                    '7', 'D',
                    '0', '0',
                    '4', '9',

                    '4', '6',
                    'F', '0',
                    'F', '2'
            };

*/


            byte[] slaveSrc = {
                    /*
                    '0', '0',
                    'E', '0',
                    '5', 'C',
                    '0', '8',
                    '4', '4',
                    'A', 'F'
                    */
                /*0x00, 0x00
                        ,14, 0x00
                        ,0x05, 12
                        ,0x00, 0x08
                        ,0x04, 0x04
                        ,10, 15
                        */
                    '0', '0',
                    'E', '0',
                    '5', 'C',

                    '0', '8',
                    'F', 'B',
                    '9', '4'
                    //00 00 0E 00
                    //05 0C 00 08
                    //0F 0B 09 04
            };

            byte[] masterSrc = {
                    /*
                    '7', '9',
                    '0', '6',
                    '6', 'E',

                    'C', '0',
                    'B', '3',
                    '1', '5'
                    */
                    /*
                0x07, 0x09
                        ,0x00, 0x06
                        ,0x06, 14
                        ,12, 0x00
                        ,11, 0x03
                        ,0x01,0x05*/
                    '7', '1',
                    '8', '3',
                    '5', '4',

                    '6', '8',
                    '5', '8',
                    'C', 'C'
            };
            //07 01 08 03
            //05 04 06 08
            //05 08 0C 0C



            String slaveBD = new String(slaveSrc, "UTF-8");

            String masterBD = new String(masterSrc, "UTF-8");

            Log.i("H2BT","H2 DEBUG ..... slaveBD " + slaveBD);
            Log.i("H2BT","H2 DEBUG ..... masterBD " + masterBD);

            String slaveBDX = addColon(slaveBD);
            String masterBDX = addColon(masterBD);

            Log.i("H2BT","H2 DEBUG ..... slaveBD " + slaveBDX);
            Log.i("H2BT","H2 DEBUG ..... masterBD " + masterBDX);

            String[] strBuffer = masterBDX.split(":", 6);
            for (i = 0; i < MasterAdd.length; i++) {
                MasterAdd[i] = Integer.parseInt(strBuffer[i], 16);
            }
            strBuffer = slaveBDX.split(":", 6);
            for (i = 0; i < SlaveAdd.length; i++) {
                SlaveAdd[i] = Integer.parseInt(strBuffer[i], 16);
            }



            //byte[] bd = new byte[12];
            //PairingActivity.this._byteBuff.position(1);
            //PairingActivity.this._byteBuff.get(bd, 0, 12);
            //String slaveBD = new String(bd, "UTF-8");
            //PairingActivity.this._byteBuff.position(13);
            //PairingActivity.this._byteBuff.get(bd, 0, 12);
            //PairingActivity.this.setBluetoothDevice(slaveBD, new String(bd, "UTF-8"));

            int A1 = ((((short) SlaveAdd[0]) + SlaveAdd[1]) + MasterAdd[0]) & 255;
            int A2 = (((short) SlaveAdd[2]) + MasterAdd[4]) & 255;
            int A3 = ((((short) SlaveAdd[3]) + MasterAdd[2]) + MasterAdd[5]) & 255;
            int A4 = (((short) SlaveAdd[4]) + MasterAdd[3]) & 255;
            int A5 = (((short) SlaveAdd[5]) + MasterAdd[1]) & 255;
            this._K1 = ((((((short) A1) + A2) + A3) + A4) + A5) & 255;

            Log.i("H2BT","A1 = " + A1 + " A2 = " + A2 + " A3 = " + A3 + " A4 = " + A4 + " A5 = " + A5);

            this._K2 = (this._K1 ^ 170) & 255;
            this._K3 = (((((A2 << 2) ^ A1) ^ A3) ^ (A4 << 3)) ^ (A5 << 2)) & 255;
            this._K4 = (this._K1 ^ this._K3) & 255;
            Log.i("H2BT","K1 = " + _K1 + " K2 = " + _K2 + " K3 = " + _K3 + " K4 = " + _K4);
            Log.i("H2BT","K1 = " + this._K1 + " K2 = " + this._K2 + " K3 = " + this._K3 + " K4 = " + this._K4);

        } catch (Exception e) {
        }

        //Log.i("K1 = " + _K1 + "K2 = " + _K2 + "K3 = " + _K3 + "K4 = " + _K4);

    }
//* protected byte[] synData() {return encrypt(new byte[]{EncDec.SYN}); */
    public byte Encrypt(int data) {
        CalcCommonKey();

        int tmpa = RotateLeft(data ^ this._K1, 4);
        int tmpb = RotateLeft(tmpa ^ this._K2, 3);
        int tmpc = RotateLeft(tmpb ^ this._K3, 5);
        int tmpd = RotateLeft(tmpc ^ this._K4, 2);

        Log.i("H2BT","TMPA = " + tmpa);
        Log.i("H2BT","TMPB = " + tmpb);
        Log.i("H2BT","TMPC = " + tmpc);
        Log.i("H2BT","TMPD = " + tmpd);

         byte tmpValue = (byte)(tmpd & 255);
        //byte tmpValue = (byte) (RotateLeft(RotateLeft(RotateLeft(RotateLeft(data ^ this._K1, 4) ^ this._K2, 3) ^ this._K3, 5) ^ this._K4, 2) & 255);

        final StringBuilder stringBuilder = new StringBuilder(1);
        //for(byte byteChar : mRawValue)
            //stringBuilder.append(String.format("%02X", byteChar));
        stringBuilder.append(String.format("%02X", tmpValue));
        //mAsciiValue = "0x" + stringBuilder.toString();
        String value = stringBuilder.toString();
        //Log.i("HAHA VALUE = " + value);
        //Log.i("HAHA");
        return  tmpValue;
        //return (byte) (RotateLeft(RotateLeft(RotateLeft(RotateLeft(data ^ this._K1, 4) ^ this._K2, 3) ^ this._K3, 5) ^ this._K4, 2) & 255);
    }

    public byte Decrypt(int data) {
        return (byte) ((RotateRight(RotateRight(RotateRight(RotateRight(data, 2) ^ this._K4, 5) ^ this._K3, 3) ^ this._K2, 4) ^ this._K1) & 255);
    }

    private int RotateLeft(int data, int Keta) {
        int Buffer = ((data + 255) + 1) & 255;
        int tmp = ((Buffer << Keta) & 255) | ((Buffer >>> (8 - Keta)) & 255);
        Log.i("H2BT","H2 DEBUG ..... ARKRAY " + Buffer + " WHAT " + tmp);
        return ((Buffer << Keta) & 255) | ((Buffer >>> (8 - Keta)) & 255);
    }

    private int RotateRight(int data, int Keta) {
        int Buffer = ((data + 255) + 1) & 255;
        return ((Buffer >>> Keta) & 255) | ((Buffer << (8 - Keta)) & 255);
    }

    private String addColon(String base) {
        return String.format("%s:%s:%s:%s:%s:%s", new Object[]{base.substring(0, 2), base.substring(2, 4), base.substring(4, 6), base.substring(6, 8), base.substring(8, 10), base.substring(10, 12)});
    }

    public void setRocheRequestTimeout() {
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                // CONNECTION_PRIORITY_HIGH
                result = mBluetoothGatt.requestConnectionPriority(0);
                if (result) {
                    Log.e("---++---", "H2BT ROCHE OTHER INIT");
                    rocheFlowInitTimeOut();
                }
            }
        };
        mHandler.postDelayed(timeout, 350);
    }

    public void rocheFlowInitTimeOut() {
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                IData.packetInfoNeed();
                IData.setCurrentTimeState(false);
                byte[] guideOtherInit = {
                        0x01, 0x01, (byte) 0x83, 0x01,  0x00, 0x01, 0x00, 0x06,
                        0x00, 0x03, 0x00, 0x02,  0x00, 0x03, 0x6C, 0x41
                };
                IData.packetIndexInit();
                Log.e("---++---", "H2BT ROCHE OTHER INIT " + mRoCharacteristicOthers);
                writeDataToCharacteristic(mRoCharacteristicOthers, guideOtherInit);
                //rocheFlowWriteTimeout();
            }
        };
        mHandler.postDelayed(timeout, 550);
    }


    public void rocheFlowWriteTimeout() {
        Log.e("---++---", "H2BT WRITE CYCLE ...." + " ");
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                System.out.println("write cycle ... " + mRoCharacteristicOthers);

                Log.e("H2BT","write cycle ... HAHA" + " "+ h2RocheCmdFlow);

                /*

                e7 00 00 1a  00 18 00 03
                01 07 00 12  00 00 0c 17
                00 0c 20 19  01 08 13 43
                09 00 00 00  00 00
                 */

                switch (h2RocheCmdFlow){
                    case 0:
                        break;

                    case 1:case 2:case 3:case 4:
                    case 5:case 6:case 7:case 8:
                    case 9:case 10:case 11:case 12:
                    case 13:case 14:case 15:
                        byte[] wTmp = IData.getAuthCode(h2RocheCmdFlow-1);
                        if (wTmp != null) {
                            //for (int j=0; j<20; j++) {
                            //    Log.e("H2BT","R-WC" + " = " + j + " - " + wTmp[j]);
                            //}
                            Log.e("H2BT","R-WC" + " = " + wTmp[0]);
                            Log.e("H2BT","R-WC" + " = " + wTmp[1]);
                            Log.e("H2BT","R-WC" + " = " + wTmp[2]);
                            Log.e("H2BT","R-WC" + " = " + wTmp[3]);
                            Log.e("H2BT","R-WC" + " = " + wTmp[4]);
                            IData.packetIndexInit();
                            writeDataToCharacteristic(mRoCharacteristicOthers, wTmp);
                            rocheFlowWriteTimeout();
                        }
                        break;
/*
                    case 15:
                        byte[] wxfTmp = IData.getAuthCode(h2RocheCmdFlow-1);
                        if (wxfTmp != null) {
                            //for (int j=0; j<18; j++) {
                            //    Log.e("H2BT","R-WC" + " = " + j + " - " + wxfTmp[j]);
                            //}
                            IData.packetIndexInit();
                            writeDataToCharacteristic(mRoCharacteristicOthers, wxfTmp);
                            rocheFlowWriteTimeout();
                        }
                        break;
*/
                    case 16:
                        //writeDataToCharacteristic(mRoCharacteristicOthers, rocheReadCurrentTime);
                        //rocheFlowWriteTimeout();
                        //rocheWriteCurrentTimeTask();
                        //rocheCurrentTimeFlow=0;
                        IData.packetIndexInit();
                        rocheGetCurrentTimeFlow = 0;
                        rocheGetCurrentTimeTask();
                        break;

                    //case 17:
                        //writeDataToCharacteristic(mRoCharacteristicOthers, rocheReadCTAfter);
                        //rocheWriteCurrentTimeTask();
                        //rocheCurrentTimeFlow=0;
                        //break;


                    default:
                        break;
                }
                h2RocheCmdFlow++;
            }
        };
        mHandler.postDelayed(timeout, 300);
    }

    public void rocheGetCurrentTimeTask() {
        Log.e("---++---", "H2BT GET CURRENT TIME CYCLE ...." + " ");


        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                byte[] rocheReadCurrentTime = {

                        // For Instant
                        0x01, 0x01, (byte) 0x88, (byte) 0x8F , 0x00, 0x01, 0x00, 0x06
                        , 0x00, 0x01, 0x00, 0x02 , 0x00, 0x00, 0x1E, (byte) 0x96

                        // 0x01, 0x01, (byte) 0x88, (byte) 0x91 , 0x00, 0x01, 0x00, 0x06
                        //, 0x00, 0x01, 0x00, 0x02 , 0x00, 0x00, 0x26, 0x0E

                        // for Guide
                        //0x01, 0x01, 0x1d, 0x00 , 0x00, 0x01, 0x00, 0x06
                        //, 0x00, 0x01, 0x00, 0x02 , 0x00, 0x00, (byte) 0xAB, (byte) 0xFA
                };

                byte[] rocheReadCTAfter = {
                        0x01, 0x01, 0x53, 0x33 , 0x00, 0x01, 0x00, 0x06
                        , 0x00, 0x01, 0x00, 0x02 , 0x00, 0x00, (byte) 0x9C, (byte) 0xC7
                };


                Log.e("H2BT", "write GET CT cycle ... HAHA" + " " + rocheGetCurrentTimeFlow);
                switch (rocheGetCurrentTimeFlow) {
                    case 0:
                    //case 1:
                        IData.packetIndexInit();
                        rocheGetCurrentTimeTask();
                        break;
                    case 1:
                        IData.packetIndexInit();
                        writeDataToCharacteristic(mRoCharacteristicOthers, rocheReadCurrentTime);
                        //rocheGetCurrentTimeTask();

                        // write Current Time
                        rocheWriteCurrentTimeTask();
                        rocheCurrentTimeFlow=0;
                        break;

                    case 2:
                    case 3:
                        IData.packetIndexInit();
                        rocheGetCurrentTimeTask();


                        break;

                    case 4:
                        IData.packetIndexInit();
                        //writeDataToCharacteristic(mRoCharacteristicOthers, rocheReadCTAfter);
                        rocheWriteCurrentTimeTask();
                        rocheCurrentTimeFlow=0;
                        break;


                    default:
                        break;

                }
                rocheGetCurrentTimeFlow++;
            }
        };
        mHandler.postDelayed(timeout, 350);
    }


    public void rocheWriteCurrentTimeTask() {
        Log.e("---++---", "H2BT CURRENT TIME CYCLE ...." + " ");


        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                byte[] rocheCurrentTimeA = {
                        0x01, 0x03

                        , (byte) 0x88, (byte) 0x92 , 0x00, 0x04, 0x00, 0x20
                        //, 0x09, (byte) 0x90, 0x00, 0x08 , 0x20, 0x19, 0x02, 0x18

                        , 0x09, (byte) 0x90, 0x00, 0x08 , 0x20, 0x17, 0x03, 0x23
                        , 0x17, 0x09, 0x49, (byte) 0x97
                        /*
                        , (byte) 0x88, (byte) 0x92 , 0x00, 0x04, 0x00, 0x20
                        , 0x09, (byte) 0x90, 0x00, 0x08

                        , 0x20, 0x17, 0x03, 0x18
                        , 0x17, 0x09
                        , 0x4A, 0x45
                        */
                };
                byte[] rocheCurrentTimeB = {
                        0x02, 0x03
                        , 0x00, 0x1c , 0x00, 0x04, 0x00, 0x00
                        , 0x00, 0x00, 0x00, 0x10 , 0x00, 0x02, 0x00, 0x00
                        , 0x00, 0x03, 0x00, 0x02

                };
                byte[] rocheCurrentTimeC = {
                        0x03, 0x03
                        , 0x00, 0x05
                        //, (byte) 0xAA, (byte) 0x88
                        , (byte) 0xE7, (byte) 0x9E

                        //, (byte) 0x2F, (byte) 0xB0
                };

                byte[] instantCurrentTimeA = {
                        0x01, 0x03

                        , (byte) 0x88, (byte) 0x92 , 0x00, 0x04, 0x00, 0x20
                        , 0x09, (byte) 0x90, 0x00, 0x08
                        //, 0x20, 0x19, 0x02, 0x18, 0x17, 0x09, 0x49, (byte) 0x97

                        //, 0x20, 0x19, 0x01, 0x24 , 0x17, 0x48, 0x43, (byte) 0x88
                        //, 0x20, 0x19, 0x01, 0x24 , 0x17, 0x48, 0x43, (byte) 0x77


                        , 0x20, 0x30, 0x03, 0x04
                        , 0x17, 0x48//, 0x43, 0x88
                        , 0x11, 0x22

                        /*
                        , (byte) 0x88, (byte) 0x92 , 0x00, 0x04, 0x00, 0x20
                        , 0x09, (byte) 0x90, 0x00, 0x08 , 0x20, 0x17, 0x03, 0x18
                        , 0x17, 0x09
                        , 0x4A, 0x45
                        */
                };
                byte[] instantCurrentTimeB = {
                        0x02, 0x03
                        , 0x00, 0x1c , 0x00, 0x04, 0x00, 0x00
                        , 0x00, 0x00, 0x00, 0x10 , 0x00, 0x02, 0x00, 0x00
                        , 0x00, 0x03, 0x00, 0x02
                };
                byte[] instantCurrentTimeC = {
                        0x03, 0x03
                        , 0x00, 0x05
                        //, (byte) 0xAA, (byte) 0x88
                        , (byte) 0xA6, (byte) 0x38
                        //, (byte) 0xF8, (byte) 0xA5
                };

                byte[] guideCurrentTimeA = {
                        0x01, 0x02,
                        //(byte) 0x88, (byte) 0x92 , 0x00, 0x04, 0x00, 0x10
                        //, 0x0c, 0x17, 0x00, 0x0c, 0x20, 0x15 , 0x03, 0x08
                        //, 0x12, 0x13, 0x12, 0x00

                        (byte) 0x88, (byte) 0x92 , 0x00, 0x04, 0x00, 0x0C

                        , 0x0c, 0x17

                        , 0x00, 0x08, 0x20, 0x15 , 0x03, 0x08, 0x12, 0x13
                        , 0x12, 0x00

                };
                byte[] guideCurrentTimeB = {
                          0x02, 0x02
                        //, 0x00, 0x00 , 0x00, 0x00
                        //, 0x22, 0x08

                        , (byte) 0xE8, 0x43
                };

                byte[] connAck = {
                        0x01, 0x01,
                        0x53, (byte) 0x90, 0x00, 0x01, 0x00, 0x06
                        , 0x00, 0x01, 0x00, 0x02, 0x00, 0x00, 0x12, (byte) 0xE5
                };


                byte[] connCT_A = {
                        0x01, 0x03,
                        (byte) 0x88, (byte) 0x92, 0x00, 0x04, 0x00, 0x20
                        , 0x09, (byte) 0x90, 0x00, 0x08, 0x20, 0x17, 0x03, 0x18
                        , 0x17, 0x09
                        , 0x4A, 0x45
                };
                byte[] connCT_B = {
                        0x02, 0x03
                        , 0x00, 0x1c, 0x00, 0x04, 0x00, 0x00
                        , 0x00, 0x00, 0x00, 0x10, 0x00, 0x02, 0x00, 0x00
                        , 0x00, 0x03, 0x00, 0x02
                };
                byte[] connCT_C = {
                        0x03, 0x03
                        , 0x00, 0x05
                        , (byte) 0x2F, (byte) 0xB0
                };



                Log.e("H2BT", "write CT cycle ... CTCT" + " " + rocheCurrentTimeFlow);
                switch (rocheCurrentTimeFlow) {


                    case 0:
                        IData.setCurrentTimeState(true);
                        IData.packetIndexInit();
                        //writeDataToCharacteristic(mRoCharacteristicOthers, connAck);
                        rocheWriteCurrentTimeTask();
                        break;

                    case 1:
                        IData.packetIndexInit();
                        writeDataToCharacteristic(mRoCharacteristicOthers, instantCurrentTimeA);
                        //writeDataToCharacteristic(mRoCharacteristicOthers, rocheCurrentTimeA);
                        //writeDataToCharacteristic(mRoCharacteristicOthers, guideCurrentTimeA);
                        //writeDataToCharacteristic(mRoCharacteristicOthers, connCT_A);

                        rocheWriteCurrentTimeTask();
                        break;

                    case 2:
                        writeDataToCharacteristic(mRoCharacteristicOthers, instantCurrentTimeB);
                        //writeDataToCharacteristic(mRoCharacteristicOthers, rocheCurrentTimeB);
                        //writeDataToCharacteristic(mRoCharacteristicOthers, guideCurrentTimeB);
                        //writeDataToCharacteristic(mRoCharacteristicOthers, connCT_B);
                        rocheWriteCurrentTimeTask();
                        break;

                    case 3:
                        writeDataToCharacteristic(mRoCharacteristicOthers, instantCurrentTimeC);
                        //writeDataToCharacteristic(mRoCharacteristicOthers, rocheCurrentTimeC);
                        //writeDataToCharacteristic(mRoCharacteristicOthers, connCT_C);
                        break;

                        default:
                            break;

                }
                rocheCurrentTimeFlow++;
            }
        };
        mHandler.postDelayed(timeout, 350);
    }



    // BGM
    public void bgmCharCtrlTimeout() {
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                UUID uuid = null;
                List<BluetoothGattCharacteristic> chars = null;
                chars = mRocheServiceBgm.getCharacteristics();
                for(BluetoothGattCharacteristic ch : chars) {
                    //UUID
                    uuid = ch.getUuid();

                    if (uuid.equals(BleDefinedUUIDs.Characteristic.BG_CONTROL_UUID)) {
                        mRoCharacteristicControl = ch;
                        Log.e("---++---", "H2BT BGM CTRL");
                        // Set Notify
                        boolean success = mBluetoothGatt.setCharacteristicNotification(ch, true);
                        if(success) {
                            Log.e("---++---", "H2BT BGM CTRL - Notify Successful");
                            BluetoothGattDescriptor descriptor = ch.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                            if(descriptor != null) {
                                byte[] val = true ? BluetoothGattDescriptor.ENABLE_INDICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
                                descriptor.setValue(val);
                                Log.e("---++---", "H2BT BGM CTRL DESC - " + descriptor);
                                Log.e("---++---", "H2BT BGM CTRL DESC - " + val[0]);
                                boolean result = mBluetoothGatt.writeDescriptor(descriptor);
                                if (result) {
                                    bgmMeasurementCharLoopTimeout();
                                    Log.e("---++---", "H2BT BGM CTRL - WR DESC Successful");
                                }
                            }
                            //Log.e("---++---", "H2BT NOTIFICATION OK ->" + " " + uuid);
                        }else {
                            Log.e("------", "Seting proper notification status for characteristic failed!");
                        }
                    }
                    //else if (uuid.equals(BleDefinedUUIDs.Characteristic.CURRENT_TIME_UUID)) {
                    //    mRoCharacteristicCurrentTime = ch;
                        //mBluetoothGatt.readCharacteristic(ch);
                    //    Log.e("---++---", "H2BT ROCHE BGM CURRENT TIME");

                    //}
                    else {

                    }
                }
                devInfoCharLoopTimeout();
                Log.e("---++---", "H2BT BGM CHAR LOOP ...." + " GOOD!! ");
            }
        };
        mHandler.postDelayed(timeout, 200);
    }

    // BGM ELSE
    public void bgmMeasurementCharLoopTimeout() {
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                UUID uuid = null;
                List<BluetoothGattCharacteristic> chars = null;
                chars = mRocheServiceBgm.getCharacteristics();
                for (BluetoothGattCharacteristic ch : chars) {
                    //UUID
                    uuid = ch.getUuid();
                    if (uuid.equals(BleDefinedUUIDs.Characteristic.BG_MEASUREMENT_UUID)) {
                        mRoCharacteristicMeasurement = ch;
                        Log.e("---++---", "H2BT BGM MEASUREMENT");
                        // Set Notify

                        boolean success = mBluetoothGatt.setCharacteristicNotification(ch, true);
                        if(success) {

                            BluetoothGattDescriptor descriptor = ch.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                            if(descriptor != null) {
                                byte[] val = true ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
                                descriptor.setValue(val);
                                boolean result = mBluetoothGatt.writeDescriptor(descriptor);
                                if (result) {
                                    //bgmContextCharLoopTimeout();
                                    rocheWriteCtrlTimeout();
                                    Log.e("---++---", "H2BT NOTIFICATION OK (MEASUREMENT) ->" + " " + uuid);
                                }
                            }

                            // bgmContextCharLoopTimeout();
                            //rocheWriteCtrlTimeout(); // Bayer
                        }else {
                            Log.e("------", "Seting proper notification status for characteristic failed!");
                        }
                    }
                    else {

                    }
                }
            }
        };
        mHandler.postDelayed(timeout, 200);
    }

    // BGM ELSE
    public void bgmContextCharLoopTimeout() {
        Log.e("---++---", "BGM CONTEXT CHAR LOOP ....");
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                UUID uuid = null;
                List<BluetoothGattCharacteristic> chars = null;
                chars = mRocheServiceBgm.getCharacteristics();
                for (BluetoothGattCharacteristic ch : chars) {
                    //UUID
                    uuid = ch.getUuid();
                    if (uuid.equals(BleDefinedUUIDs.Characteristic.BG_CONTEXT_UUID)) {
                        mRoCharacteristicContext = ch;
                        Log.e("---++---", "H2BT BGM CONTEXT");
                        // Set Notify
                        boolean success = mBluetoothGatt.setCharacteristicNotification(ch, true);
                        if(success) {
                            /*
                            BluetoothGattDescriptor descriptor = ch.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                            if(descriptor != null) {
                                byte[] val = true ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
                                descriptor.setValue(val);
                                boolean result = mBluetoothGatt.writeDescriptor(descriptor);
                                if (result) {

                                    rocheWriteCtrlTimeout(); // Bayer
                                    //rocheOtherCharLoopTimeout(); // Roche
                                    Log.e("---++---", "H2BT NOTIFICATION OK (CONTEXT) ->" + " " + uuid);
                                }
                            }
                            */
                            rocheWriteCtrlTimeout(); // Bayer
                        }else {
                            Log.e("------", "Seting proper notification status for characteristic failed!");
                        }
                    }
                    //else if (uuid.equals(BleDefinedUUIDs.Characteristic.CURRENT_TIME_UUID)) {
                    //    mRoCharacteristicCurrentTime = ch;
                        //mBluetoothGatt.readCharacteristic(ch);
                    //    Log.e("---++---", "H2BT ROCHE BGM CURRENT TIME");

                    //}
                    else {

                    }
                }
            }
        };
        mHandler.postDelayed(timeout, 200);
    }

    // ROCHE OTHER
    public void rocheOtherCharLoopTimeout() {
        Log.e("---++---", "ROCHE OTHERS CHAR LOOP ...." + " ");
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                bgmCharListing();

                List<BluetoothGattCharacteristic> chars = null;
                chars = mRocheServiceOther.getCharacteristics();
                for(BluetoothGattCharacteristic ch : chars) {
                    UUID uuid = ch.getUuid();
                    if (uuid.equals(BleDefinedUUIDs.Characteristic.ROCHE_OTHER_UUID)){
                        mRoCharacteristicOthers = ch;
                        h2RocheCmdFlow = 1;
                        // Set Notify
                        boolean success = mBluetoothGatt.setCharacteristicNotification(ch, true);
                        if(success) {
                            //return;

                            BluetoothGattDescriptor descriptor = ch.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                            if(descriptor != null) {
                                byte[] val = true ? BluetoothGattDescriptor.ENABLE_INDICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
                                //byte[] val = true ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
                                descriptor.setValue(val);
                                boolean result = mBluetoothGatt.writeDescriptor(descriptor);
                                if (result) {
                                    //rocheFlowInitTimeOut();
                                    rocheCharReadCTTimeout();
                                    Log.e("---++---", "H2BT ROCHE OTHER NOTIFICATION" + " " + uuid);
                                }
                            }

                        }else {
                            Log.e("------", "Seting proper notification status for characteristic failed!");
                        }
                    }
                }
                Log.e("---++---", "H2BT ROCHE CHAR LOOP ...." + " GOOD!! ");
            }
        };
        mHandler.postDelayed(timeout, 200);
    }

    // ROCHE READ CURRENT TIME
    public void rocheCharReadCTTimeout() {
        //Log.e("---++---", "ROCHE READ CT ...." + " ");
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                mBluetoothGatt.readCharacteristic(mRoCharacteristicCurrentTime);
                rocheCharReadSNTimeout();
                Log.e("---++---", "H2BT ROCHE CT CHAR  .... READING!!");

            }
        };
        mHandler.postDelayed(timeout, 300);
    }

    // ROCHE READ CURRENT TIME
    public void rocheCharReadSNTimeout() {
        //Log.e("---++---", "ROCHE READ SN ...." + " ");
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                mBluetoothGatt.readCharacteristic(mRoCharacteristicSerialNumber);
                rocheFlowInitTimeOut();
                //rocheFlowWriteTimeout();
                Log.e("---++---", "H2BT ROCHE SN CHAR  .... READING!!");

            }
        };
        mHandler.postDelayed(timeout, 300);
    }

    // DEVICE
    public void devInfoCharLoopTimeout() {
        Log.e("---++---", "BGM CHAR LOOP ...." + " ");
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                UUID uuid = null;
                List<BluetoothGattCharacteristic> chars = null;
                chars = mRocheServiceDevInfo.getCharacteristics();
                for (BluetoothGattCharacteristic ch : chars) {
                    //UUID
                    uuid = ch.getUuid();

                    if (uuid.equals(BleDefinedUUIDs.Characteristic.BG_SN_UUID)) {
                        mRoCharacteristicSerialNumber = ch;
                        Log.e("---++---", "H2BT SN");
                        break;
                    }
                }
            }
        };
    }

    // H2 SERVICE TIME OUT
    public void h2ServiceTimeout() {

        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                UUID uuid = null;
                List<BluetoothGattCharacteristic> chars = null;
                chars = mH2BTServiceCable.getCharacteristics();
                for (BluetoothGattCharacteristic ch : chars) {
                    //UUID
                    uuid = ch.getUuid();
                    Log.e("---++---", "CABLE CHAR ...." + uuid);
                    if (uuid.equals(BleDefinedUUIDs.Characteristic.H2BT_CABLE_CHARACTERISTIC)) {
                        mH2BTCharacteristicCable = ch;
                        // Set Notify
                        boolean success = mBluetoothGatt.setCharacteristicNotification(ch, true);
                        if(success) {
                            Log.e("---++---", "CABLE SET NOTIFY " + uuid);
                        }else {
                            Log.e("------", "Seting proper notification status for characteristic failed!");
                        }
                        h2CableWriteTimeout();
                        Log.e("---++---", "H2BT CABLE CHAR ");
                        break;
                    }
                }
            }
        };
        mHandler.postDelayed(timeout, 200);
    }

    public void h2CableWriteTimeout() {

        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                byte[] dataToWriteExisting = { 0x00, 0x00, 0x0b, 0x00, 0x00, 0x00, 0x00, (byte) 0x95,
                        0x00, 0x00, 0x00, (byte) 0x9e
                };
                writeDataToCharacteristic(mH2BTCharacteristicCable, dataToWriteExisting);
            }
        };
        mHandler.postDelayed(timeout, 200);
    }


    // BGM CHAR LISTING
    public void bgmCharListing() {
        UUID uuid = null;
        rocheLoop = 3;
        List<BluetoothGattCharacteristic> chars = null;
        chars = mRocheServiceBgm.getCharacteristics();
        for (BluetoothGattCharacteristic ch : chars) {
            //UUID
            uuid = ch.getUuid();
            if (uuid.equals(BleDefinedUUIDs.Characteristic.BG_CONTROL_UUID)) {
                mRoCharacteristicControl = ch;
            }
            else if (uuid.equals(BleDefinedUUIDs.Characteristic.BG_MEASUREMENT_UUID)) {
                mRoCharacteristicMeasurement = ch;
                Log.e("---++---", "H2BT BGM MEASUREMENT");
            }
            else if (uuid.equals(BleDefinedUUIDs.Characteristic.BG_CONTEXT_UUID)) {
                mRoCharacteristicContext = ch;
            }
            else if (uuid.equals(BleDefinedUUIDs.Characteristic.CURRENT_TIME_UUID)) {
                mRoCharacteristicCurrentTime = ch;
            }

            else {

            }
        }

        List<BluetoothGattCharacteristic> charsInDev = null;
        charsInDev = mRocheServiceDevInfo.getCharacteristics();
        for (BluetoothGattCharacteristic ch : charsInDev) {
            uuid = ch.getUuid();

            if (uuid.equals(BleDefinedUUIDs.Characteristic.BG_SN_UUID)) {
                mRoCharacteristicSerialNumber = ch;
                Log.e("---++---", "H2BT SN");
                break;
            }
        }
    }



    /* get characteristic's value (and parse it for some types of characteristics)
     * before calling this You should always update the value by calling requestCharacteristicValue() */
    public void getRocheCharacteristicValue(BluetoothGattCharacteristic ch) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null || ch == null) return;

        byte[] rawValue = ch.getValue();
        String strValue = null;
        int dLen = ch.getValue().length;
        int intValue = 0;

        // lets read and do real parsing of some characteristic to get meaningful value from it
        UUID uuid = ch.getUuid();

        Log.e("H2BT","BACK ROCHE --- ... HAHA " + uuid);


        if (uuid.equals(BleDefinedUUIDs.Characteristic.CURRENT_TIME_UUID)) {
            Log.e("H2BT","ROCHE CURRENT TIME ... CT !!");
            Log.e("H2BT","CT = " + rawValue[0]);
            Log.e("H2BT","CT = " + rawValue[1]);
            Log.e("H2BT","CT = " + rawValue[2]);
            Log.e("H2BT","CT = " + rawValue[3]);
/*
            byte year_hi = rawValue[0];
            byte year_lo = rawValue[1];

            int aHiYear = ((year_hi & 0xF0)>>4) * 10 + (year_hi & 0x0F);
            int aLoYear = ((year_lo & 0xF0)>>4) * 10 + (year_lo & 0x0F);
            aHiYear = aHiYear * 100 + aLoYear;
            Log.e("H2BT", "CT_ YEAR = ?? " + aHiYear);

            byte month = rawValue[2];
            int aMonth = ((month & 0xF0)>>4) * 10 + (month & 0x0F);
            Log.e("H2BT", "CT_ MONTH = ?? " + aMonth);

            byte day = rawValue[3];
            int aDay = ((day & 0xF0)>>4) * 10 + (day & 0x0F);
            Log.e("H2BT", "CT_ DAY = ?? " + aDay);

            byte hour = rawValue[4];
            int aHour = ((hour & 0xF0)>>4) * 10 + (hour & 0x0F);
            Log.e("H2BT", "CT_ HOUR = ?? " + aHour);

            byte minute = rawValue[5];
            int aMinute = ((minute & 0xF0)>>4) * 10 + (minute & 0x0F);
            Log.e("H2BT", "CT_ MINUTE = ?? " + aMinute);
*/
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.LOCAL_TIME_UUID)) {
            Log.e("H2BT","BACK-TIME ... HAHA");
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.BG_CONTROL_UUID)) {
            Log.e("H2BT","BACK-CTRL ... RACP!!");

        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.BG_MEASUREMENT_UUID)) {
            Log.e("H2BT","BACK-MEASUREMENT ... MEASUREMENT!!");
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.BG_CONTEXT_UUID)) {
            Log.e("H2BT","BACK-CONTEXT ... CONTEXT!!");
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.BG_SN_UUID)) {
            Log.e("H2BT","BACK-SN ... SN!!");
            //rocheWriteCtrlTimeout();
        }
        else if(uuid.equals(BleDefinedUUIDs.Characteristic.ROCHE_OTHER_UUID)) {
            Log.e("H2BT","BACK-OTHERS ... OTHERS!!");
            for (int i=0; i<dLen; i++) {
                Log.e("H2BT","BACK - OH " + i + " " + rawValue[i]);
            }

            if (IData.rocheVendorDataInput(rawValue)) {
                IData.rocheCreateAuthCode(ScanningActivity.scanContext);
                h2RocheCmdFlow = 1;
                rocheFlowWriteTimeout();
            }

            //rocheWriteCtrlTimeout();
        }

        else {
            // not known type of characteristic, so we need to handle this in "general" way
            // get first four bytes and transform it to integer
            Log.e("H2BT","BACK-ERROR ... FAIL");
        }

        Log.e("---++---", "H2BT VALUE ...." + " " );
/*
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS").format(new Date());
        mUiCallback.uiNewValueForCharacteristic(mBluetoothGatt,
                mBluetoothDevice,
                mBluetoothSelectedService,
                ch,
                strValue,
                intValue,
                rawValue,
                timestamp);
                */
    }


	private Activity mParent = null;
	private boolean mConnected = false;
	private String mDeviceAddress = "";

    private BluetoothManager mBluetoothManager = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothDevice  mBluetoothDevice = null;
    private BluetoothGatt    mBluetoothGatt = null;
    private BluetoothGattService mBluetoothSelectedService = null;
    private List<BluetoothGattService> mBluetoothGattServices = null;

    private Handler mTimerHandler = new Handler();
    private boolean mTimerEnabled = false;
}


