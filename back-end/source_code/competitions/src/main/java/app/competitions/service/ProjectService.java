package app.competitions.service;

import java.util.List;

import app.competitions.model.Bid;
import app.competitions.model.Project;

public interface ProjectService {
	void save(Project project);
	int delete(long projId);
	public List<Project> searchProjects(String keyword);
	public List<Project> searchSimilarToUserProjects(String keyword,String username);
	public Project findProjectById(long projId);
	public List<Bid> findProjectBids(long projId);
	public List<Project> findProjectsByEmployer(long empId,boolean isClosed);
	public List<Project> findAllProjectsByEmployer(long empId);
}
