package tn.wevioo.dto.response;

import tn.wevioo.dto.packager.PackagerInstanceDTO;

public class SplitResponseDTO {

	PackagerInstanceDTO source;
	PackagerInstanceDTO destination1;
	PackagerInstanceDTO destination2;

	public PackagerInstanceDTO getSource() {
		return source;
	}

	public void setSource(PackagerInstanceDTO source) {
		this.source = source;
	}

	public PackagerInstanceDTO getDestination1() {
		return destination1;
	}

	public void setDestination1(PackagerInstanceDTO destination1) {
		this.destination1 = destination1;
	}

	public PackagerInstanceDTO getDestination2() {
		return destination2;
	}

	public void setDestination2(PackagerInstanceDTO destination2) {
		this.destination2 = destination2;
	}

}
