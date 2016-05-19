package com.mcnc.domain;

import java.util.List;

public class PushMessage {
	private List<String> registration_ids;

	public List<String> getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(List<String> registration_ids) {
		this.registration_ids = registration_ids;
	}

	@Override
	public String toString() {
		return "PushMessage [registration_ids=" + registration_ids + "]";
	}

}
