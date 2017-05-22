package tn.wevioo.model.request;

import java.util.List;

public class GenerateSqlScriptToImportExistingPackagersRequest {
	List<PackagerRequest> requests;

	String workspace;

	String finalName;

	public List<PackagerRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<PackagerRequest> requests) {
		this.requests = requests;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public String getFinalName() {
		return finalName;
	}

	public void setFinalName(String finalName) {
		this.finalName = finalName;
	}

}
