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
        User user = new User();
        NameResolutionCollection resolvedNames = service.resolveName(email);

        if (resolvedNames.getCount() == 1) {
            NameResolution resolvedName = resolvedNames.iterator().next();

            user = new User(
                resolvedName.getMailbox().getAddress(),
                resolvedName.getMailbox().getName(),
                resolvedName.getMailbox().getName(),
                resolvedName.getMailbox().getAddress()
            );
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
