package app.competitions.spring.rest;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import app.competitions.dto.BidDTO;
import app.competitions.dto.ExpertDTO;
import app.competitions.dto.NewExpertDTO;
import app.competitions.dto.ProjectDTO;
import app.competitions.dto.utils.HibernateMapperFactory;
import app.competitions.model.Bid;
import app.competitions.model.Expert;
import app.competitions.model.Project;
import app.competitions.model.Role;
import app.competitions.service.BidService;
import app.competitions.service.EvaluationService;
import app.competitions.service.ExpertService;
import app.competitions.service.ProjectService;
import app.competitions.service.RoleService;
import app.competitions.spring.security.MyUserDetails;

@RestController
@RequestMapping("/experts")
public class ExpertRest {

	@Autowired
	ExpertService expertService;
	@Autowired
	RoleService roleService;
	@Autowired
	ProjectService projectService;
	@Autowired
	BidService bidService;
	@Autowired
	EvaluationService evaluationService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	HibernateMapperFactory mapperFactory;

	@RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getExperts(@RequestParam(required=false) String username) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		String expertsToJson = "";
		List<Expert> experts = new ArrayList<Expert>();
		if (username == null) {
			experts.addAll(expertService.listExperts());
		} else {

			Expert expert = expertService.findExpertByUsername(username);
			experts.add(expert);
		}
		mapperFactory.classMap(Expert.class, ExpertDTO.class)
				.field("role.role", "role").byDefault().register();
		List<ExpertDTO> expertDTOs = mapperFactory.getMapperFacade().mapAsList(
				experts, ExpertDTO.class);

		try {
			expertsToJson = mapper.writeValueAsString(expertDTOs);
			System.out.println(expertsToJson);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return expertsToJson;

	}
	@RequestMapping(value = "/{expId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getExpert(@PathVariable long expId) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		String expertToJson = "";
	    Expert expert = expertService.findExpertById(expId);
	    System.out.println(expert.getQualifications().size());
		mapperFactory.classMap(Expert.class, ExpertDTO.class)
				.field("role.role", "role").byDefault().register();
		
		ExpertDTO expertDTO = mapperFactory.getMapperFacade().map(
				expert, ExpertDTO.class);
		expertDTO.setRole(expert.getRole().getRole());

		try {
			expertToJson = mapper.writeValueAsString(expertDTO);
			System.out.println(expertToJson);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return expertToJson;

	}
	@RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/json")
	public void saveExpert(@Valid @RequestBody NewExpertDTO expertDTO) {
		Role role = roleService.findRoleByRoleName(expertDTO.getRole());

		Expert expert = mapperFactory.getMapperFacade().map(expertDTO,
				Expert.class);
		expert.setPassword(passwordEncoder.encode(expert.getPassword()));
		expert.setRole(role);
		expertService.save(expert);
	}
	@RequestMapping(value = "", method = RequestMethod.PUT, headers = "Accept=application/json")
	public void updateExpert(@Valid @RequestBody ExpertDTO expertDTO) {
		Role role = roleService.findRoleByRoleName(expertDTO.getRole());
		Expert e=expertService.findExpertById(expertDTO.getUserId());
		Expert expert = mapperFactory.getMapperFacade().map(expertDTO,
				Expert.class);
		expert.setPassword(e.getPassword());
		expert.setRole(role);
		expertService.save(expert);
	}
	@RequestMapping(value = "/{expId}/projects", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getExpertProjects(@PathVariable String expId) {
		List<Project> projects = expertService.findExpertProjects(Long
				.valueOf(expId));
		List<ProjectDTO> projectDTOs = mapperFactory.getMapperFacade()
				.mapAsList(projects, ProjectDTO.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		String projectsToJson = "";
		try {
			projectsToJson = mapper.writeValueAsString(projectDTOs);
			System.out.println(projectsToJson);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return projectsToJson;

	}

	@RequestMapping(value = "/{expId}/bids", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getExpertBids(@PathVariable String expId,
			@RequestParam boolean isClosed) {
		List<Bid> bids = bidService.findBidByExpert(Long.valueOf(expId),
				isClosed);
		List<BidDTO> bidDTOs = mapperFactory.getMapperFacade().mapAsList(bids,
				BidDTO.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		String bidsToJson = "";
		try {
			bidsToJson = mapper.writeValueAsString(bidDTOs);
			System.out.println(bidsToJson);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return bidsToJson;

	}


	@RequestMapping(value = "/search", method = RequestMethod.GET, headers = "Accept=application/json")
	public String searchExperts(@RequestParam(value="keyword",required=false) String keyword) {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		List<Expert> experts = expertService.searchExpertsSimilarToEmployer(
				keyword, userDetails.getUser().getUserId());
		List<ExpertDTO> expertDTOs = mapperFactory.getMapperFacade().mapAsList(
				experts, ExpertDTO.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		String expertsToJson = "";
		try {
			expertsToJson = mapper.writeValueAsString(expertDTOs);
			System.out.println(expertsToJson);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return expertsToJson;

	}
}
