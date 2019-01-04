package app.competitions.service;

import java.util.List;

import javax.transaction.Transactional;

import app.competitions.dao.QualificationDao;
import app.competitions.model.Qualification;

public class QualificationServiceImpl implements QualificationService {

	private QualificationDao qualificationDao;

	public void setQualificationDao(QualificationDao qualificationDao) {
		this.qualificationDao = qualificationDao;
	}

	@Transactional
	@Override
	public List<Qualification> listQualifications() {
		// TODO Auto-generated method stub
		return qualificationDao.listQualifications();
	}
}
