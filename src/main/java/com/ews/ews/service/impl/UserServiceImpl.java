package com.ews.ews.service.impl;

import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.misc.NameResolution;
import microsoft.exchange.webservices.data.misc.NameResolutionCollection;

import com.ews.ews.service.UserService;

import com.ews.ews.model.User;

@Service
public class UserServiceImpl implements UserService {
    public ResponseEntity<User> getUser(ExchangeService service, String email) throws Exception {
        NameResolutionCollection resolvedNames = service.resolveName(email);

        if (resolvedNames.getCount() != 1) {
            throw new Exception("User not found for the provided email address");
        }
        NameResolution resolvedName = resolvedNames.iterator().next();

        String displayName = resolvedName.getMailbox().getName();
        String mail = resolvedName.getMailbox().getAddress();

        return new ResponseEntity<>(new User(mail, displayName, mail), HttpStatus.OK);
    }
}
