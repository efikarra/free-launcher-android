package app.competitions.dao;

import java.util.List;

import app.competitions.model.Evaluation;
import app.competitions.model.Expert;
import app.competitions.model.Project;


public interface ExpertDao {
	void save(Expert expert);
    List<Expert> listExperts();
    Expert findExpertByUsername(String username);
    List<Expert> searchExperts(String keyword);
    List<Project> findExpertProjects(long expId);
    List<Evaluation> findExpertProjectsEvaluations(long expId,long projId);
    Expert findExpertById(long expId);
	List<Expert> search(String keyword);
	List<Expert> findExpertsByProject(long projId);
}
