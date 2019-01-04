package app.competitions.service;

import java.util.List;

import app.competitions.model.Employer;

public interface EmployerService {
	public void save(Employer employer);
	public List<Employer> listEmployers();
	public Employer findEmployerById(long empId);
	Employer findEmployerByProject(long projId);
}
