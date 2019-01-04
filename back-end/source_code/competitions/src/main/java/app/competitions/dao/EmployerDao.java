package app.competitions.dao;

import java.util.List;

import app.competitions.model.Employer;

public interface EmployerDao{
	public void save(Employer employer);
	Employer findEmployerById(long empId);
	public List<Employer> listEmployers();
	Employer findEmployerByProject(long projId);
	
}
