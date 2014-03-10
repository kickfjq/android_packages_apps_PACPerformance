package com.pac.performance.utils;

import java.io.IOException;

public class VoltageHelper {

	public static final String CPU_VOLTAGE = "/sys/devices/system/cpu/cpu0/cpufreq/UV_mV_table";

	public static Integer[] getFreqVoltages() {
		if (Utils.exist(CPU_VOLTAGE))
			try {
				String[] value = Utils.readBlock(CPU_VOLTAGE)
						.replace(" mV", "").replace("mhz:", "")
						.split("\\r?\\n");
				Integer[] freq = new Integer[value.length];
				for (int i = 0; i < value.length; i++)
					freq[i] = Integer.parseInt(value[i].split(" ")[0]);
				return freq;
			} catch (IOException e) {
			}
		return new Integer[] { 0 };
	}

	public static Integer[] getVoltages() {
		if (Utils.exist(CPU_VOLTAGE))
			try {
				String[] value = Utils.readBlock(CPU_VOLTAGE)
						.replace(" mV", "").replace("mhz:", "")
						.split("\\r?\\n");
				Integer[] voltages = new Integer[value.length];
				for (int i = 0; i < value.length; i++)
					voltages[i] = Integer.parseInt(value[i].split(" ")[1]);
				return voltages;
			} catch (IOException e) {
			}
		return new Integer[] { 0 };
	}

}