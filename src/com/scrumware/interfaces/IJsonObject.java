package com.scrumware.interfaces;

/**
 * Database Transfer Objects should implement this if
 * they are using drag and drop or being used by the mobile app.
 * 
 * @author Elvin Bearden
 */

import org.json.*;

public interface IJsonObject {
	public JSONObject toJSON();
	public void updateFromJSON(JSONObject json);
}
