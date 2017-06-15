package tn.wevioo.packager.model.request;

public class MergePackagersRequest {

	PackagerRequest source1;
	PackagerRequest source2;
	PackagerTransformationRequest destination;

	public PackagerRequest getSource1() {
		return source1;
	}

	public void setSource1(PackagerRequest source1) {
		this.source1 = source1;
	}

	public PackagerRequest getSource2() {
		return source2;
	}

	public void setSource2(PackagerRequest source2) {
		this.source2 = source2;
	}

	public PackagerTransformationRequest getDestination() {
		return destination;
	}

	public void setDestination(PackagerTransformationRequest destination) {
		this.destination = destination;
	}

}
