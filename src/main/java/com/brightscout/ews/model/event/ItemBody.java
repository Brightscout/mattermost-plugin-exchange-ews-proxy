package com.brightscout.ews.model.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ItemBody {

	@NonNull
	private String content; // Text

	private String contentType; // BodyType
}
