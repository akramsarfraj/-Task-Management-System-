## Steps to run in your machine
Clone the Repo

Open your project with your favorite ide (IntelliJ IDEA,Eclipse)

Update the project (To add the dependencies)

Go to Traini8Application.class file present in package - src\main\java\com\marktech\task_management_system 

Open and Run TaskManagementSystemApplication.java (run as java application)

## Note
  System should have Redis And MySQL

## Creating User
![CreatingUser](https://github.com/user-attachments/assets/28127c99-334b-434f-853e-935178299675)

## Login 
![getTasks](https://github.com/user-attachments/assets/a4502ebc-914d-4dbc-a570-2194a401162c)

It will generate Token to access All other API

## Fetching all the tasks

![getTasks](https://github.com/user-attachments/assets/7e96a4b5-47c9-4aae-a0f5-bfa0f203b536)

It take request param "limit" and "page"

## Other API 
http://localhost:8080/api/delete-task/{id}  for deleting the task 
http://localhost:8080/api/task/{id} fetch task by id
http://localhost:8080/api/filter?priority=*&status=* fetch task by priority and status
