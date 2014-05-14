package es.unileon.ulebank.users;

import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.handler.Handler;
import java.util.ArrayList;

/**
 * 
 * @author dorian
 */
public class Admin extends Employee {

	/**
	 * 
	 * @see es.unileon.ulebank.Employee#Employee(java.lang.String,
	 *      java.lang.String, es.unileon.ulebank.handler.Handler,
	 *      es.unileon.ulebank.handler.Handler)
	 */
	public Admin(String name, String surname, String address, float salary,
			Office idOffice, Handler idEmployee) {
		super(name, surname, address, salary, idOffice, idEmployee);
	}

	/**
	 * 
	 * @see es.unileon.ulebank.Employee#Employee(java.lang.String,
	 *      java.lang.String, es.unileon.ulebank.handler.Handler)
	 */
	public Admin(String name, String surname, String address, float salary,
			Handler idEmployee) {
		super(name, surname, address, salary, idEmployee);
	}

	/**
	 * @see es.unileon.ulebank.Employee#isAdmin()
	 * @return true if the employee is admin
	 */
	@Override
	public boolean isAdmin() {
		return true;
	}

	/**
	 * Add a new employee
	 * 
	 * @param newEmployee
	 *            the employee to add
	 * @return true if sucesfully added
	 */
	public boolean addEmployee(Employee newEmployee) {
            return this.getOffice().addEmployee(newEmployee);
	}

	/**
	 * Remove the employee with the given identifier
	 * 
	 * @param employeeIdentifier
	 *            the handler of the employee
	 * @return true if sucessful removed
	 */
	public boolean removeEmployee(Handler employeeIdentifier) {
		return this.getOffice().deleteEmployee(this);
	}
        
        public ArrayList<Employee> getListEmployee(){
            return this.getOffice().getEmployeeList();
        }

}
