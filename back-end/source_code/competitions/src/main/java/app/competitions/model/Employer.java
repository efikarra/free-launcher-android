package app.competitions.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "employer")
@PrimaryKeyJoinColumn(name="emp_id")  
public class Employer extends User{

	@OneToMany(mappedBy = "employer",cascade = CascadeType.ALL,fetch=FetchType.LAZY)  
	private List<Project> projects;

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
}
