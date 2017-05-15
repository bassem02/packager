package tn.wevioo.dto.response;

import tn.wevioo.dto.packager.PackagerInstanceDTO;

public class TranslocateProductResponseDTO {

	PackagerInstanceDTO source;
	PackagerInstanceDTO destination;

	public PackagerInstanceDTO getSource() {
		return source;
	}

	public void setSource(PackagerInstanceDTO source) {
		this.source = source;
	}

	public PackagerInstanceDTO getDestination() {
		return destination;
	}

	public void setDestination(PackagerInstanceDTO destination) {
		this.destination = destination;
	}

}
