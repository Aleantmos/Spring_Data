import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Engine implements Runnable {

    private static final String UPDATE_TOWNS ="update Town as t set t.name = upper(t.name) where length(t.name) < 5";
    private static final String SELECT_EMPLOYEE_WITH_NAME_COUNT = "select count(e) from Employee as e where e.firstName = :f_name and e.lastName = :l_name";
    private static final String SELECT_EMPLOYEE_WITH_SALARY = "select e from Employee as e where e.salary > :min_salary";
    private static final String SELECT_EMPLOYEE_FROM_DEPARTMENT = "select e from Employee as e " +
            "where e.department.name = :department_name " +
            "order by e.salary asc, e.id asc";
    private static final String SELECT_EMPLOYEE_WITH_NAME = "select e from Employee as e where e.lastName = :last_name";
    private static final String SELECT_ADDRESS_WITH_EMPLOYEE_COUNT = "select a from Address as a " +
            "order by a.employees.size desc";
    private static final String UPDATE_EMPLOYEES_SALARY = "update Employee as e " +
            "set e.salary = e.salary * 1.2 " +
            "where e.department.id in :ids";
    private static final String SELECT_EMPLOYEE_WITH_ID = "select e from Employee as e where e.id = :emp_id";
    private static final String SELECT_LATEST_TEN_PROJECTS = "select p from Project as p order by p.startDate desc";
    private static final String SELECT_EMPLOYEES_BY_FIRST_NAME_WITH_PATTERN = "select e from Employee as e where e.firstName like :fn";
    private static final String DELETE_ADDRESSES_WITH_TOWN_NAME = "d";
    private static final String SELECT_TOWN_BY_TOWN_NAME = "select t from Town as t where t.name = :town_name";
    private static final String GET_ALL_ADDRESSES_BY_TOWN_NAME = "select a from Address as a where a.town.name = :town_name";
    private static final String SELECT_EMPLOYEE_MAX_SALARY_FOR_DEPARTMENT_IN_RANGE = "select e from Employee as e " +
            "group by e.department.name " +
            "having e.salary not between 30000 and 70000";


    private final EntityManager entityManager;
    private final BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Select ex number:");

        try {
            int exNum = Integer.parseInt(bufferedReader.readLine());
            
            switch (exNum) {
                case 2: exTwo();

                case 3: exThree();

                case 4: exFour();

                case 5: exFive();

                case 6: exSix();

                case 7: exSeven();

                case 8: exEight();

                case 9: exNine();

                case 10: exTen();

                case 11: exEleven();

                case 12: exTwelve();

                case 13: exThirteen();
            }
                    
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    private void exThirteen() throws IOException {

        String townName = bufferedReader.readLine();

        Town town = entityManager.createQuery(SELECT_TOWN_BY_TOWN_NAME, Town.class)
                .setParameter("town_name", townName)
                .getSingleResult();

        List<Address> addresses = entityManager.createQuery(GET_ALL_ADDRESSES_BY_TOWN_NAME, Address.class)
                .setParameter("town_name", townName)
                .getResultList();

        entityManager.getTransaction().begin();

        addresses.forEach(address -> {
            for (Employee employee : address.getEmployees()) {
                employee.setAddress(null);
            }
            address.setTown(null);
            entityManager.remove(address);
        });

        entityManager.remove(town);

        entityManager.getTransaction().commit();

        System.out.printf("%d address%s in %s deleted" + System.lineSeparator(),
                addresses.size(),
                addresses.size() == 1 ? "" : "es",
                town.getName());
    }

    private void exTwelve() {
        entityManager.createQuery(SELECT_EMPLOYEE_MAX_SALARY_FOR_DEPARTMENT_IN_RANGE, Employee.class)
                .getResultList()
                .forEach(employee -> System.out.printf("%s %.2f" + System.lineSeparator(),
                        employee.getDepartment().getName(),
                        employee.getSalary()));


    }

    private void exEleven() throws IOException {
        String pattern = bufferedReader.readLine();

        entityManager.createQuery(SELECT_EMPLOYEES_BY_FIRST_NAME_WITH_PATTERN, Employee.class)
                .setParameter("fn", pattern + "%")
                .getResultList()
                .forEach(employee -> System.out.printf("%s %s - %s - ($%.2f)" + System.lineSeparator(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getJobTitle(),
                        employee.getSalary()));
    }

    private void exTen() {
        entityManager.getTransaction().begin();
        int affectedRoles = entityManager.createQuery(UPDATE_EMPLOYEES_SALARY)
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();
    }

    private void exNine() {

        List<Project> projects = entityManager.createQuery(SELECT_LATEST_TEN_PROJECTS, Project.class)
                .setMaxResults(10)
                .getResultList();

        projects.stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> System.out.printf("Project name: %s%n" +
                        "Project Description: %s%n" +
                        "Project Start Date: %s%n" +
                        "Project End Date: %s%n",
                        project.getName(), project.getDescription(), project.getStartDate().toString(),
                        project.getEndDate() == null ? "null" :  project.getEndDate().toString()));
    }

    private void exEight() throws IOException {
        System.out.println("Type employee's id:");
        int employeeId = Integer.parseInt(bufferedReader.readLine());

        Employee employee = entityManager.createQuery(SELECT_EMPLOYEE_WITH_ID, Employee.class)
                .setParameter("emp_id", employeeId)
                .getSingleResult();


        System.out.printf("%s %s - %s", employee.getFirstName(), employee.getLastName(),
                employee.getProjects().stream()
                        .map(Project::getName)
                        .collect(Collectors.joining(System.lineSeparator())));

    }

    private void exSeven() {
        List<Address> addresses = entityManager
                .createQuery(SELECT_ADDRESS_WITH_EMPLOYEE_COUNT, Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses.forEach(address -> {
            System.out.printf("%s, %s - %d employees%n",
                    address.getText(),
                    address.getTown() == null ? "Unknown" : address.getTown().getName(),
                    address.getEmployees().size());
        });
    }

    private void exSix() throws IOException {
        System.out.println("Enter employee last name:");

        final String lastName = bufferedReader.readLine();

        Employee employee = entityManager.createQuery(SELECT_EMPLOYEE_WITH_NAME, Employee.class)
                .setParameter("last_name", lastName)
                .getSingleResult();

        Address address = createAddress("Vioshka 15");

        entityManager.getTransaction().begin();

        employee.setAddress(address);

        entityManager.getTransaction().commit();
    }

    private Address createAddress(String addressName) {
        Address address = new Address();

        address.setText(addressName);

        entityManager.getTransaction().begin();

        entityManager.persist(address);

        entityManager.getTransaction().commit();

        return address;
    }

    private void exFive() {
        entityManager.createQuery(SELECT_EMPLOYEE_FROM_DEPARTMENT, Employee.class)
                .setParameter("department_name", "Research and Development")
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s from %s - $%.2f%n", employee.getFirstName(),
                            employee.getLastName(),
                            employee.getDepartment().getName(),
                            employee.getSalary());
                });
    }

    private void exFour() {
        entityManager
                .createQuery(SELECT_EMPLOYEE_WITH_SALARY, Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000))
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);
    }

    private void exThree() throws IOException {
        System.out.println("Enter employee full name:");

        final String[] fullName = bufferedReader.readLine().split("\\s+");

        final String firstName = fullName[0];

        final String lastName = fullName[1];

        Long singleResult = entityManager
                .createQuery(SELECT_EMPLOYEE_WITH_NAME_COUNT, Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();


        System.out.println(singleResult == 0 ? "No" : "Yes");

    }

    private void exTwo() {
        entityManager.getTransaction().begin();

        int updateQuery = entityManager.createQuery(
                UPDATE_TOWNS).executeUpdate();

        System.out.println(updateQuery);

        entityManager.getTransaction().commit();
    }
}
