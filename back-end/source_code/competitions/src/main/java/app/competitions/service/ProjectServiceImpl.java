package app.competitions.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import app.competitions.dao.ExpertDao;
import app.competitions.dao.ProjectDao;
import app.competitions.model.Bid;
import app.competitions.model.Expert;
import app.competitions.model.Project;
import app.competitions.model.Qualification;

public class ProjectServiceImpl implements ProjectService {
	@Autowired
	ExpertDao expertDao;

	private ProjectDao projectDao;

	public void setProjectDao(ProjectDao expertDao) {
		this.projectDao = expertDao;
	}

	@Transactional
	@Override
	public List<Project> searchProjects(String keyword) {
		return projectDao.searchProjects(keyword, false);
	}

	@Transactional
	@Override
	public List<Project> searchSimilarToUserProjects(String keyword,
			String username) {
		List<Project> projects;
		if (keyword == null) {
			projects = projectDao.findProjects(false);
		} else {
			projects = projectDao.searchProjects(keyword, false);
		}

		Expert expert = expertDao.findExpertByUsername(username);

		Map<Project, Integer> scores = new HashMap<Project, Integer>();
		Set<Qualification> qualifications = expert.getQualifications();
		for (int i = 0; i < projects.size(); i++) {
			scores.put(projects.get(i), 0);
		}
		for (int i = 0; i < projects.size(); i++) {
			for (Qualification q : qualifications) {
				Project proj = projects.get(i);
				if (proj.getQualifications().contains(q)) {

					if (scores.containsKey(proj)) {
						scores.put(proj, scores.get(projects.get(i)) + 1);
					}
				}
			}
		}
		Comparator<Map.Entry<Project, Integer>> byProjectDate = (
				Map.Entry<Project, Integer> e1, Map.Entry<Project, Integer> e2) -> Long
				.compare(e1.getKey().getEndDate().getTime(), e2.getKey()
						.getEndDate().getTime());
		List<Map.Entry<Project, Integer>> list = new LinkedList<Map.Entry<Project, Integer>>(
				scores.entrySet());
		Collections.sort(list,
				Comparator.comparing(Map.Entry<Project, Integer>::getValue)
						.thenComparing(byProjectDate).reversed());
		return list.stream().map(Map.Entry::getKey)
				.collect(Collectors.toList());

	}

	@Transactional
	@Override
	public Project findProjectById(long projId) {
		return projectDao.findProjectById(projId);
	}

	@Transactional
	@Override
	public List<Bid> findProjectBids(long projId) {
		return projectDao.findProjectBids(projId);
	}

	@Transactional
	@Override
	public void save(Project project) {
		projectDao.save(project);

	}

	@Transactional
	@Override
	public List<Project> findProjectsByEmployer(long empId, boolean isClosed) {
		return projectDao.findProjectsByEmployer(empId, isClosed);
	}

	@Transactional
	@Override
	public List<Project> findAllProjectsByEmployer(long empId) {
		return projectDao.findAllProjectsByEmployer(empId);
	}

	@Transactional
	@Override
	public int delete(long projId) {
		return projectDao.delete(projId);
	}
}
