package tn.wevioo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.wevioo.instance.FPackagerInstance;
import tn.wevioo.request.FPackagerRequest;

@RestController
//rest
public class PackagerService {

	@RequestMapping(value = "/createPackager", method = RequestMethod.POST)
	public FPackagerInstance createPackager(@RequestBody FPackagerRequest request) {
		
		return null;
	}
}
