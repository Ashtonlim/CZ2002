import java.io.Serializable;
import java.util.Date;

public class Faculty implements Serializable{
	private String facultyName;
	private Date startDate;
	private Date endDate;
	
	public Faculty(String facultyName, Date startDate, Date endDate) {
		this.facultyName = facultyName;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public String getFacultyName() {
		return facultyName;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public void setEndtDate(Date endDate) {
		this.endDate = endDate;
	}
}

