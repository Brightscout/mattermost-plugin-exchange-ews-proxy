package com.brightscout.ews.service;

public interface RestService {

	public void syncSubscriptions();

	public boolean getSubscriptionByID(String subscriptionID);
}
