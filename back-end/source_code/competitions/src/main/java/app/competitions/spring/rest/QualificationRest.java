package app.competitions.spring.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import app.competitions.dao.QualificationDao;
import app.competitions.dto.ExpertDTO;
import app.competitions.dto.QualificationDTO;
import app.competitions.dto.utils.HibernateMapperFactory;
import app.competitions.model.Expert;
import app.competitions.model.Qualification;
import app.competitions.service.QualificationService;

@RestController
@RequestMapping("/qualifications")
public class QualificationRest {

	@Autowired
	QualificationService qualificationService;
	@Autowired
	HibernateMapperFactory mapperFactory;
	@RequestMapping(value="",method = RequestMethod.GET,headers="Accept=application/json")
	  public String getAllQualifications() {  
	   List<Qualification> qualifications=qualificationService.listQualifications();
	   List<QualificationDTO> qualificationDTOs = mapperFactory.getMapperFacade().mapAsList(
			   qualifications, QualificationDTO.class);
	    ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		String qualsToJson = "";
		try {
			qualsToJson = mapper.writeValueAsString(qualificationDTOs);
			System.out.println(qualsToJson);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return qualsToJson;

	  }
}
