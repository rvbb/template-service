package com.rvbb.api.template;

import com.rvbb.api.template.dto.adapter.Cif;
import com.rvbb.api.template.service.impl.CifService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CifServiceTest {

	@Autowired
	CifService service;

	/*	{
        "_id" : ObjectId("604ee8b4a91a422bfea0dae7"),
            "description" : "Using credit and loan",
            "base" : "User",
            "strict" : true,
            "idInjection" : false,
            "_class" : "webflux.demo.customer.model.Cif"
    }*/
	@Test
	void givenBaseValue_whenGetCif_thenReturnOneCif() {
d		String base = "User";
		Cif cif = service.getCif(base);
		assertEquals("User", cif.getBase());
	}

}
