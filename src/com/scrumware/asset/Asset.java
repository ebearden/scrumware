package com.scrumware.asset;

import java.util.Date;

import org.json.JSONObject;

import com.scrumware.config.Constants;
import com.scrumware.interfaces.IJsonObject;

public class Asset implements IJsonObject {
	//Base Variables
	private int assetId;
	private String name;
	private String description;
	private String location;
	private Date createdOn;
	private Date updatedOn;
	private int createdBy;
	private int updatedBy;
	
	//Empty Constructor
	public Asset() {
		
	}
	
	//JSON objects
	public JSONObject toJSON() {
		JSONObject json =  new JSONObject();
		json.put(Constants.ASSET_ID, this.assetId);
		json.put(Constants.UPDATED, this.updatedOn);
		json.put(Constants.UPDATED_BY, this.updatedBy);
		json.put(Constants.ASSET_NAME, this.name);
		json.put(Constants.DESCRIPTION, this.description);
		json.put(Constants.LOCATION, this.location);
		return json;
	}

	public void updateFromJSON(JSONObject json) {
		assetId = json.getInt(Constants.ASSET_ID);
		name = json.getString(Constants.ASSET_NAME);
		description = json.getString(Constants.DESCRIPTION);
		location = json.getString(Constants.LOCATION);
		//createdOn = json.;
		//updatedOn = json.;
		createdBy = json.getInt(Constants.CREATED_BY);
		updatedBy = json.getInt(Constants.UPDATED_BY);
	}
	
	//Get Values
	public int getAssetID() {
		return this.assetId;
	}
	
	public Date getCreated() {
		return this.createdOn;
	}
	
	public int getCreatedBy() {
		return this.createdBy;
	}
	
	public Date getUpdated() {
		return this.updatedOn;
	}
	
	public int getUpdatedBy() {
		return this.updatedBy;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	//Set Values
	public void setAssetID(int asset) {
		this.assetId = asset;
	}
	
	public void setCreated(Date create) {
		this.createdOn = create;
	}
	
	public void setCreatedBy(int createdby) {
		this.createdBy = createdby;
	}
	
	public void setUpdated(Date update) {
		this.updatedOn = update;
	}
	
	public void setUpdatedBy(int updatedby) {
		this.updatedBy = updatedby;
	}
	
	public void setName(String assName) {
		this.name = assName;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public void setLocation(String loc) {
		this.location = loc;
	}
}