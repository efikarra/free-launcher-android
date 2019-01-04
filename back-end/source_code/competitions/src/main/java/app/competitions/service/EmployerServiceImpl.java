package app.competitions.service;

import java.util.List;

import javax.transaction.Transactional;

import app.competitions.dao.EmployerDao;
import app.competitions.model.Employer;

public class EmployerServiceImpl implements EmployerService {
	private EmployerDao employerDao;

	public void setEmployerDao(EmployerDao employerDao) {
		this.employerDao = employerDao;
	}

	@Transactional
	@Override
	public void save(Employer employer) {
		employerDao.save(employer);

	}

	@Transactional
	@Override
	public List<Employer> listEmployers() {
		return employerDao.listEmployers();
	}

	@Transactional
	@Override
	public Employer findEmployerById(long empId) {
		return employerDao.findEmployerById(empId);
	}

	@Transactional
	@Override
	public Employer findEmployerByProject(long projId) {

		return employerDao.findEmployerByProject(projId);
	}

}
