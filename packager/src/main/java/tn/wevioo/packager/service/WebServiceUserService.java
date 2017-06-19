package tn.wevioo.packager.service;

import tn.wevioo.packager.entities.WebServiceUser;

public interface WebServiceUserService extends CrudService<WebServiceUser, Integer> {

	public WebServiceUser getWebserviceUser();

}
