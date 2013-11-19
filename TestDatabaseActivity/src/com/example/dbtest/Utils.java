package com.example.dbtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

public class Utils {

	/**
	 * Return a List of strings from file in given InputStream
	 * 
	 * @param stream
	 *            = getAssets().open("filename.txt"))
	 * @return List<string>
	 */

	public static List<String> readLinesFromFile(InputStream stream)
	{
		List<String> lines = new ArrayList<String>();
		String line;

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			// read lines until null
			line = reader.readLine();
			while (line != null) {
				lines.add(line);
				Log.d("FileRead", line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// Log the error.
			Log.d("FileRead", e.toString());
		}
		return lines;
	}

}
