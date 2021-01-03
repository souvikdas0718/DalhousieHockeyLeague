# DalhouseHockeyLeague

This project is a part of a course work at Dalhousie University and it is developed in a way that new functionalities can be added easily along with modifying existing functionalities without introducing new errors and bugs.<br>

**Course Instructor:** [Robert Hawkey](https://www.linkedin.com/in/roberthawkey/)<br>
**Course Work:** Advanced Topics in Software Development<br>
**Programming Language:** Java<br>
**Database:** MySQL<br>
**Deployment Platform:** Timberlea

## Package Structure
1) [main](https://github.com/souvikdas0718/DalhousieHockeyLeague/tree/master/src) package - consists of functionality's source code
2) [test](https://github.com/souvikdas0718/DalhousieHockeyLeague/tree/master/src) package - consists of test cases' source code

## Features
1) Creating a hockey simulation game that allows users to either create a team or simulate an existing team from a JSON file provided (Team, Coach, Free Agents, Managers, Players, Division, Conference).
2) For creating a new team, assign Caoch, Manager, and players among list of available free agents to the team.
3) Trade players, increment aging and update team stats based on simulated games in a season.
4) Update the JSON for each seasons accrdingly and the season winners is displayed in Timberlea Console.

## Implementation
### **S.O.L.I.D Principles** and **Design Patterns**
- Each and every class has specific one responsibility that defines the component of the implemented features.
- No use of hard coded values.
- Database credentials are stored as environment variables for security purpose and not in the source code.
- Production code contains no layer violation, which is useful when dealing with modifications in existing functionality.
- _Programming to an interface_ can be observed in production code, which helps in reducing coupling among different modules.
- All the packages contain java files that represent specific functionality and hence high cohesion among those files are necessary.
- Creational design pattern is used to instantiate classes. To instantiate classes, abstract factories are used, whose implementation supplied concrete factories for using business logic.
- Behavioural design pattern is also used to manage the algorithm for forming the groups. State pattern helps in switching between different algorithms and hence provides the flexibility to adopt new algorithm whenever requried without changing more code.

### **Test Driven Development (TDD)**
- For developing high quality and robust system that never fails, TDD approach fits best. For writing test cases, various mock objects are created. The library used for these test cases is JUnit.
- Test cases cover all the business logics that are written in the production code.
- Test code adheres to the same clean code standards as production code.

### **Continuous Integration and Continuous Deployment Pipeline**
- During the entire development cycle, gitflow was maintained by creating master, develop and other feature branches. Based on the change in branches, workflow was determined, which can be observed in [.gitlab-ci.yml](https://github.com/souvikdas0718/DalhousieHockeyLeague/blob/master/.gitlab-ci.yml) file.
- Test cases are executed by the runner when the code is changed in master and develop branch.
- Environments and databases: Dev, Test and Production.

### **Logging and Exception handling**
- This project contains logs of all the major events that were printed on Timberlea accounts console

### **Clean Code**
- Consistent indentation
- Language (standards) idioms
- Preferred polymorphism to conditions
- No negative conditionals
- No comment noise
- Resource clean up after usage
