package com.scrumware.interfaces;

import org.json.*;

public interface IJsonObject {
	public JSONObject toJSON();
	public void updateFromJSON(JSONObject json);
}
