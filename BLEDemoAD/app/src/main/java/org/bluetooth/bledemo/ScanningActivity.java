package org.bluetooth.bledemo;

import org.bluetooth.bledemo.R;
import org.bluetooth.idata.IData;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.Calendar;
import java.util.Date;

public class ScanningActivity extends ListActivity {
	
	private static final long SCANNING_TIMEOUT = 8 * 1000; /* 8 seconds */
	private static final int ENABLE_BT_REQUEST_ID = 1;
	
	private boolean mScanning = false;
	private Handler mHandler = new Handler();
	private DeviceListAdapter mDevicesListAdapter = null;
	private BleWrapper mBleWrapper = null;

    /* */
    private  Encrypter mEncrypter = null;
    public static Context scanContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // create BleWrapper with empty callback object except uiDeficeFound function (we need only that here) 
        mBleWrapper = new BleWrapper(this, new BleWrapperUiCallbacks.Null() {
        	@Override
        	public void uiDeviceFound(final BluetoothDevice device, final int rssi, final byte[] record) {
        		handleFoundDevice(device, rssi, record);
        	}
        });
        
        // check if we have BT and BLE on board
        if(mBleWrapper.checkBleHardwareAvailable() == false) {
        	bleMissing();
        }
    }

    @Override
    protected void onResume() {
    	super.onResume();

    	byte [] srcDataA = {
                0x01, 0x03,
                (byte) 0xf0, 0x03 , 0x00, 0x01, 0x00, 0x24
                , 0x00, 0x11, 0x00, 0x20
                //, 0x25, (byte) 0xFF, 0x4F, 0x39
                //, 0x74, (byte) 0x9F, (byte) 0x89, (byte) 0xAA

                , (byte) 0xea, 0x0f, (byte) 0xb3, (byte) 0xc8
                , 0x73, (byte) 0xb9, (byte) 0x87, 0x62
        };

        byte [] srcDataB = {
                // ======= 2 =======
                //0x02, 0x03
                //, (byte) 0x82, (byte) 0xFF, (byte) 0xB7, 0x2D, 0x11, 0x30
                //, 0x45, 0x56, 0x6E, (byte) 0xDD, (byte) 0xB1, 0x56, 0x7E, 0x3F
                //, 0x12, (byte) 0xBA, 0x64, (byte) 0xF8

                  0x02, 0x03, 0x48, (byte) 0xf3 , 0x10, 0x0f, 0x50, (byte) 0xee
                , 0x4f, (byte) 0xf5, 0x51, 0x0d , (byte) 0xd2, 0x20, (byte) 0xdc, 0x00
                , (byte) 0xf6, 0x50, (byte) 0xcb, 0x65
        };

        byte [] srcDataC = {
            // ======= 3 =======
                // 0x03, 0x03
                //, (byte) 0xAB, (byte) 0x83, 0x7D, (byte) 0xA7, 0x68, (byte) 0xC3
                //, (byte) 0x97, 0x79

                  0x03, 0x03, (byte) 0x8f, (byte) 0x90 , 0x74, (byte) 0xb1, (byte) 0x9c, 0x09
                , (byte) 0xF6, 0x44
        };


    	
    	// on every Resume check if BT is enabled (user could turn it off while app was in background etc.)
    	if(mBleWrapper.isBtEnabled() == false) {
			// BT is not turned on - ask user to make it enabled
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, ENABLE_BT_REQUEST_ID);
		    // see onActivityResult to check what is the status of our request
		}
    	
    	// initialize BleWrapper object
        mBleWrapper.initialize();
    	
    	mDevicesListAdapter = new DeviceListAdapter(this);
        setListAdapter(mDevicesListAdapter);
    	
        // Automatically start scanning for devices
    	mScanning = true;
		// remember to add timeout for scanning to not run it forever and drain the battery
		addScanningTimeout();    	
		mBleWrapper.startScanning();
		
        invalidateOptionsMenu();

        /* ==== Roche Task ==== */
        //Context context = new Context(this);
        Context context = null;

        context = this.getBaseContext();
        C0641.rocheEncodeTest(context);

        //context = this;
        //context = this.getApplicationContext();

        scanContext = this.getApplicationContext();
        //C0641.rocheEncodeTest(scanContext);



        //mEncrypter.AndroidGenerateEncrypter();

        // File Create ...
        //AndroidFileFunctions.createFile(context);

        //Encrypter cryp = mEncrypter.GenerateEncrypter(context);


        //AssetManager assManager = context.getAssets();

        //IData.rocheEncodeTest(scanContext);


        //AndroidFileFunctions.Testm573(srcDataA, context);
        //AndroidFileFunctions.Testm573(srcDataB, context);
        //AndroidFileFunctions.Testm573(srcDataC, context);
/*
        IData.rocheVendorDataInput(srcDataA);
        IData.rocheVendorDataInput(srcDataB);
        IData.rocheVendorDataInput(srcDataC);
        IData.rocheCreateAuthCode(context);
*/
        byte[] instantCurrentTimeA = {
                //0x01, 0x03,
                (byte) 0x88, (byte) 0x92 , 0x00, 0x04, 0x00, 0x20
                , 0x09, (byte) 0x90, 0x00, 0x08 , 0x20, 0x17, 0x03, 0x18
                , 0x17, 0x09

                //, 0x49, (byte) 0x97
                , 0x4A, 0x45

                //0x02, 0x03
                , 0x00, 0x1c , 0x00, 0x04, 0x00, 0x00
                , 0x00, 0x00, 0x00, 0x10 , 0x00, 0x02, 0x00, 0x00
                , 0x00, 0x03, 0x00, 0x02

                //0x03, 0x03
                , 0x00, 0x05
                //, (byte) 0xAA, (byte) 0x88
        };

        int xcrc = C0636.rocheCrcCalculate(instantCurrentTimeA, instantCurrentTimeA.length);

        Log.e("H2BT","H2 SCAN ACTIVITY ..... " + xcrc);
        Log.e("H2BT","H2 SCAN ACTIVITY ..... " + ((xcrc & 0xFF00)>>8));
        Log.e("H2BT","H2 SCAN ACTIVITY ..... " + (xcrc & 0x00FF));






        //InputStream is = null;
        /*
        try {
            KeyStore instance = KeyStore.getInstance("pkcs12");
            //KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
            //is = assManager.open("production.dat");;
            //InputStream
            open = context.getResources().getAssets().open("production.dat");
            Log.e("H2BT","H2 ASSETS ..... DEFAULT TYPE " + open);
        } catch (Exception e) {
            Log.e("H2BT","H2 ASSETS ..... " + e.getMessage() +" " + open);
            e.getMessage();
        }
        */
/*
        try {
            byte [] encode = SecurityUtils.encryptPBE("yes", "No");
         } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */


        Log.e("H2BT","H2 SCAN ACTIVITY ..... END");
    };
    
    @Override
    protected void onPause() {
    	super.onPause();
    	mScanning = false;    	
    	mBleWrapper.stopScanning();
    	invalidateOptionsMenu();
    	
    	mDevicesListAdapter.clearList();
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scanning, menu);

        if (mScanning) {
            menu.findItem(R.id.scanning_start).setVisible(false);
            menu.findItem(R.id.scanning_stop).setVisible(true);
            menu.findItem(R.id.scanning_indicator)
                .setActionView(R.layout.progress_indicator);

        } else {
            menu.findItem(R.id.scanning_start).setVisible(true);
            menu.findItem(R.id.scanning_stop).setVisible(false);
            menu.findItem(R.id.scanning_indicator).setActionView(null);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scanning_start:
            	mScanning = true;
            	mBleWrapper.startScanning();
                break;
            case R.id.scanning_stop:
            	mScanning = false;
            	mBleWrapper.stopScanning();
                break;
        }
        
        invalidateOptionsMenu();
        return true;
    }


    /* user has selected one of the device */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final BluetoothDevice device = mDevicesListAdapter.getDevice(position);
        if (device == null) return;
        
        final Intent intent = new Intent(this, PeripheralActivity.class);
        intent.putExtra(PeripheralActivity.EXTRAS_DEVICE_NAME, device.getName());
        intent.putExtra(PeripheralActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
        intent.putExtra(PeripheralActivity.EXTRAS_DEVICE_RSSI, mDevicesListAdapter.getRssi(position));
        
        if (mScanning) {
            mScanning = false;
            invalidateOptionsMenu();
            mBleWrapper.stopScanning();
        }

        startActivity(intent);
    }    
    
    /* check if user agreed to enable BT */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // user didn't want to turn on BT
        if (requestCode == ENABLE_BT_REQUEST_ID) {
        	if(resultCode == Activity.RESULT_CANCELED) {
		    	btDisabled();
		        return;
		    }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

	/* make sure that potential scanning will take no longer
	 * than <SCANNING_TIMEOUT> seconds from now on */
	private void addScanningTimeout() {
		Runnable timeout = new Runnable() {
            @Override
            public void run() {
            	if(mBleWrapper == null) return;
                mScanning = false;
                mBleWrapper.stopScanning();
                invalidateOptionsMenu();
            }
        };
        mHandler.postDelayed(timeout, SCANNING_TIMEOUT);
	}    

	/* add device to the current list of devices */
    private void handleFoundDevice(final BluetoothDevice device,
            final int rssi,
            final byte[] scanRecord)
	{
		// adding to the UI have to happen in UI thread
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mDevicesListAdapter.addDevice(device, rssi, scanRecord);
				mDevicesListAdapter.notifyDataSetChanged();
			}
		});
	}	

    private void btDisabled() {
    	Toast.makeText(this, "Sorry, BT has to be turned ON for us to work!", Toast.LENGTH_LONG).show();
        finish();    	
    }
    
    private void bleMissing() {
    	Toast.makeText(this, "BLE Hardware is required but not available!", Toast.LENGTH_LONG).show();
        finish();    	
    }
}

/*

  0x04, 0x0F, 0x43, 0x31 , 0xC9, 0xC1, 0x3D, 0x6B
, 0x99, 0x9E, 0xD8, 0xA2 , 0x83, 0x77, 0xC2, 0xB4
, 0x86, 0xAD, 0xE9, 0x48

  0x05, 0x0F, 0xB1, 0x03 , 0xA1, 0x3D, 0x7B, 0xCF
, 0xD9, 0xCC, 0xCE, 0xBC , 0xBF, 0xBC, 0xB1, 0x61
, 0xEC, 0x71, 0x9F, 0xFB

  0x06, 0x0F, 0xA0, 0xA4 , 0xF9, 0xEF, 0xB4, 0xCA
, 0xF3, 0xAF, 0x66, 0x8D , 0x62, 0xE4, 0xB2, 0x60
, 0x8A, 0x9B, 0xA1, 0x44

  0x07, 0x0F, 0x6B, 0x6F , 0xF7, 0x94, 0x97, 0x29
, 0xFA, 0x86, 0x63, 0xF4 , 0x4E, 0x3D, 0x3F, 0x1A
, 0xC1, 0xAD, 0x3D, 0x09

  0x08, 0x0F, 0xE5, 0xD7 , 0xB7, 0x11, 0x86, 0xEE
, 0x81, 0xD5, 0x9C, 0xD8 , 0x7D, 0x9C, 0x25, 0x76
, 0x04, 0xF0, 0x62, 0x6C

  0x09, 0x0F, 0x6B, 0x88 , 0xED, 0x17, 0xEE, 0x9E
, 0x96, 0x83, 0x83, 0x37 , 0xFF, 0x00, 0x14, 0xCE
, 0xF6, 0x8F, 0x29, 0x4A

  0x0A, 0x0E, 0x3C, 0xEC , 0x57, 0x89, 0x67, 0x55
, 0xC3, 0x67, 0xDF, 0x61 , 0x72, 0xE9, 0xFB, 0x65
, 0x8D, 0x99, 0x9D, 0x9B

  0x0B, 0x0F, 0xB9, 0xE0 , 0x36, 0x97, 0x0E, 0xFC
, 0x1B, 0x2A, 0xAA, 0xCB , 0x17, 0x79, 0xAE, 0x06
, 0xB7, 0xB9, 0x58, 0x53

  0x0C, 0x0F, 0xF5, 0x88 , 0xAC, 0xC4, 0x43, 0x8C
, 0x4D, 0x9A, 0x52, 0x02 , 0x33, 0xA7, 0xF0, 0x7E
, 0x15, 0x59, 0xEA, 0xB6

  0x0D, 0x0F, 0x38, 0x59 , 0x97, 0x24, 0xA7, 0x54
, 0xB8, 0x9F, 0x6F, 0xDC , 0x30, 0x8A, 0x14, 0x0C
, 0xF3, 0x72, 0xDF, 0xC6

  0x0E, 0x0F, 0x8C, 0x9F , 0xF5, 0x86, 0x24, 0x26
, 0x10, 0x99, 0xE9, 0xD4 , 0x1C, 0x03, 0x0F, 0xAB
, 0xFA, 0xC8, 0xB4, 0x39

  0x0F, 0x0F, 0x27, 0xC2 , 0xFE, 0xC2, 0x8F, 0x72
, 0x92, 0xA6, 0x4B, 0x34 , 0x38, 0x52, 0x1F, 0xF4
, 0xB1, 0x3F

  0x01, 0x01, 0x83, 0x01 , 0x00, 0x01, 0x00, 0x06
, 0x00, 0x03, 0x00, 0x02 , 0x00, 0x03, 0x6C, 0x41


  0x01, 0x0F, 0x83, 0x02 , 0x00, 0x01, 0x01, 0x04
, 0x00, 0x11, 0x01, 0x00 , 0x0C, 0x3D, 0xB9, 0x00
, 0x31, 0x53, 0x14, 0x16

  0x02, 0x0F, 0xB8, 0xB0 , 0x74, 0xFE, 0x9C, 0xD1
, 0xFE, 0x83, 0xC5, 0xE1 , 0x81, 0x4E, 0x8B, 0x72
, 0x8D, 0x8A, 0xE5, 0xCF

  0x03, 0x0F, 0x7A, 0x69 , 0x4A, 0xF4, 0x3D, 0x55
, 0xA0, 0xFE, 0xE9, 0x05 , 0xA8, 0x58, 0xD1, 0xF4
, 0x67, 0x40, 0xAB, 0x3E





byte[] rocheOther_1 = { 0x01, 0x0F, (byte) 0x83, 0x02 ,  0x00, 0x01, 0x01, 0x04,
                        0x00, 0x11, 0x01, 0x00 ,  (byte) 0x98, 0x06, 0x16, 0x44,
                        0x2F, 0x50, (byte) 0xF2, 0x2F };

                byte[] rocheOther_2 = {0x02, 0x0F, (byte) 0xE0, 0x43 , 0x45, (byte) 0xD3, (byte) 0xE8, 0x08 ,
                        0x26, 0x4A, 0x4A, (byte) 0xF6 , 0x12, 0x15, (byte) 0xA5, (byte) 0xE1 ,
                        0x7E, (byte) 0xFD, (byte) 0x99, (byte) 0xA9 };

                byte[] rocheOther_3 = {0x03, 0x0F, 0x0A, (byte) 0xA0 , (byte) 0xE3, (byte) 0xE5, (byte) 0x8F, 0x71 ,
                        0x28, (byte) 0xB1, (byte) 0xA4, 0x7C , (byte) 0xA0, (byte) 0x90, (byte) 0xA1, 0x6A ,
                        0x40, 0x0B, (byte) 0xAF, 0x7F };

                byte[] rocheOther_4 = {0x04, 0x0F, (byte) 0xF8, (byte) 0x80 , 0x13, 0x4E, 0x15, (byte) 0x98 ,
                        (byte) 0xA4, 0x22, 0x36, 0x28 , 0x66, 0x6E, 0x27, (byte) 0xA5 ,
                        0x1D, (byte) 0x9D, 0x0D, (byte) 0xF7 };

                byte[] rocheOther_5 = {0x05, 0x0F, (byte) 0xB0, 0x0E , 0x4D, (byte) 0xA8, (byte) 0xB9, 0x71 ,
                        0x30, (byte) 0xD8, (byte) 0xDD, (byte) 0x96 , 0x29, (byte) 0xA8, 0x6D, (byte) 0xC6 ,
                        (byte) 0xCB, 0x25, (byte) 0xCC, (byte) 0x9B };

                byte[] rocheOther_6 = {0x06, 0x0F, 0x0F, 0x30 , (byte) 0xB0, 0x4E, 0x34, 0x67 ,
                        0x7B, 0x71, 0x70, (byte) 0x8D , 0x37, (byte) 0xA6, (byte) 0xDB, 0x40 ,
                        (byte) 0xBC, 0x7D, 0x29, 0x00 };

                byte[] rocheOther_7 = {0x07, 0x0F, 0x5F, (byte) 0xA2 , (byte) 0xF4, (byte) 0xF6, 0x68, 0x20 ,
                        0x7E, (byte) 0x81, 0x5F, 0x4A , (byte) 0xB9, (byte) 0x9A, (byte) 0xC0, (byte) 0xDC ,
                        0x7D, (byte) 0xF4, (byte) 0xD2, (byte) 0x86 };

                byte[] rocheOther_8 = {0x08, 0x0F, 0x17, (byte) 0xC8 , 0x08, (byte) 0xFC, 0x25, 0x1F ,
                        0x48, (byte) 0xFE, 0x63, (byte) 0x9E , 0x07, (byte) 0xAA, 0x07, (byte) 0x85 ,
                        0x2C, 0x67, 0x22, (byte) 0xA2 };

                byte[] rocheOther_9 = {0x09, 0x0F, (byte) 0x8B, 0x3C , 0x02, (byte) 0xDF, 0x77, 0x1F ,
                        0x28, (byte) 0x8A, (byte) 0xD5, (byte) 0xF8 , (byte) 0xBF, 0x37, 0x6E, 0x2B ,
                        (byte) 0xFE, (byte) 0xA2, 0x55, 0x08 };

                byte[] rocheOther_a = {0x0A, 0x0F, 0x42, (byte) 0xD4 , (byte) 0x89, 0x2A, 0x33, 0x10 ,
                        0x29, 0x17, (byte)0xF0, (byte) 0xD9 , (byte) 0xC2, (byte) 0xD5, (byte) 0xA2, (byte) 0x90 ,
                        0x43, (byte) 0xA5, 0x28, 0x28 };

                byte[] rocheOther_b = {0x0B, 0x0F, (byte) 0xC9, (byte) 0xB1 , (byte) 0xFF, (byte) 0xDB, 0x6F, (byte) 0xE5 ,
                        (byte) 0xB3, 0x29, (byte) 0xDA, (byte) 0xE9 , 0x40, 0x51, 0x46, 0x0B ,
                        0x23, 0x4B, (byte) 0xF4, 0x60 };

                byte[] rocheOther_c = {0x0C, 0x0F, 0x34, 0x6A , (byte) 0xAE, 0x22, (byte) 0xA7, (byte) 0xA4 ,
                        0x30, (byte) 0x83, (byte) 0xDD, (byte) 0x85 , 0x4C, (byte) 0xB3, (byte) 0xD8, (byte) 0xB6 ,
                        0x33, 0x1F, (byte) 0xF8, 0x49 };

                byte[] rocheOther_d = {0x0D, 0x0F, 0x01, 0x20 , (byte) 0x8F, 0x76, 0x04, 0x27 ,
                        0x5F, 0x20, 0x47, 0x35 , 0x44, 0x5E, 0x13, 0x58 ,
                        (byte) 0x8F, 0x4F, 0x32, 0x30 };

                byte[] rocheOther_e = {0x0E, 0x0F, (byte) 0xE9, 0x14 , 0x3A, 0x22, 0x69, (byte) 0xC9 ,
                        0x32, 0x72, (byte) 0x8F, (byte) 0xA0 , (byte) 0xC0, 0x3F, (byte) 0x9E, 0x06 ,
                        (byte) 0xA2, (byte) 0xC1, (byte) 0xEA, 0x54 };

                byte[] rocheOther_f = { 0x0F, 0x0F, 0x05, 0x63 , 0x72, 0x04, (byte) 0xB4, (byte) 0xF5 ,
                        (byte) 0x8F, (byte) 0xAB, (byte) 0x92, 0x32 , (byte) 0xC4, 0x7B, (byte) 0xF2, 0x34 ,
                        (byte) 0xE4, 0x17 };




 */