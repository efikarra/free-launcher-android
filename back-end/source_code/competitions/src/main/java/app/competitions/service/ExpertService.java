package app.competitions.service;

import java.util.List;

import app.competitions.model.Evaluation;
import app.competitions.model.Expert;
import app.competitions.model.Project;

public interface ExpertService {
	public void save(Expert expert);
    public List<Expert> listExperts();
    public List<Expert> searchExperts(String keyword);
    public Expert findExpertByUsername(String username);
    public List<Project> findExpertProjects(long expId);
    public List<Evaluation> findExpertProjectsEvaluations(long expId,long projId);
    public Expert findExpertById(long expId);
	List<Expert> searchExpertsSimilarToEmployer(String keyword,long empId);
	List<Expert> findExpertsByProject(long projId);
}
