package app.competitions.spring.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.competitions.dto.BidDTO;
import app.competitions.dto.EvaluationDTO;
import app.competitions.dto.ExpertDTO;
import app.competitions.dto.NewProjectDTO;
import app.competitions.dto.ProjectDTO;
import app.competitions.dto.utils.HibernateMapperFactory;
import app.competitions.model.Bid;
import app.competitions.model.EmployerEvaluation;
import app.competitions.model.EmployerEvaluationKey;
import app.competitions.model.Evaluation;
import app.competitions.model.Expert;
import app.competitions.model.ExpertEvaluation;
import app.competitions.model.ExpertEvaluationKey;
import app.competitions.model.Project;
import app.competitions.service.BidService;
import app.competitions.service.EmployerService;
import app.competitions.service.EvaluationService;
import app.competitions.service.ExpertService;
import app.competitions.service.GcmRegistrationService;
import app.competitions.service.NotificationService;
import app.competitions.service.ProjectService;
import app.competitions.service.RoleService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@RestController
@RequestMapping("/projects")
public class ProjectRest {
	@Autowired
	ExpertService expertService;
	@Autowired
	ProjectService projectService;
	@Autowired
	EmployerService employerService;
	@Autowired
	RoleService roleService;
	@Autowired
	BidService bidService;
	@Autowired
	HibernateMapperFactory mapperFactory;
	@Autowired
	EvaluationService evaluationService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	GcmRegistrationService gcmRegistrationService;

	@RequestMapping(value = "/search", method = RequestMethod.GET, headers = "Accept=application/json")
	public String searchProjects(
			@RequestParam(name = "keyword", required = false) String keyword) {
		String currentUsername = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		List<Project> projects = projectService.searchSimilarToUserProjects(
				keyword, currentUsername);
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

	@RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/json")
	public void saveProject(@Valid @RequestBody NewProjectDTO projectDTO) {
		Project project = mapperFactory.getMapperFacade().map(projectDTO,
				Project.class);
		project.setClosed(false);
		projectService.save(project);

	}

	@RequestMapping(value = "", method = RequestMethod.PUT, headers = "Accept=application/json")
	public void updateProject(@Valid @RequestBody ProjectDTO projectDTO) {
		Project project = mapperFactory.getMapperFacade().map(projectDTO,
				Project.class);
		project.setClosed(false);
		projectService.save(project);

	}

	@RequestMapping(value = "/{projId}/isClosed", method = RequestMethod.PUT, headers = "Accept=application/json")
	public void updateProjectIsClosed(@PathVariable("projId") long projId,
			@RequestBody boolean isClosed) {
		Project project = projectService.findProjectById(Long.valueOf(projId));
		project.setClosed(isClosed);
		projectService.save(project);

	}

	@RequestMapping(value = "/{projId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void deleteProject(@PathVariable("projId") long projId) {
		projectService.delete(projId);

	}

	@RequestMapping(value = "/{projId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getProject(@PathVariable("projId") long projId) {

		Project project = projectService.findProjectById(projId);
		ProjectDTO projectDTO = mapperFactory.getMapperFacade().map(project,
				ProjectDTO.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		String projectToJson = "";
		try {
			projectToJson = mapper.writeValueAsString(projectDTO);
			System.out.println(projectToJson);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return projectToJson;

	}

	@RequestMapping(value = "/{projId}/experts", method = RequestMethod.POST, headers = "Accept=application/json")
	public void saveProjectExperts(@PathVariable("projId") long projId,
			@RequestBody List<ExpertDTO> expertDTOs) {
		List<Expert> experts = mapperFactory.getMapperFacade().mapAsList(
				expertDTOs, Expert.class);
		Project project = projectService.findProjectById(projId);
		Set<Expert> expertsSet = new HashSet<Expert>(experts);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		try {
			System.out.println(mapper.writeValueAsString(experts));
			System.out.println(mapper.writeValueAsString(project));
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println(expertsSet.size());
		project.setExperts(expertsSet);
		projectService.save(project);
	}

	@RequestMapping(value = "/{projId}/experts", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getProjectExperts(@PathVariable("projId") long projId) {
		List<Expert> experts = expertService.findExpertsByProject(projId);

		List<ExpertDTO> expertDTOs = mapperFactory.getMapperFacade().mapAsList(
				experts, ExpertDTO.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());

		String expertsToJson = "";
		try {
			expertsToJson = mapper.writeValueAsString(expertDTOs);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return expertsToJson;
	}

	@RequestMapping(value = "/{projId}/bids", method = RequestMethod.GET, headers = "Accept=application/json")
	public String findProjectBids(@PathVariable("projId") long projId) {

		List<Bid> bids = projectService.findProjectBids(projId);
		mapperFactory.classMap(Bid.class, BidDTO.class)
				.field("expert.role.role", "expert.role").byDefault()
				.register();
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

	@RequestMapping(value = "/{projId}/bids", method = RequestMethod.POST, headers = "Accept=application/json")
	public void saveProjectBid(@Valid @RequestBody BidDTO bidDTO,
			@PathVariable("projId") long projId) {
		Bid bid = mapperFactory.getMapperFacade().map(bidDTO, Bid.class);
		bid.getExpert().setRole(
				roleService.findRoleByRoleName(bidDTO.getExpert().getRole()));
		bidService.save(bid);

	}

	@RequestMapping(value = "/{projId}/bids", method = RequestMethod.PUT, headers = "Accept=application/json")
	public void updateProjectBid(@Valid @RequestBody BidDTO bidDTO,
			@PathVariable("projId") String projId) {
		Bid bid = mapperFactory.getMapperFacade().map(bidDTO, Bid.class);
		bid.getExpert().setRole(
				roleService.findRoleByRoleName(bidDTO.getExpert().getRole()));
		bidService.save(bid);

	}

	@RequestMapping(value = "/{projId}/bids/{bidId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void deleteProjectBid(@PathVariable("projId") long projId,
			@PathVariable("bidId") long bidId) {

		bidService.delete(bidId);

	}

	@RequestMapping(value = "/{projId}/evaluations/expertEvaluations", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getProjectExpertEvaluations(
			@PathVariable("projId") long projId,
			@RequestParam(name = "expId", required = false) String expId) {
		mapperFactory.classMap(ExpertEvaluation.class, EvaluationDTO.class)
				.field("evaluation.evalId", "evalId")
				.field("evaluation.comment", "comment")
				.field("evaluation.rating", "rating")
				.field("primaryKey.project", "project")
				.field("primaryKey.expert", "user").byDefault().register();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		List<ExpertEvaluation> evaluations = new ArrayList<ExpertEvaluation>();
		String evaluationsToJson = "";
		if (expId != null) {
			ExpertEvaluation evaluation = evaluationService
					.findExpertEvaluationByExpertAndProject(projId,
							Long.valueOf(expId));
			if (evaluation != null) {
				evaluations.add(evaluation);
			}
		} else {
			evaluations = evaluationService
					.findExpertEvaluationsByProject(projId);
		}
		List<EvaluationDTO> evaluationDTOs = mapperFactory.getMapperFacade()
				.mapAsList(evaluations, EvaluationDTO.class);
		try {
			evaluationsToJson = mapper.writeValueAsString(evaluationDTOs);
			System.out.println(evaluationsToJson);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return evaluationsToJson;

	}

	@RequestMapping(value = "/{projId}/evaluations/expertEvaluations", method = {
			RequestMethod.POST, RequestMethod.PUT }, headers = "Accept=application/json")
	public void saveProjectExpertEvaluation(
			@PathVariable("projId") String projId,
			@RequestBody EvaluationDTO evaluationDTO) {
		Evaluation evaluation = new Evaluation();
		evaluation.setComment(evaluationDTO.getComment());
		evaluation.setRating(evaluationDTO.getRating());
		Project project = projectService.findProjectById(Long.valueOf(projId));
		Expert expert = expertService.findExpertById(evaluationDTO.getUser()
				.getUserId());

		ExpertEvaluationKey primaryKey = new ExpertEvaluationKey();
		primaryKey.setProject(project);
		primaryKey.setExpert(expert);
		ExpertEvaluation e = new ExpertEvaluation();
		e.setEvaluation(evaluation);
		e.setPrimaryKey(primaryKey);

		evaluationService.saveExpertEvaluation(e);
	}

	@RequestMapping(value = "/{projId}/evaluations/expertEvaluations", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void deleteProjectEvaluation(@PathVariable("projId") long projId,
			@RequestParam("userId") long userId) {
		evaluationService.deleteExpertEvaluation(userId, projId);

	}

	@RequestMapping(value = "/{projId}/evaluations/employerEvaluations", method = RequestMethod.GET, headers = "Accept=application/json")
	public String getProjectEmployerEvaluations(
			@PathVariable("projId") long projId) {
		List<EmployerEvaluation> evaluations = evaluationService
				.findEmployerEvaluationsByProject(projId);

		mapperFactory.classMap(EmployerEvaluation.class, EvaluationDTO.class)
				.field("evaluation.evalId", "evalId")
				.field("evaluation.comment", "comment")
				.field("evaluation.rating", "rating")
				.field("primaryKey.project", "project")
				.field("primaryKey.expert", "user").byDefault().register();
		List<EvaluationDTO> evaluationDTOs = mapperFactory.getMapperFacade()
				.mapAsList(evaluations, EvaluationDTO.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate4Module());
		String evaluationsToJson = "";
		try {
			evaluationsToJson = mapper.writeValueAsString(evaluationDTOs);
			System.out.println(evaluationsToJson);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return evaluationsToJson;

	}

	@RequestMapping(value = "/{projId}/evaluations/employerEvaluations", method = {
			RequestMethod.POST, RequestMethod.PUT }, headers = "Accept=application/json")
	public void saveProjectEmployerEvaluation(
			@PathVariable("projId") String projId,
			@RequestBody EvaluationDTO evaluationDTO) {
		Evaluation evaluation = new Evaluation();
		evaluation.setComment(evaluationDTO.getComment());
		evaluation.setRating(evaluationDTO.getRating());
		Project project = projectService.findProjectById(Long.valueOf(projId));
		Expert expert = expertService.findExpertById(evaluationDTO.getUser()
				.getUserId());

		EmployerEvaluationKey primaryKey = new EmployerEvaluationKey();
		primaryKey.setProject(project);
		primaryKey.setExpert(expert);
		EmployerEvaluation e = new EmployerEvaluation();
		e.setEvaluation(evaluation);
		e.setPrimaryKey(primaryKey);

		evaluationService.saveEmployerEvaluation(e);
	}

	@RequestMapping(value = "/{projId}/evaluations/employerEvaluations", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void deleteEmployerEvaluation(@PathVariable("projId") long projId,
			@RequestParam("userId") long userId) {
		evaluationService.deleteEmployerEvaluation(userId, projId);

	}
}
