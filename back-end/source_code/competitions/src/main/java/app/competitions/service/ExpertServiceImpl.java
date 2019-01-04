package app.competitions.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import app.competitions.dao.ExpertDao;
import app.competitions.dao.ProjectDao;
import app.competitions.model.Evaluation;
import app.competitions.model.Expert;
import app.competitions.model.Project;
import app.competitions.model.Qualification;

public class ExpertServiceImpl implements ExpertService {
	private ExpertDao expertDao;
	@Autowired
	private ProjectDao projectDao;

	public void setExpertDao(ExpertDao expertDao) {
		this.expertDao = expertDao;
	}

	@Transactional
	@Override
	public void save(Expert expert) {
		expertDao.save(expert);

	}

	@Transactional
	@Override
	public List<Expert> listExperts() {
		return expertDao.listExperts();
	}

	@Transactional
	@Override
	public List<Expert> searchExperts(String keyword) {
		return expertDao.searchExperts(keyword);
	}

	@Transactional
	@Override
	public Expert findExpertByUsername(String username) {
		return expertDao.findExpertByUsername(username);
	}

	@Transactional
	@Override
	public List<Project> findExpertProjects(long expId) {
		return expertDao.findExpertProjects(expId);
	}

	@Transactional
	@Override
	public List<Evaluation> findExpertProjectsEvaluations(long expId,
			long projId) {
		return expertDao.findExpertProjectsEvaluations(expId, projId);
	}

	@Transactional
	@Override
	public Expert findExpertById(long expId) {
		return expertDao.findExpertById(expId);
	}

	@Transactional
	@Override
	public List<Expert> searchExpertsSimilarToEmployer(String keyword,
			long empId) {
		List<Expert> experts = new ArrayList<Expert>();
		if (keyword == null) {
			experts = expertDao.listExperts();
		} else
			experts = expertDao.search(keyword);

		List<Project> projects = projectDao
				.findProjectsByEmployer(empId, false);

		Map<Expert, Integer> scores = new HashMap<Expert, Integer>();
		for (int i = 0; i < experts.size(); i++) {
			scores.put(experts.get(i), 0);
		}
		for (int i = 0; i < experts.size(); i++) {
			Expert expert = experts.get(i);
			Set<Qualification> expQuals = expert.getQualifications();
			for (int j = 0; j < projects.size(); j++) {
				Project project = projects.get(i);
				Set<Qualification> projQuals = project.getQualifications();

				for (Qualification q : expQuals)
					if (projQuals.contains(q)) {
						scores.put(expert, scores.get(expert) + 1);
					}
			}
		}
		List<Expert> sorted = scores.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.map(Map.Entry::getKey).collect(Collectors.toList());
		return sorted;
	}

	@Transactional
	@Override
	public List<Expert> findExpertsByProject(long projId) {
		return expertDao.findExpertsByProject(projId);
	}

}
