package com.mdsd.library.net.service.task;



public class ServiceResponse {
	private boolean success;
	private String data;
	private String message;

//	public ServiceResponse(boolean success, String data, String message) {
//		this.success = success;
//		this.data = data;
//		this.message = message;
//	}
//
//	public ServiceResponse(String jsonStr) {
//		try {
//			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//			
//			this.success = jsonObject.getBooleanValue("success");
//			this.data = jsonObject.getString("data");
//			this.message = jsonObject.getString("message");
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		
//	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
