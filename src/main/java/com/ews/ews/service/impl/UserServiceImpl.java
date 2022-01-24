package com.ews.ews.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ews.ews.exception.InternalServerException;
import com.ews.ews.model.User;
import com.ews.ews.payload.ApiResponse;
import com.ews.ews.service.UserService;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.misc.NameResolution;
import microsoft.exchange.webservices.data.misc.NameResolutionCollection;

@Service
public class UserServiceImpl implements UserService {
	public ResponseEntity<User> getUser(ExchangeService service, String email) throws Exception {
		try {
			NameResolutionCollection resolvedNames = service.resolveName(email);

			if (resolvedNames.getCount() == 0) {
				throw new InternalServerException(
						new ApiResponse(Boolean.FALSE, "no user found for the provided email address"));
			}
			if (resolvedNames.getCount() > 1) {
				throw new InternalServerException(
						new ApiResponse(Boolean.FALSE, "multiple users found for the provided email address"));
			}
			NameResolution resolvedName = resolvedNames.iterator().next();

			String displayName = resolvedName.getMailbox().getName();
			String mail = resolvedName.getMailbox().getAddress();

			return new ResponseEntity<>(new User(mail, displayName, mail), HttpStatus.OK);

		} catch (Exception e) {
			throw new InternalServerException(new ApiResponse(Boolean.FALSE,
					"error occurred while fetching user details. Error: " + e.getMessage()));
		}
	}
}
