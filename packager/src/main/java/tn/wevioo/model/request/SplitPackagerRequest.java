package tn.wevioo.model.request;

public class SplitPackagerRequest {
	PackagerRequest source;
	PackagerTransformationRequest destination1;
	PackagerTransformationRequest destination2;

	public PackagerRequest getSource() {
		return source;
	}

	public void setSource(PackagerRequest source) {
		this.source = source;
	}

	public PackagerTransformationRequest getDestination1() {
		return destination1;
	}

	public void setDestination1(PackagerTransformationRequest destination1) {
		this.destination1 = destination1;
	}

	public PackagerTransformationRequest getDestination2() {
		return destination2;
	}

	public void setDestination2(PackagerTransformationRequest destination2) {
		this.destination2 = destination2;
	}

}
