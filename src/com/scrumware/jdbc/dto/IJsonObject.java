package com.scrumware.jdbc.dto;

import org.json.*;

public interface IJsonObject {
	public JSONObject toJSON();
	public void updateFromJSON(JSONObject json);
}
