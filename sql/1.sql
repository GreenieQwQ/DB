CREATE VIEW `view1` AS
    SELECT 
        id, name, salary, base_salary, salary + base_salary
    FROM
        Worker
            NATURAL JOIN
        Management
            NATURAL JOIN
        Department
    WHERE
        Worker.id = Management.worker_id
            AND Management.department_id = Deaprtment.id