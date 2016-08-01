<?php
    require_once 'connect.php';
    $uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
 
    if (mysqli_connect_errno()) { 
		die("Could not connect to the DB"); 
		return 1;
	}
	
    $EscIP = $uConnect->real_escape_string($_POST['IP']);
 
    if($result = $uConnect->query("SELECT `NextCheck`, `AppRunning`, `HasASI`, `HasTrainer`, `file_0`, `file_1`, `file_2`, `file_3`, `file_4`, `file_5`, `file_6`, `file_7`, `file_8`, `file_9`, `file_10`, `file_11`, `file_12`, `file_13`, `file_14`, `file_15`, `file_16`, `file_17`, `file_18`, `file_19`, `file_20`, `file_21`, `file_22`, `file_23`, `file_24`, `file_25`, `file_26`, `file_27`, `file_28`, `file_29`, `file_30`, `X`, `Y`, `Z`, UNIX_TIMESTAMP() AS `CurrTimestamp` FROM `AC_Players` WHERE `IP` = '" . $EscIP . "' LIMIT 1"))
    {
        $row = $result->fetch_assoc();
		
		if(strcmp($row['HasASI'], "false") == 0) {
			$row['HasASI'] = "0";
		} else {
			$row['HasASI'] = "1";
		}
		
		if($row['NextCheck'] >= $row['CurrTimestamp'] && $row['AppRunning'] == 1)
			{
			$resultfile = '-1';
			for($j = 0; $j < 30; $j++) {
				if(strcmp($row['file_' . $j], "false") == 0) {
					$resultfile = $j;
					break;
				}
			}
			echo "1, " . $row['HasASI'] . ", " . $row['HasTrainer'] . ", " . $resultfile . ", " . $row['X'] . ", " . $row['Y'] . ", " . $row['Z'];
		} else {
			echo "0";
		}
 
        $result->free();
    } else {
	$uConnect->Close();
	die("There was an error getting the data");
    }

     
    $uConnect->Close();
?>