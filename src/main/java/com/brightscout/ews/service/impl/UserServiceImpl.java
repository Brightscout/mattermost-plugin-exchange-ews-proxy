package com.brightscout.ews.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.User;
import com.brightscout.ews.payload.ApiResponse;
import com.brightscout.ews.service.UserService;
import com.brightscout.ews.utils.AppConstants;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.search.ResolveNameSearchLocation;
import microsoft.exchange.webservices.data.misc.NameResolution;
import microsoft.exchange.webservices.data.misc.NameResolutionCollection;

@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public ResponseEntity<User> getUser(ExchangeService service, String email) throws InternalServerException {
		logger.debug("Getting user details for user: {}", email);
		try {
			NameResolutionCollection resolvedNames = service.resolveName(email, ResolveNameSearchLocation.DirectoryOnly, false);

			if (resolvedNames.getCount() == 0) {
				throw new InternalServerException(
						new ApiResponse(Boolean.FALSE, "no user found for the provided email address"));
			}
			if (resolvedNames.getCount() > AppConstants.MAX_NUMBER_OF_USERS_PER_EMAIL) {
				throw new InternalServerException(
						new ApiResponse(Boolean.FALSE, "multiple users found for the provided email address"));
			}
			NameResolution resolvedName = resolvedNames.iterator().next();

			String displayName = resolvedName.getMailbox().getName();
			String mail = resolvedName.getMailbox().getAddress();

			return new ResponseEntity<>(new User(mail, displayName, mail), HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while fetching user details. Error: " + e.getMessage()));
		}
	}
}
