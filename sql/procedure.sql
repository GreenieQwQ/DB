drop procedure if exists saveHistorySalary;
delimiter //
create procedure saveHistorySalary()
BEGIN
	DECLARE n INT DEFAULT 0;
    DECLARE date DATETIME;
	DECLARE i INT DEFAULT 0;
	DECLARE tax INT DEFAULT 0;
	DECLARE Tsalary INT DEFAULT 0;
	SELECT COUNT(*) FROM salary INTO n;
	SET i=0;
    SET date = now();
	WHILE i<n DO 
		set TAX = 0;
        SELECT total_salary FROM salary LIMIT i,1 into Tsalary ;
        if Tsalary <= 10000
    		then set tax = 0;
    	elseif Tsalary <= 20000
    		then set tax = (Tsalary-10000)*0.05;
    	else set tax = 500 + (Tsalary-20000)*0.1;
    	end if;
    	INSERT INTO SalaryHistory(date, worker_id, worker_name, department_name, job, base_salary, salary, punishment, before_tax, tax, total_salary) 
        SELECT date, id, name, department_name, job, base_salary, salary, LatePUnish, total_salary, tax, total_salary-tax FROM salary LIMIT i,1;
  		SET i = i + 1;
	END WHILE;
END //
delimiter ;
