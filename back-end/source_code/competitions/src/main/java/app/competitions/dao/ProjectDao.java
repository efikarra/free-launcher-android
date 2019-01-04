package app.competitions.dao;

import java.util.List;

import app.competitions.model.Bid;
import app.competitions.model.Project;

public interface ProjectDao {
	void save(Project project);
	int delete(long projId);
	public List<Project> findProjects(boolean isClosed);
	public List<Project> searchProjects(String keyword,boolean isClosed);
	public Project findProjectById(long projId);
	public List<Bid> findProjectBids(long projId);
	public List<Project> findProjectsByEmployer(long empId,boolean isClosed);
	public List<Project> findAllProjectsByEmployer(long empId);
}
