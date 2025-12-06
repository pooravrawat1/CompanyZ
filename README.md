# CompanyZ
CSC3350 Final Development Project


# Team Members
- Eden, Poorav, Shelden, Alaya 

# DB Schema (For Reports)
<img width="1024" height="565" alt="image" src="https://github.com/user-attachments/assets/a48e5938-541e-4efa-935c-4dd2b40bb7ef" />

# Programming Tasks 

User Authentication: 
- Implement a user function that utilizes an employee a unique identifier, like employee ID 
- Store user password and use to validate user authenticity 
- Return valid or invalid details

HR Employee Search:
- Implement a search function that uses name, DOB, SSN, or empid
- Returns related  results for matching employees

Employee Search Handler: 
- Implement a search function that uses name, DOB, SSN, or empid
- Returns related  results for employee 

Database Connection Manager
- Create a module responsible for establishing, maintaining, and closing connections to the database.
- Implement methods for executing queries, handling exceptions, and ensuring secure database transactions.
- This module should support all other tasks such as authentication, search, and salary updates.

Update employee’s salary for an increase of a particular percentage
- Implement a function that updates an employee’s salary by a specified percentage increase.
- This function should take the employee’s ID and the percentage increase as inputs.
- It should connect through the Database Connection Manager, perform the update securely, and confirm success or failure.


# Test Cases

Programming task: Updating employee data.
- First, the user would need to log into the admin HR account, as editing/updating can only be done from there. We’d also need to implement a check to ensure the entered user and pass for the admin account is correct.
Then, the admin would have to pull the data via the SELECT command. This way, they could see what the employee’s data already looks like.
We’d also have to create a method to update XYZ information using UPDATE.

Test cases
 - - Regular update command
 - - Attempting to update the information of an employee that does not exist


Programming task: Search for employee data
- The login process would be the same as that of task a. Then, the admin would choose what data they’re searching by (employee ID, first/last name, SSN, etc.) and what columns they want to display with the SELECT COMMAND. Here’s an example of searching for employees via last name using the employee2Data from the last lab (with another employee with the last name “Brown” added to the table).

Test cases
- - Regular search command
- - Incorrectly entering an employee’s information
- - Attempting to search for the information of an employee that does not exist
- - Attemtping to show the information for a column that does not exist


Programming Tasks: Update salary for for all employees less than a particular amount
- This is a combination of tasks a and b. The admin would log in and search for employees using SELECT (....) WHERE salary < X. After that, we’d have to use the update method & iterate over that list of employees with a salary less than X.

Test Cases
- - Regular search command
- - Incorrectly entering a salary value
- - Attempting to update employees with salaries < X, but there are no rows in the table that match that
