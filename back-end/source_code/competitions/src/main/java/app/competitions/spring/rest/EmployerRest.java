package app.competitions.spring.rest;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import app.competitions.dao.EmployerDao;
import app.competitions.dto.EmployerDTO;
import app.competitions.dto.ExpertDTO;
import app.competitions.dto.NewEmployerDTO;
import app.competitions.dto.ProjectDTO;
import app.competitions.dto.utils.HibernateMapperFactory;
import app.competitions.model.Employer;
import app.competitions.model.Expert;
import app.competitions.model.Project;
import app.competitions.model.Role;
import app.competitions.service.EmployerService;
import app.competitions.service.ProjectService;
import app.competitions.service.RoleService;

@RestController
@RequestMapping("/employers")
public class EmployerRest {

	@Autowired
	EmployerService employerService;
	@Autowired
	ProjectService projectService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	HibernateMapperFactory mapperFactory;
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<EmployerDTO> getAllEmployers() {
		List<Employer> employers = employerService.listEmployers();

		mapperFactory.classMap(Employer.class, EmployerDTO.class)
				.field("role.role", "role").byDefault().register();
		List<EmployerDTO> employerDTOs = mapperFactory.getMapperFacade()
				.mapAsList(employers, EmployerDTO.class);
		return employerDTOs;

	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public void saveEmployer(@Valid @RequestBody NewEmployerDTO employerDTO,
			BindingResult result) {

		Role role = roleService.findRoleByRoleName(employerDTO.getRole());
		Employer employer = mapperFactory.getMapperFacade().map(employerDTO,
				Employer.class);
		employer.setRole(role);
		employer.setPassword(passwordEncoder.encode(employerDTO.getPassword()));

		employerService.save(employer);
	}
	@RequestMapping(value = "", method = RequestMethod.PUT, headers = "Accept=application/json")
	public void updateEmployer(@Valid @RequestBody EmployerDTO employerDTO) {
		Role role = roleService.findRoleByRoleName(employerDTO.getRole());
		Employer e=employerService.findEmployerById(employerDTO.getUserId());
		Employer employer = mapperFactory.getMapperFacade().map(employerDTO,
				Employer.class);
		employer.setPassword(e.getPassword());
		employer.setRole(role);

		employerService.save(employer);
	}
	@RequestMapping(value = "/{empId}/projects", method = RequestMethod.GET, headers = "Accept=application/json")
	public String findEmployerProjects(@PathVariable long empId) {
		List<Project> projects = projectService
				.findAllProjectsByEmployer(empId);
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

	@RequestMapping(value = "/{empId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String findEmployer(@PathVariable long empId) {
		Employer employer = employerService.findEmployerById(empId);
		mapperFactory.classMap(Employer.class, EmployerDTO.class)
				.field("role.role", "role").byDefault().register();
		EmployerDTO employerDTO = mapperFactory.getMapperFacade().map(employer,
				EmployerDTO.class);
		employerDTO.setRole(employer.getRole().getRole());
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		String employerToJson = "";
		try {
			employerToJson = mapper.writeValueAsString(employerDTO);
			System.out.println(employerToJson);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return employerToJson;
	}

}
