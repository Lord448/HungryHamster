package ca.crit.hungryhamster;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.UUID;

import ca.crit.hungryhamster.main.GameScreen;

public class AndroidLauncher extends AndroidApplication {

	public final UUID txChUUID = UUID.fromString("058804de-0a45-11ee-be56-0242ac120002");
	public final UUID rxChUUID = UUID.fromString("006e861c-0a45-11ee-be56-0242ac120002");
	private static final String TAG = "AndroidLauncher";
	private static final String deviceName = "Ladder";
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	private String deviceMacAddress;
	private RojoBLE rojoTX, rojoRX;
	private String strValue;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(!RojoBLE.checkBLESupport(this, bluetoothAdapter)) {
			Toast.makeText(getApplicationContext(), "Your device doesn't support bluetooth", Toast.LENGTH_LONG).show();
			finish();
		}
		deviceMacAddress = RojoBLE.searchForMacAddress(this, bluetoothAdapter, deviceName);
		if(deviceMacAddress == null) {
			Log.e(TAG, "ESP32 not paired");
			Toast.makeText(getApplicationContext(), "ESP32 not paired", Toast.LENGTH_LONG).show();
		}
		else {
			if(GameHandler.DEBUG_MODE != GameHandler.DEBUG_NONE)
				Toast.makeText(getApplicationContext(), "ESP32 paired", Toast.LENGTH_LONG).show();
			rojoTX = new RojoBLE(this, rxChUUID, RojoBLE.ROJO_TYPE_WRITE, deviceMacAddress);
			rojoRX = new RojoBLE(this, txChUUID, RojoBLE.ROJO_TYPE_NOTIFY, deviceMacAddress);
			rojoRX.setOnCharacteristicNotificationListener(this::onCharacteristicNotificationListener);
		}
		//GameHandler.init(0.5f, GameHandler.MOBILE_ENV);
		if(GameHandler.DEBUG_MODE == GameHandler.DEBUG_DEMO)
			GameHandler.init(0.5f, GameHandler.DESKTOP_ENV);
		else
			GameHandler.init(0.0f, GameHandler.MOBILE_ENV);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Main_hungryHamster(), config);
	}

	public void onCharacteristicNotificationListener(byte[] value) {
		strValue = RojoBLE.getString(value);
		Log.i(TAG, "Received: " + strValue);
		GameScreen.lblBLEData.setText(strValue);
		for(int i = 0; i <= GameHandler.LADDER_MAX_STEPS; i++) {
			Log.i(TAG, "StrRecept: " + GameHandler.strReceptions[i]);
			if(strValue.toLowerCase().trim().equals(GameHandler.strReceptions[i].toLowerCase().trim() )) {
				GameHandler.espTouch[i] = true;
				GameHandler.wizardSpell = true;
				for(int j = 0; j < GameHandler.numHouseSteps; j++) {
					if(j != i) {
						GameHandler.espTouch[j] = false;
					}
				}
				return;
			}
		}
		Log.i(TAG, "Data not handled");
	}
}
