package app.competitions.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpertDTO extends UserDTO{
	@NotEmpty
	@NotNull
	@JsonProperty(value="qualifications")
	private List<QualificationDTO> qualifications;


	public List<QualificationDTO> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<QualificationDTO> qualifications) {
		this.qualifications = qualifications;
	}

}
