package com.brightscout.ews.model.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemBody {

	@NonNull
	private String content; // Text

	@NonNull
	private String contentType; // BodyType
}
