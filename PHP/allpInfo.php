<?php
    require_once 'connect.php';
    $uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
 
	$resultStr = "";
	for($i=0; $i < 500; $i++) {
		$EscIP[$i] = $uConnect->real_escape_string($_POST['IP' . $i]);
		if(strlen($EscIP[$i]) == 0) {
			continue;
		}
		if($result = $uConnect->query("SELECT `NextCheck`, `AppRunning`, `HasASI`, `HasTrainer`, `file_0`, `file_1`, `file_2`, `file_3`, `file_4`, `file_5`, `file_6`, `file_7`, `file_8`, `file_9`, `file_10`, `file_11`, `file_12`, `file_13`, `file_14`, `file_15`, `file_16`, `file_17`, `file_18`, `file_19`, `file_20`, `file_21`, `file_22`, `file_23`, `file_24`, `file_25`, `file_26`, `file_27`, `file_28`, `file_29`, `file_30`, `X`, `Y`, `Z`, UNIX_TIMESTAMP() AS `CurrTimestamp` FROM `AC_Players` WHERE `IP` = '" . $EscIP[$i] . "' LIMIT 1"))
		{
			$row = $result->fetch_assoc();
			
			if(strcmp(trim($row['HasASI']), "false") == 0) {
				$row['HasASI'] = "0";
			} else {
				$row['HasASI'] = "1";
			}
			
			if(strcmp(trim($row['HasTrainer']), "false") == 0) {
				$row['HasTrainer'] = "0";
			} else {
				$row['HasTrainer'] = "1";
			}
			
	 
			if($row['NextCheck'] >= $row['CurrTimestamp'] && $row['AppRunning'] == 1)
			{
				$resultfile = '-1';
				for($j = 0; $j < 30; $j++) {
					if(strcmp(trim($row['file_' . $j]), "false") == 0) {
						$resultfile = $j;
					}
				}
				$resultStr = $resultStr . $i . ", 1, " . $row['HasASI'] . ", " . $row['HasTrainer'] . ", " . $resultfile . ", " . $row['X'] . ", " . $row['Y'] . ", " . $row['Z'] . "|";
			} else {
				$resultStr = $resultStr . $i . ", 0|";
			}
	 
			$result->free();
		} 
	}
	
	echo $resultStr;
     
    mysqli_close($uConnect);
?>